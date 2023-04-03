package io.pliant.webstore.dto;

import io.pliant.webstore.validation.anotation.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters.")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name should be between 2 and 50 characters.")
    private String lastName;

    @Size(min = 5, max = 50, message = "Password should be between 5 and 100 characters.")
    private String password;

    @Size(min = 2, max = 50, message = "Email should be between 5 and 100 characters.")
    @ValidEmail(message = "Please enter a valid email address.")
    private String email;
}
