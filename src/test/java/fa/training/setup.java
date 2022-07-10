package fa.training;

import fa.training.annotation.Inject;
import fa.training.entities.Apartment;
import fa.training.entities.Employee;
import fa.training.entities.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.SESSION;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class setup {

    @Inject(SESSION)
    private static Session session;

    @AfterAll
    public static void clear() {
        session.close();
    }

    @Test
    public void initData() {
        inject(this);
        Transaction transaction = session.beginTransaction();

        Role role1 = Role.createRole("QL");
        Role role2 = Role.createRole("NV");

        Employee employee1 = Employee.createEmployee("Employee 1", role1);
        Employee employee2 = Employee.createEmployee("Employee 2", role2);
        Employee employee3 = Employee.createEmployee("Employee 3", role2);
        Employee employee4 = Employee.createEmployee("Employee 4", role2);

        role1.setEmployees(Stream.of(employee1).collect(Collectors.toSet()));
        role2.setEmployees(new HashSet<>(Arrays.asList(employee2, employee3, employee4)));

        session.save(role1);
        session.save(role2);

        Apartment apartment1 = Apartment.createApartment("A-1", 1, "north", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment2 = Apartment.createApartment("A-2", 1, "east", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment3 = Apartment.createApartment("A-3", 1, "south", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment4 = Apartment.createApartment("A-4", 2, "north", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment5 = Apartment.createApartment("A-5", 2, "south", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment6 = Apartment.createApartment("A-6", 2, "north", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment7 = Apartment.createApartment("A-7", 2, "east", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment8 = Apartment.createApartment("A-8", 2, "west", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment9 = Apartment.createApartment("A-9", 3, "north", 10000, Apartment.Status.AVAILABLE.getValue());
        Apartment apartment10 = Apartment.createApartment("A-10", 3, "east", 10000, Apartment.Status.AVAILABLE.getValue());

        session.save(apartment1);
        session.save(apartment2);
        session.save(apartment3);
        session.save(apartment4);
        session.save(apartment5);
        session.save(apartment6);
        session.save(apartment7);
        session.save(apartment8);
        session.save(apartment9);
        session.save(apartment10);

        transaction.commit();
        assertTrue(true);
    }
}
