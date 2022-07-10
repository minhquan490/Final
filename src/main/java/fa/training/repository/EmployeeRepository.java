package fa.training.repository;

import fa.training.entities.Employee;

import java.util.Set;

public interface EmployeeRepository {
    boolean save(Employee employee);

    boolean edit(Employee employee);

    boolean delete(Employee employee);

    Employee get(int employeeId);

    Employee get(String employeeName);

    /**
     * This method use to find employee has role NV and sale less 3 apartment
     *
     * @author QuanHM9
     */
    Set<Employee> get();

    Set<Employee> getIfBillNull();

    Set<Employee> getAll();
}
