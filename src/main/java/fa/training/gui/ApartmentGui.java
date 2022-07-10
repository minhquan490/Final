package fa.training.gui;

import fa.training.annotation.Inject;
import fa.training.entities.Apartment;
import fa.training.repository.ApartmentRepository;

import java.util.Scanner;
import java.util.Set;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.REPOSITORY;

public class ApartmentGui {

    private final TableList tableApartment = new TableList("Apartment Code", "Number Bedroom", "Door Direction", "Price");

    @Inject(REPOSITORY)
    private ApartmentRepository repository;

    public void renderBuyMenu() {
        System.out.println("--------------------------------------------------");
        System.out.println("--  1. List of apartment available  --------------");
        System.out.println("--  2. Choose the apartment that suits your taste-");
        System.out.println("--  3. Exit --------------------------------------");
        System.out.println("--------------------------------------------------");
    }

    public boolean process(int choice, Scanner scanner) {
        if (repository == null) {
            inject(this);
        }
        Set<Apartment> apartments;
        switch (choice) {
            case 1:
                apartments = repository.getByStatus(Apartment.Status.AVAILABLE.getValue());
                if (apartments.isEmpty()) {
                    System.out.println("Apartment is not available");
                    return false;
                }
                apartments.forEach(a -> tableApartment.addRow(a.getApartmentCode(), String.valueOf(a.getNumBedroom()), a.getDoorDirection(), String.valueOf(a.getPrice())));
                tableApartment.print();
                return true;
            case 2:
                System.out.println("How many bedroom do you want ?");
                int numBedroom = scanner.nextInt();
                System.out.println("Which door direction do you want ?");
                String direct = scanner.nextLine().trim();
                apartments = repository.get(numBedroom,direct,Apartment.Status.AVAILABLE.getValue());
                if (apartments.isEmpty()) {
                    System.out.println("Apartment is not available");
                    return false;
                }
                return true;
            case 3:
                return false;
            default:
                System.out.println("Number input should be in range 1 to 3");
                System.out.println("Your choice ?");
                process(scanner.nextInt(), scanner);
                break;
        }
        return false;
    }

    public Apartment retrieveData(String apartmentCode) {
        if (repository == null) {
            inject(this);
        }
        return repository.get(apartmentCode);
    }

    public void updateStatus(Apartment apartment) {
        apartment.setStatus(Apartment.Status.NOT_AVAILABLE.getValue());
        repository.edit(apartment);
    }
}
