package fa.training.gui;

import fa.training.annotation.Inject;
import fa.training.entities.Apartment;
import fa.training.entities.Bill;
import fa.training.entities.Customer;
import fa.training.entities.Employee;
import fa.training.repository.BillRepository;

import java.time.LocalDate;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.REPOSITORY;

public class BillGui {

    @Inject(REPOSITORY)
    private BillRepository repository;

    public boolean process(Employee e, Apartment a, Customer c) {
        if (repository == null) {
            inject(this);
        }
        Bill bill = new Bill(e, a, c, LocalDate.now(), a.getPrice());
        return repository.save(bill);
    }
}
