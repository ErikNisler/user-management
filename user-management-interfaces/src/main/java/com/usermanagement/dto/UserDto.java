package com.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 5)
    private String name;

    @NotNull
    @Size(min = 5)
    private String password;

    @Email
    private String username;
}
