package fa.training.repository;

import fa.training.entities.Apartment;

import java.util.Set;

public interface ApartmentRepository {

    boolean save(Apartment apartment);

    boolean edit(Apartment apartment);

    boolean delete(Apartment apartment);

    Apartment get(int apartmentId);

    Apartment get(String apartmentCode);

    Set<Apartment> get(int numBedroom, String doorDirection, String status);

    Set<Apartment> getByStatus(String status);

    Set<Apartment> getAll();
}
