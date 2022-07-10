package fa.training.repository;

import fa.training.entities.Customer;

import java.util.Set;

public interface CustomerRepository {
    boolean save(Customer customer);

    boolean edit(Customer customer);

    boolean delete(Customer customer);

    Customer get(int customerId);

    Customer get(String phone);

    Customer getIfBillNull(String phone);

    /**
     * This method return set of customer who buy <= 2 apartment
     *
     * @author QuanHM9
     */
    Set<Customer> getALl();
}
