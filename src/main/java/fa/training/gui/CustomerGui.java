package fa.training.gui;

import fa.training.annotation.Inject;
import fa.training.entities.Customer;
import fa.training.repository.CustomerRepository;
import fa.training.validate.Validator;

import java.util.Scanner;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.REPOSITORY;

public class CustomerGui {

    @Inject(REPOSITORY)
    private CustomerRepository repository;

    @Inject
    private Validator validator;

    public Customer login(String phone) {
        if (repository == null) {inject(this);}
        return repository.get(phone);
    }

    public Customer get(String phone) {
        if (repository == null) {inject(this);}
        return repository.getIfBillNull(phone);
    }

    public boolean register(Scanner scanner) {
        if (repository == null) {inject(this);}
        Customer customer = new Customer();
        String fullName = "";
        String phone = "";
        String birthDate = "";
        String address = "";

        System.out.println("What is full name ?");
        do {
            fullName = scanner.nextLine().trim();
        } while (fullName.isEmpty());
        System.out.println("What is your phone ?");
        do {
            phone = scanner.nextLine().trim();
        } while (phone.isEmpty());
        System.out.println("What is your birth date ? Pattern : yyyy-MM-dd");
        do {
            birthDate = scanner.nextLine().trim();
        } while (birthDate.isEmpty());
        System.out.println("What is your address ?");
        do {
            address = scanner.nextLine().trim();
        } while (address.isEmpty());

        customer.setCustomerName(fullName);
        customer.setPhone(validator.validatePhone(phone));
        customer.setBirthDate(validator.validateDate(birthDate));
        customer.setAddress(address);
        return repository.save(customer);
    }
}
