package fa.training.repository;

import fa.training.entities.Bill;

import java.util.Set;

public interface BillRepository {
    boolean save(Bill bill);

    boolean edit(Bill bill);

    boolean delete(Bill bill);

    Set<Bill> getAll();
}
