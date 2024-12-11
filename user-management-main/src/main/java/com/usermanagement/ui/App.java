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
    private UserDto user;

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
                    System.out.print("Choose your option: ");
                    scanner = new Scanner(System.in);
                    command = scanner.nextInt();
                    isCommandSet = true;
                } catch(InputMismatchException ime){
                    System.out.println("Not a number!");
                }
            }

            switch (command) {
                case 1:
                    System.out.print("User name: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();

                    user = new UserDto();
                    user.setUsername(username);
                    user.setPassword(password);

                    boolean logged = userFacade.login(user);
                    if (logged) {
                        login.start(user);
                    }
                    break;
                case 2:
                    System.out.print("Full Name: ");
                    name = scanner.next();
                    System.out.print("User name: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();

                    user = new UserDto();
                    user.setName(name);
                    user.setUsername(username);
                    user.setPassword(password);

                    boolean registered = userFacade.register(user);
                    if (registered) {
                        System.out.println(String.format("User %s has been successfully created!", user.getName()));
                    } else {
                        System.out.println(String.format("Error when creating user %s !", user.getName()));
                    }
                    break;
                case 3:
                    isEndWordEntered = true;
                    System.out.println("The end, thank you!");
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
