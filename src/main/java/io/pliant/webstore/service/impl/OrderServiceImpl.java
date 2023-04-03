package io.pliant.webstore.service.impl;

import io.pliant.webstore.dto.CreateOrderDto;
import io.pliant.webstore.dto.response.OrderDtoModel;
import io.pliant.webstore.exception.*;
import io.pliant.webstore.model.Barcode;
import io.pliant.webstore.model.OrderEntity;
import io.pliant.webstore.model.ProductEntity;
import io.pliant.webstore.model.UserEntity;
import io.pliant.webstore.repository.OrderRepository;
import io.pliant.webstore.repository.ProductRepository;
import io.pliant.webstore.repository.UserRepository;
import io.pliant.webstore.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<OrderDtoModel> findAllOrders() {

        return mapOrderEntitiesToOrderViewModels(orderRepository.findAll());

    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<OrderDtoModel> findAllOrdersForUserWithEmail(String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return mapOrderEntitiesToOrderViewModels(orderRepository.findAllByUser(user));
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public OrderDtoModel createOrder(String email, CreateOrderDto createOrderDto) throws UserNotFoundException, ProductNotFoundException {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<String> barcodes = createOrderDto.getProductEntities();

        List<ProductEntity> productEntities = new ArrayList<>();

        for (String barcode : barcodes) {
            ProductEntity productEntity = productRepository.findByBarcode(new Barcode(barcode))
                    .orElseThrow(ProductNotFoundException::new);


            productEntities.add(productEntity);

        }

        OrderEntity orderEntity = new OrderEntity();

        for (ProductEntity product : productEntities) {
            if (orderEntity.getTotalPrice() == null) {
                orderEntity.setTotalPrice(new BigDecimal(String.valueOf(product.getPrice())));
            } else {
                orderEntity.setTotalPrice(orderEntity.getTotalPrice().add(product.getPrice()));
            }

            product.getOrders().add(orderEntity);
        }

        orderEntity.setGreeting(createOrderDto.getGreeting());
        orderEntity.setProducts(productEntities);
        orderEntity.setUser(user);

        orderRepository.save(orderEntity);
        orderRepository.save(orderEntity);

        return mapOrderEntityToOrderViewModel(orderEntity);
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public OrderDtoModel updateSpecificOrder(String email, Long orderId, CreateOrderDto createOrderDto) throws UserNotFoundException, OrderNotFoundException, ProductNotFoundException {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<String> barcodes = createOrderDto.getProductEntities();

        List<ProductEntity> productEntities = new ArrayList<>();

        for (String barcode : barcodes) {
            ProductEntity productEntity = productRepository.findByBarcode(new Barcode(barcode))
                    .orElseThrow(ProductNotFoundException::new);


            productEntities.add(productEntity);

        }

        OrderEntity order = orderRepository.findById(orderId)
                        .orElseThrow(OrderNotFoundException::new);


        order.setTotalPrice(null);

        for (ProductEntity product : productEntities) {
            if (order.getTotalPrice() == null) {
                order.setTotalPrice(new BigDecimal(String.valueOf(product.getPrice())));
            } else
                order.setTotalPrice(order.getTotalPrice().add(product.getPrice()));
        }

        order.setGreeting(createOrderDto.getGreeting());

        order.setProducts(productEntities);
        order.setUser(user);
        order.setDate(LocalDateTime.now());
        orderRepository.save(order);

        return mapOrderEntityToOrderViewModel(order);
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public void deleteAllOrders(String email) throws UserNotFoundException, NotFoundOrdersException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (orderRepository.findAllByUser(user).size() == 0) {
            throw new NotFoundOrdersException();
        }

        orderRepository.deleteAllByUser(user);
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public void deleteSpecificOrder(String email, Long orderId) throws OrderNotFoundException, UserNotFoundException {
        userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderRepository.delete(order);

    }

    private OrderDtoModel mapOrderEntityToOrderViewModel(OrderEntity orderEntity) {
        OrderDtoModel orderDtoModel = new OrderDtoModel();

        orderDtoModel.setDate(orderEntity.getDate());
        orderDtoModel.setTotalPrice(orderEntity.getTotalPrice());
        orderDtoModel.setGreeting(orderEntity.getGreeting());

        orderDtoModel.setProductEntities(
                orderEntity.getProducts()
                        .stream()
                        .map(ProductEntity::getBarcode)
                        .map(Barcode::getValue)
                        .collect(Collectors.toList())
        );
        return orderDtoModel;
    }

    public List<OrderDtoModel> mapOrderEntitiesToOrderViewModels(List<OrderEntity> orders) {

        List<OrderDtoModel> ordersViews = new ArrayList<>();

        for (OrderEntity order : orders) {
            OrderDtoModel orderDtoModel = new OrderDtoModel();

            orderDtoModel.setDate(order.getDate());
            orderDtoModel.setTotalPrice(order.getTotalPrice());
            orderDtoModel.setGreeting(order.getGreeting());

            orderDtoModel.setProductEntities(
                    order.getProducts()
                            .stream()
                            .map(ProductEntity::getBarcode)
                            .map(Barcode::getValue)
                            .collect(Collectors.toList())
            );

            ordersViews.add(orderDtoModel);
        }

        return ordersViews;
    }
}
