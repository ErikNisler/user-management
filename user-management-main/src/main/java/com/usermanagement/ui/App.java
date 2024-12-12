package com.usermanagement.ui;

import com.usermanagement.dto.UserDto;
import com.usermanagement.facade.UserFacade;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class App {

    private static final String HEADER = "===================================";
    private static final String LOGIN_APP_HEADER = "============ LOGIN APP ============";

    private boolean isEndWordEntered;

    private final UserFacade userFacade;
    private final Login login;

    private String name;
    private String username;
    private String password;

    public App(UserFacade userFacade, Login login) {
        this.userFacade = userFacade;
        this.login = login;
    }

    public void start() {
        printHeader();

        while (!isEndWordEntered) {
            System.out.println("[1] - Login");
            System.out.println("[2] - Registration");
            System.out.println("[3] - End");

            boolean isCommandSet = false;
            Scanner scanner = null;
            int command = 0;

            while (!isCommandSet) {
                try {
                    System.out.println("Choose your option: ");
                    scanner = new Scanner(System.in);
                    command = scanner.nextInt();
                    isCommandSet = true;
                    scanner.nextLine();
                } catch(InputMismatchException ime){
                    System.out.println("Not a number!");
                }
            }

            switch (command) {
                case 1:
                    System.out.println("User name: ");
                    username = scanner.nextLine();
                    System.out.println("Password: ");
                    password = scanner.nextLine();

                    UserDto foundUser = userFacade.login(username, password);
                    if (foundUser != null) {
                        login.start(foundUser);
                    }
                    break;
                case 2:
                    System.out.println("Full Name:");
                    name = scanner.nextLine();
                    System.out.println("User name:");
                    username = scanner.nextLine();
                    System.out.println("Password:");
                    password = scanner.nextLine();

                    boolean registered = userFacade.register(name, username, password);
                    if (registered) {
                        System.out.println(String.format("User %s has been successfully created", name));
                    }
                    break;
                case 3:
                    isEndWordEntered = true;
                    System.out.println("The end, thank you!");
                    break;
                default:
                    System.out.println("Unknown option! Try again");
            }
        }
    }

    private static void printHeader() {
        System.out.println(HEADER);
        System.out.println(LOGIN_APP_HEADER);
        System.out.println(HEADER);
        System.out.println();
    }

}
