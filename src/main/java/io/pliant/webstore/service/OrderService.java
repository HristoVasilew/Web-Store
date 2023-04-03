package io.pliant.webstore.service;

import io.pliant.webstore.dto.CreateOrderDto;
import io.pliant.webstore.dto.response.OrderDtoModel;
import io.pliant.webstore.exception.NotFoundOrdersException;
import io.pliant.webstore.exception.OrderNotFoundException;
import io.pliant.webstore.exception.ProductNotFoundException;
import io.pliant.webstore.exception.UserNotFoundException;

import java.util.List;

public interface OrderService {

    List<OrderDtoModel> findAllOrders();

    List<OrderDtoModel> findAllOrdersForUserWithEmail(String email) throws UserNotFoundException;

    OrderDtoModel createOrder(String email, CreateOrderDto barcode) throws UserNotFoundException, ProductNotFoundException;

    OrderDtoModel updateSpecificOrder(String email, Long orderId, CreateOrderDto createOrderDto) throws UserNotFoundException, OrderNotFoundException, ProductNotFoundException;

    void deleteAllOrders(String email) throws UserNotFoundException, NotFoundOrdersException;

    void deleteSpecificOrder(String email, Long orderId) throws OrderNotFoundException, UserNotFoundException;
}
