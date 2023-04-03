package io.pliant.webstore.controller;

import io.pliant.webstore.dto.CreateOrderDto;
import io.pliant.webstore.dto.response.OrderDtoModel;
import io.pliant.webstore.exception.UserNotFoundException;
import io.pliant.webstore.exception.WebStoreException;
import io.pliant.webstore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<OrderDtoModel> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{email}")
    public List<OrderDtoModel> getAllOrdersForUserWithEmail(
            @PathVariable String email
    ) throws UserNotFoundException {
        return orderService.findAllOrdersForUserWithEmail(email);
    }

    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDtoModel createOrder(
            @PathVariable String email,
            @Valid @RequestBody CreateOrderDto createOrderDto
    ) throws WebStoreException {
        return orderService.createOrder(email, createOrderDto);
    }

    @PutMapping("/{email}/{orderId}")
    public OrderDtoModel updateOrder(
            @PathVariable String email,
            @PathVariable Long orderId,
            @Valid @RequestBody CreateOrderDto createOrderDto
            ) throws WebStoreException {

        return orderService.updateSpecificOrder(email, orderId, createOrderDto);
    }

    @DeleteMapping("/{email}")
    public void deleteAllOrders(
            @PathVariable String email
    ) throws WebStoreException {
        orderService.deleteAllOrders(email);
    }

    @DeleteMapping("/{email}/{orderId}")
    public void deleteSpecificOrder(
            @PathVariable String email,
            @PathVariable Long orderId
    ) throws WebStoreException {
        orderService.deleteSpecificOrder(email, orderId);
    }

}
