package fa.training.validate;

import fa.training.exception.BirthDateException;
import fa.training.exception.EmailException;
import fa.training.exception.PhoneException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Validator {

    private static final Map<String, Integer> monthMap = new HashMap<>();

    protected void initMonth() {
        monthMap.put("Jan", 1);
        monthMap.put("Feb", 2);
        monthMap.put("Mar", 3);
        monthMap.put("Apr", 4);
        monthMap.put("May", 5);
        monthMap.put("Jun", 6);
        monthMap.put("Jul", 7);
        monthMap.put("Aug", 8);
        monthMap.put("Sep", 9);
        monthMap.put("Oct", 10);
        monthMap.put("Nov", 11);
        monthMap.put("Dec", 12);
    }

    protected boolean isLeapYear(int year) {
        boolean leap = false;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                leap = year % 400 == 0;
            } else {
                leap = true;
            }
        } else {
            leap = false;
        }
        return leap;
    }

    public Validator(){}

    public String validatePhone(String phone) {
        String pattern = "^((03[2-9])|(07([6-9]|0))|(08[1-5])|(05[6|8])|0[5|9]9|(09[6-8])|088|091|094|089|090|093|086|092)\\d{7}$";
        if (phone.matches(pattern)) {return phone;} else {throw new PhoneException("Phone is invalid");}
    }

    public LocalDate validateDate(String date) {
        initMonth();
        boolean notHaveCharacter = false;
        String[] dateComponent = new String[0];
        if (date.contains("-")) {
            dateComponent = date.split("-");
        }
        if (date.contains("/")) {
            dateComponent = date.split("/");
        }
        if (dateComponent.length == 0) {
            throw new BirthDateException("Wrong format ! We only support xxxx/xx/xx or xxxx-xx-xx");
        }
        for (String value : dateComponent) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                notHaveCharacter = true;
            }
        }
        int year = 0;
        int month = 0;
        int day = 0;
        if (notHaveCharacter) {
            for (String s : dateComponent) {
                if (s.length() == 4 && s.matches("^\\d{4}$")) {
                    year = Integer.parseInt(s);
                } else {
                    if ((day == 0 && month != 0) || (day == 0 && month == 0)) {
                        day = Integer.parseInt(s);
                    }
                    if (month == 0) {
                        month = Integer.parseInt(s) > 12 ? 0 : Integer.parseInt(s);
                    }
                    if (month > day && month > 12) {
                        day = day * month;
                        month = day / month;
                        day = day / month;
                    }
                }
            }
            return LocalDate.parse(String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
        }
        for (String s : dateComponent) {
            try {
                if (s.length() == 4 && s.matches("^\\d{4}$")) {
                    year = Integer.parseInt(s);
                } else {
                    if ((day == 0 && month != 0) || (day == 0 && month == 0)) {
                        day = Integer.parseInt(s);
                    }
                    if (month == 0) {
                        month = Integer.parseInt(s) > 12 ? 0 : Integer.parseInt(s);
                    }
                    if (month > day && month > 12) {
                        day = day * month;
                        month = day / month;
                        day = day / month;
                    }
                }
            } catch (NumberFormatException e) {
                if (monthMap.containsKey(s)) {
                    month = monthMap.get(s);
                }
            }
        }
        if (year == 0 | month == 0 | day == 0) {
            throw new BirthDateException("Something wrong !!!");
        }
        if (year > LocalDate.now().getYear()) {
            throw new BirthDateException("Sorry ! We don't support time traveller");
        }
        if (month == 2 && day > 29) {
            throw new BirthDateException("February can't have " + day);
        }
        if (month > 13) {
            throw new BirthDateException("Has 12 month each year");
        }
        if (day > 31) {
            throw new BirthDateException("Day can't have " + day);
        }
        if (!isLeapYear(year) && month == 2 && day == 29) {
            throw new BirthDateException("Feb in leap year has only 28 day");
        }
        return LocalDate.parse(String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
    }

    public String validateEmail(String email) {
        String patter = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (email.matches(patter)) {return email;} else {throw new EmailException("Wrong format");}
    }
}
