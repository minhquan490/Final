package fa.training.gui;

import fa.training.annotation.Inject;
import fa.training.entities.Employee;
import fa.training.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.REPOSITORY;

public class EmployeeGui {

    @Inject(REPOSITORY)
    private EmployeeRepository repository;

    public Employee find() {
        if (repository == null) {inject(this);}
        Set<Employee> employees = repository.get();
        if (employees == null) {
            employees = repository.getIfBillNull();
        }
        int id = ThreadLocalRandom.current().nextInt(1, employees.size());
        return (Employee) new ArrayList<>(Arrays.asList(employees.toArray())).get(id);
    }
}
