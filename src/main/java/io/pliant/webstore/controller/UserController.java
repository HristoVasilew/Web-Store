package io.pliant.webstore.controller;

import io.pliant.webstore.dto.AddressDto;
import io.pliant.webstore.dto.CreateUserDto;
import io.pliant.webstore.dto.UpdateUserDto;
import io.pliant.webstore.dto.response.AddressDtoModel;
import io.pliant.webstore.dto.response.UserDtoModel;
import io.pliant.webstore.exception.EmailExistException;
import io.pliant.webstore.exception.UserNotFoundException;
import io.pliant.webstore.exception.WebStoreException;
import io.pliant.webstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String helloAdmin() {
        return "Hello, Admin";
    }

    @GetMapping("/")
    public List<UserDtoModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoModel createUser(
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws EmailExistException {
        return userService.createUser(createUserDto);
    }

    @GetMapping("/{email}")
    public UserDtoModel getUserByEmail(
            @PathVariable String email
    ) throws UserNotFoundException {

       return userService.getUserByEmail(email);

    }

    @PutMapping("/{email}")
    public UserDtoModel updateUser(
            @PathVariable String email,
            @RequestBody UpdateUserDto updateUserDto
    ) throws UserNotFoundException {

        return userService.updateUser(email, updateUserDto);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(
            @PathVariable String email
    ) throws WebStoreException {
        userService.deleteUser(email);
    }

    @GetMapping("/{email}/addresses/")
    public List<AddressDtoModel> getAllAddressesForUserWithEmail(
            @PathVariable String email
    ) throws UserNotFoundException {
        return userService.getAllUserAddresses(email);
    }

    @PostMapping("/{email}/addresses/")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDtoModel createAddress(
            @PathVariable String email,
            @RequestBody AddressDto addressDto
            ) throws WebStoreException {
        return userService.createAddress(email, addressDto);
    }

    @PutMapping("/{email}/addresses/{addressId}")
    public AddressDtoModel updateAddress(
            @PathVariable String email,
            @PathVariable int addressId,
            @RequestBody AddressDto addressDto
    ) throws WebStoreException {
        return userService.updateSpecificAddress(email, addressId, addressDto);
    }

    @DeleteMapping("/{email}/addresses/")
    public void deleteAllAddresses(
            @PathVariable String email
    ) throws WebStoreException {
        userService.deleteAllAddresses(email);
    }

    @DeleteMapping("/{email}/addresses/{addressId}")
    public void deleteSpecificAddress(
            @PathVariable String email,
            @PathVariable int addressId
    ) throws WebStoreException {
        userService.deleteSpecificAddress(email, addressId);
    }

}
