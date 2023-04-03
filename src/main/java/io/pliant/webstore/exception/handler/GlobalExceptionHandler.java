package io.pliant.webstore.exception.handler;

import io.pliant.webstore.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleEmailExistException(){
        return "User with that email exist!";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleUserNotFoundException(){
        return "Not exist user with that email!";
    }

    @ExceptionHandler(UserHaveOrdersException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public String handleUserHaveOrdersException(){
        return "Finish all user orders before delete!";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleProductNotFoundException(){
        return "Not exist product with that barcode!";
    }


    @ExceptionHandler(ProductProcessingInOrder.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public String handleProductProcessingInOrder(){
        return "Product existing in order! Finish or remove order to perform this task!";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleOrderNotFoundException(){
        return "Not exist order!";
    }

    @ExceptionHandler(NotFoundOrdersException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundOrdersException(){
        return "Not found any orders!";
    }

    @ExceptionHandler(NotFoundAddressesException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundAddressesException(){
        return "Not found any addresses!";
    }

    @ExceptionHandler(BarcodeExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleBarcodeExistException(){
        return "Product with this barcode exist!";
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleAddressNotFoundException(){
        return "Address not found!";
    }

    @ExceptionHandler(AddressAlreadyRegistered.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleAddressAlreadyRegistered(){
        return "This address exist!";
    }
}
