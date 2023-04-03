package io.pliant.webstore.controller;

import io.pliant.webstore.dto.CreateProductDto;
import io.pliant.webstore.dto.UpdateProductDto;
import io.pliant.webstore.dto.response.ProductDtoModel;
import io.pliant.webstore.exception.BarcodeExistException;
import io.pliant.webstore.exception.ProductNotFoundException;
import io.pliant.webstore.exception.WebStoreException;
import io.pliant.webstore.model.Barcode;
import io.pliant.webstore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
//import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public Collection<ProductDtoModel> findAllProducts(){
        return productService.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDtoModel createProduct(
            @Valid @RequestBody CreateProductDto productDto
    ) throws BarcodeExistException {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{barcode}")
    public ProductDtoModel findByBarcode(
            @PathVariable Barcode barcode
    ) throws ProductNotFoundException {
        return productService.findByBarcode(barcode);
    }

    @PutMapping("/{barcode}")
    public ProductDtoModel updateProduct(
            @PathVariable Barcode barcode,
            //spring convertors and jackson
            @Valid @RequestBody UpdateProductDto updateProductDto
    ) throws ProductNotFoundException {
        return productService.updateProduct(barcode, updateProductDto);
    }

    @DeleteMapping("/{barcode}")
    public void deleteProduct(
            @PathVariable Barcode barcode
    ) throws WebStoreException {
        productService.deleteProduct(barcode);
    }
}
