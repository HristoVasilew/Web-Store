package io.pliant.webstore.service;

import io.pliant.webstore.dto.AddressDto;
import io.pliant.webstore.dto.CreateUserDto;
import io.pliant.webstore.dto.UpdateUserDto;
import io.pliant.webstore.dto.response.AddressDtoModel;
import io.pliant.webstore.dto.response.UserDtoModel;
import io.pliant.webstore.exception.*;

import java.util.List;

public interface UserService {
    List<UserDtoModel> getAllUsers();

    UserDtoModel getUserByEmail(String email) throws UserNotFoundException;

    UserDtoModel createUser(CreateUserDto createUserDto) throws EmailExistException;

    UserDtoModel updateUser(String email, UpdateUserDto updateUserDto) throws UserNotFoundException;

    void deleteUser(String email) throws UserNotFoundException, UserHaveOrdersException, AddressNotFoundException;

    List<AddressDtoModel> getAllUserAddresses(String email) throws UserNotFoundException;

    AddressDtoModel createAddress(String email, AddressDto addressDto) throws UserNotFoundException, AddressAlreadyRegistered;

    AddressDtoModel updateSpecificAddress(String email, int addressId, AddressDto addressDto) throws UserNotFoundException, AddressNotFoundException;

    void deleteAllAddresses(String email) throws UserNotFoundException, AddressNotFoundException, NotFoundAddressesException;

    void deleteSpecificAddress(String email, int addressId) throws UserNotFoundException, AddressNotFoundException;
}
