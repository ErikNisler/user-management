package com.usermanagement.ui;

import com.usermanagement.dto.UserDto;
import com.usermanagement.facade.UserFacade;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
@Component
public class Login {

    private boolean isEndWordEntered;

    private final UserFacade userFacade;

    private String username;
    private String password;

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
                    log.error("Not a number!");
                }
            }

            switch (command) {
                case 1:
                    System.out.print("Write a new password: ");
                    password = scanner.nextLine();
                    if (StringUtils.isBlank(password)) {
                        System.out.print("Aborting password change");
                        break;
                    }
                    boolean updated = userFacade.changePassword(userDto.getUsername(), password);
                    if (updated) {
                        System.out.println(String.format("Password for user %s has been successfully updated", userDto.getUsername()));
                    } else {
                        System.out.println("Password was not updated.");
                    }
                    break;
                case 2:
                    System.out.print("Write a user name to be deleted: ");
                    username = scanner.nextLine();
                    System.out.print("Execute with password: ");
                    password = scanner.nextLine();

                    boolean deleted = userFacade.delete(userDto.getUsername(), password, username);
                    if (deleted) {
                        System.out.println(String.format("User with username %s deleted successfully", username));
                    } else {
                        System.out.println("User was not deleted.");
                    }
                    break;
                case 3:
                    isEndWordEntered = true;
                    System.out.println(String.format("User %s logged out.", userDto.getName()));
                    break;
                default:
                    System.out.println("Unknown option! Try again");
            }
        }
    }
}
