package io.pliant.webstore.service.impl;

import io.pliant.webstore.dto.AddressDto;
import io.pliant.webstore.dto.CreateUserDto;
import io.pliant.webstore.dto.UpdateUserDto;
import io.pliant.webstore.dto.response.AddressDtoModel;
import io.pliant.webstore.dto.response.UserDtoModel;
import io.pliant.webstore.exception.*;
import io.pliant.webstore.model.AddressEntity;
import io.pliant.webstore.model.OrderEntity;
import io.pliant.webstore.model.UserEntity;
import io.pliant.webstore.model.enums.MemberTypeEnum;
import io.pliant.webstore.repository.OrderRepository;
import io.pliant.webstore.repository.UserRepository;
import io.pliant.webstore.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<UserDtoModel> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return mapUserEntitiesToUserViewModel(users);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDtoModel getUserByEmail(String email) throws UserNotFoundException {

        return mapUserEntityToUserViewModel(userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Transactional (rollbackOn = WebStoreException.class)
    public UserDtoModel createUser(CreateUserDto createUserDto) throws EmailExistException {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new EmailExistException();
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(createUserDto.getEmail());
        userEntity.setFirstName(createUserDto.getFirstName());
        userEntity.setLastName(createUserDto.getLastName());

        userEntity.setPassword(createUserDto.getPassword());

        MemberTypeEnum randomType = MemberTypeEnum.values()[new Random().nextInt(MemberTypeEnum.values().length)];

        userEntity.setType(randomType);

        userRepository.save(userEntity);

        return mapUserEntityToUserViewModel(userEntity);
    }

    private void passwordMatches(String forCheck, String current) throws UserNotFoundException {
        if (!passwordEncoder.matches(forCheck, current)){
            throw new UserNotFoundException();
        }
    }


    @Override
    @Transactional (rollbackOn = WebStoreException.class)
    public UserDtoModel updateUser(String email, UpdateUserDto updateUserDto) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        passwordMatches(updateUserDto.getPassword(), user.getPassword());

        user.setEmail(updateUserDto.getEmail() == null
                ? user.getEmail() : updateUserDto.getEmail());

        user.setFirstName(updateUserDto.getFirstName() == null
                ? user.getFirstName() : updateUserDto.getFirstName());

        user.setLastName(updateUserDto.getLastName() == null
                ? user.getLastName() : updateUserDto.getLastName());

        user.setPassword(updateUserDto.getPassword() == null
                ? user.getPassword() : updateUserDto.getPassword());


        userRepository.save(user);

        return mapUserEntityToUserViewModel(user);
    }

    @Override
    @Transactional (rollbackOn = WebStoreException.class)
    public void deleteUser(String email) throws UserHaveOrdersException, UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (orderRepository.findAllByUser(user).size() > 0) {
            throw new UserHaveOrdersException();
        }

        userRepository.delete(user);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AddressDtoModel> getAllUserAddresses(String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return mapAddressEntitiesToAddressViewModels(
                user.getAddresses());

    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public AddressDtoModel createAddress(String email, AddressDto addressDto) throws UserNotFoundException, AddressAlreadyRegistered {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setNumber(addressDto.getNumber());


        for (AddressEntity address : user.getAddresses()) {
            if (address.equals(addressEntity)) {
                throw new AddressAlreadyRegistered();
            }
        }

        addressEntity.setUser(user);

        List<AddressEntity> addresses = user.getAddresses();

        if (addresses == null) {
            addresses = new ArrayList<>();
        }

        addresses.add(addressEntity);

        user.setAddresses(addresses);

        userRepository.save(user);

        return mapAddressEntityToAddressViewModel(
                addressEntity);
    }


    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public AddressDtoModel updateSpecificAddress(String email, int addressId, AddressDto addressDto) throws UserNotFoundException, AddressNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        AddressEntity addressEntity = user.getAddresses().get(addressId - 1);

        if (addressEntity == null) {
            throw new AddressNotFoundException();
        }

        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setNumber(addressDto.getNumber());

        userRepository.save(user);

        return mapAddressEntityToAddressViewModel(
                addressEntity);
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public void deleteSpecificAddress(String email, int addressId) throws UserNotFoundException, AddressNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        AddressEntity addressEntity = user.getAddresses().remove(addressId - 1);

        if (addressEntity == null) {
            throw new AddressNotFoundException();
        }

        userRepository.save(user);

    }

    @Override
    @Transactional(rollbackOn = UserNotFoundException.class)
    public void deleteAllAddresses(String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        user.getAddresses().clear();
    }

    @NotNull
    private UserDtoModel mapUserEntityToUserViewModel(UserEntity user) {

        UserDtoModel model = new UserDtoModel();
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setEmail(user.getEmail());
        model.setType(user.getType());
        model.setDateAdded(user.getDateAdded());
        if (user.getOrders() != null) {
            model.setOrders(
                    user
                            .getOrders()
                            .stream()
                            .map(OrderEntity::getId)
                            .map(Object::toString)
                            .collect(Collectors.toList())
            );
        }

        if (user.getAddresses() != null) {
            model.setAddresses(
                    user
                            .getAddresses()
                            .stream()
                            .map(AddressEntity::toString)
                            .collect(Collectors.toList())
            );
        }
        model.setType(user.getType());

        return model;
    }

    @NotNull
    private List<UserDtoModel> mapUserEntitiesToUserViewModel(List<UserEntity> users) {
        List<UserDtoModel> userDtoModels = new ArrayList<>();

        for (UserEntity user : users) {
            UserDtoModel model = new UserDtoModel();
            model.setFirstName(user.getFirstName());
            model.setLastName(user.getLastName());
            model.setEmail(user.getEmail());
            model.setType(user.getType());
            model.setDateAdded(user.getDateAdded());
            model.setOrders(
                    user
                            .getOrders()
                            .stream()
                            .map(OrderEntity::getId)
                            .map(Object::toString)
                            .collect(Collectors.toList())
            );
            model.setAddresses(
                    user
                            .getAddresses()
                            .stream()
                            .map(AddressEntity::toString)
                            .collect(Collectors.toList())
            );

            model.setType(user.getType());

            userDtoModels.add(model);
        }

        return userDtoModels;
    }

    @NotNull
    private AddressDtoModel mapAddressEntityToAddressViewModel(AddressEntity addressEntity) {
        AddressDtoModel model = new AddressDtoModel();
        model.setCity(addressEntity.getCity());
        model.setStreet(addressEntity.getStreet());
        model.setNumber(addressEntity.getNumber());
        model.setUser(addressEntity.getUser().toString());
        return model;
    }

    @NotNull
    private List<AddressDtoModel> mapAddressEntitiesToAddressViewModels(List<AddressEntity> addresses) {
        List<AddressDtoModel> addressDtoModels = new ArrayList<>();

        for (AddressEntity address : addresses) {
            AddressDtoModel model = new AddressDtoModel();
            model.setCity(address.getCity());
            model.setStreet(address.getStreet());
            model.setNumber(address.getNumber());
            model.setUser(address.getUser().toString());

            addressDtoModels.add(model);
        }
        return addressDtoModels;
    }
}
