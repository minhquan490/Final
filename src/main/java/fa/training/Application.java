package fa.training;

import fa.training.annotation.Inject;
import fa.training.entities.Apartment;
import fa.training.entities.Customer;
import fa.training.entities.Employee;
import fa.training.exception.BirthDateException;
import fa.training.exception.EmailException;
import fa.training.exception.PhoneException;
import fa.training.gui.ApartmentGui;
import fa.training.gui.BillGui;
import fa.training.gui.CustomerGui;
import fa.training.gui.EmployeeGui;
import fa.training.gui.MainGui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import static fa.training.annotation.resolver.InjectResolver.inject;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    @Inject
    private MainGui mainGui;
    @Inject
    private ApartmentGui apartmentGui;

    @Inject
    private CustomerGui customerGui;

    @Inject
    private EmployeeGui employeeGui;

    @Inject
    private BillGui billGui;

    public void run() {
        ConsoleCleaner.clear();
        if (mainGui == null) {
            inject(this);
        }
        try {
            mainGui.renderMain();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    String phone = mainGui.renderLogin(scanner);
                    Customer customer = customerGui.login(phone);
                    if (customer == null) {
                        Customer customerHasNoBill = customerGui.get(phone);
                        customerHasNoBill.setBills(new HashSet<>());
                        customer = customerHasNoBill;
                    }
                    if (customer == null) {
                        System.out.println("You are not registered");
                        run();
                        break;
                    }
                    System.out.println("Login success");
                    int totalApartment = customer.getBills().size();
                    System.out.println("--------------------------------------------------");
                    System.out.println("Hello " + customer.getCustomerName());
                    System.out.println("Total apartment you bought: " + totalApartment);
                    if (totalApartment == 3) {
                        System.out.println("The offer has run out, you can't buy any apartment");
                        break;
                    }
                    System.out.println();
                    apartmentGui.renderBuyMenu();
                    boolean process = apartmentGui.process(scanner.nextInt(), scanner);
                    if (!process) {
                        run();
                        break;
                    }
                    System.out.println("What apartment do you want to buy ? Use apartment code");
                    String code = "";
                    do {
                        code = scanner.nextLine().trim();
                    } while (code.isEmpty());
                    Apartment apartment = apartmentGui.retrieveData(code);
                    if (apartment == null) {
                        System.out.println("Oh no ! Apartment you choice this time is not available");
                        run();
                        break;
                    }
                    System.out.println("Are you sure ? Y/n");
                    if (scanner.nextLine().trim().equalsIgnoreCase("n")) {
                        run();
                        break;
                    }
                    Employee employee = employeeGui.find();
                    if (employee == null) {
                        System.out.println("All employee is busy ! please try later");
                        break;
                    }
                    boolean success = billGui.process(employee, apartment, customer);
                    if (!success) {
                        System.out.println("Buy apartment failure !!! sorry very much");
                        break;
                    }
                    System.out.println("You success buy a apartment, thank you very much");
                    apartmentGui.updateStatus(apartment);
                    break;
                case 2:
                    boolean register = customerGui.register(scanner);
                    if (register) {
                        System.out.println("Register success");
                        run();
                    }
                    break;
                case 3:
                    break;
            }
        } catch (Exception e) {
            if (e instanceof EmailException || e instanceof PhoneException || e instanceof BirthDateException ) {
                System.out.println(e.getMessage());
                boolean register = customerGui.register(scanner);
                if (register) {
                    System.out.println("Register success");
                    run();
                }
            } else {
                System.out.println(e.getMessage());
            }
        }
    }
}
