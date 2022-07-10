package fa.training.gui;

import java.util.Scanner;

public class MainGui {

    public void renderMain() {
        System.out.println("---------------------WELCOME----------------------");
        System.out.println("--  1. Login                                    --");
        System.out.println("--  2. Register                                 --");
        System.out.println("--  3. Exit                                     --");
        System.out.println("--------------------------------------------------");
    }

    public String renderLogin (Scanner scanner) {
        String phone = "";
        System.out.println("Your phone number: ");
        while (true) {
            phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) {return phone;}
        }
    }
}
