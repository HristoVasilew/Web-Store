package io.pliant.webstore.service;

import io.pliant.webstore.dto.CreateProductDto;
import io.pliant.webstore.dto.UpdateProductDto;
import io.pliant.webstore.dto.response.ProductDtoModel;
import io.pliant.webstore.exception.BarcodeExistException;
import io.pliant.webstore.exception.ProductNotFoundException;
import io.pliant.webstore.exception.ProductProcessingInOrder;
import io.pliant.webstore.model.Barcode;

import java.util.List;

public interface ProductService {

    ProductDtoModel findByBarcode(Barcode barcode) throws ProductNotFoundException;

    List<ProductDtoModel> findAll();

    ProductDtoModel createProduct(CreateProductDto productDto) throws BarcodeExistException;

    ProductDtoModel updateProduct(Barcode barcode, UpdateProductDto updateProductDto) throws ProductNotFoundException;

    void deleteProduct(Barcode barcode) throws ProductNotFoundException, ProductProcessingInOrder;
}
