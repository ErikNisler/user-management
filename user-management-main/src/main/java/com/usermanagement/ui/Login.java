package com.usermanagement.ui;

import com.usermanagement.dto.UserDto;
import com.usermanagement.facade.UserFacade;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Login {

    private boolean isEndWordEntered;

    private final UserFacade userFacade;

    public Login(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void start(UserDto userDto) {
        System.out.println(String.format("Welcome %s", userDto.getName()));

        while (!isEndWordEntered) {
            System.out.println("[1] - Change password");
            System.out.println("[2] - Delete user");
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
                } catch (InputMismatchException ime) {
                    System.out.println("Not a number!");
                }
            }

            switch (command) {
                case 1:
//                    System.out.print("User name: ");
//                    username = scanner.next();
//                    System.out.print("Password: ");
//                    password = scanner.next();
//
//                    user = new UserDto();
//                    user.setUsername(username);
//                    user.setPassword(password);
//
//                    boolean logged = userFacade.login(user);
//                    if (logged) {
//                        login.start(user);
//                    }
//                    break;
                case 2:
                    boolean deleted = userFacade.register(user);
                    if (registered) {
                        System.out.println(String.format("User %s has been successfully created!", user.getName()));
                    } else {
                        System.out.println(String.format("Error when creating user %s !", user.getName()));
                    }
                    break;
                case 3:
                    isEndWordEntered = true;
                    System.out.println(String.format("User %s logged out.", userDto.getName()));
            }
        }
    }
}
