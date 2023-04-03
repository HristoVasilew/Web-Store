package io.pliant.webstore.service.impl;

import io.pliant.webstore.dto.CreateProductDto;
import io.pliant.webstore.dto.UpdateProductDto;
import io.pliant.webstore.dto.response.ProductDtoModel;
import io.pliant.webstore.exception.BarcodeExistException;
import io.pliant.webstore.exception.ProductNotFoundException;
import io.pliant.webstore.exception.ProductProcessingInOrder;
import io.pliant.webstore.exception.WebStoreException;
import io.pliant.webstore.model.Barcode;
import io.pliant.webstore.model.ProductEntity;
import io.pliant.webstore.repository.ProductRepository;
import io.pliant.webstore.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ProductDtoModel> findAll(){

        return mapProductEntitiesToProductViewModels(
                productRepository.findAll());

    }
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ProductDtoModel findByBarcode(Barcode barcode) throws ProductNotFoundException {

        return mapProductEntityToProductViewModel(
                productRepository.findByBarcode(barcode)
                .orElseThrow(ProductNotFoundException::new));

    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public ProductDtoModel createProduct(CreateProductDto productDto) throws BarcodeExistException {
        if (productRepository.existsByBarcode(productDto.getBarcode())) {
            throw new BarcodeExistException();
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setBarcode(productDto.getBarcode());
        productEntity.setPrice(productDto.getPrice());

        return mapProductEntityToProductViewModel(
                productRepository.save(productEntity));
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public ProductDtoModel updateProduct(Barcode barcode, UpdateProductDto updateProductDto) throws ProductNotFoundException {
        ProductEntity productEntity = productRepository.findByBarcode(barcode)
                .orElseThrow(ProductNotFoundException::new);


        productEntity.setName(updateProductDto.getName());
        productEntity.setPrice(updateProductDto.getPrice());


        return mapProductEntityToProductViewModel(
                productRepository.save(productEntity));
    }

    @Override
    @Transactional(rollbackOn = WebStoreException.class)
    public void deleteProduct(Barcode barcode) throws ProductNotFoundException, ProductProcessingInOrder {
        ProductEntity product = productRepository.findByBarcode(barcode)
                .orElseThrow(ProductNotFoundException::new);

        if (!product.getOrders().isEmpty()){
            throw new ProductProcessingInOrder();
        }

        productRepository.delete(product);
    }

    private ProductDtoModel mapProductEntityToProductViewModel(ProductEntity productEntity) {
        ProductDtoModel model = new ProductDtoModel();
        model.setBarcode(productEntity.getBarcode().getValue());
        model.setName(productEntity.getName());
        model.setPrice(productEntity.getPrice());
        model.setDateUpdated(productEntity.getDateUpdated());

        return model;
    }

    private List<ProductDtoModel> mapProductEntitiesToProductViewModels(List<ProductEntity> all) {
        List<ProductDtoModel> productDtoModels = new ArrayList<>();

        for (ProductEntity product : all) {
            ProductDtoModel model = new ProductDtoModel();
            model.setBarcode(product.getBarcode().getValue());
            model.setName(product.getName());
            model.setPrice(product.getPrice());
            model.setDateUpdated(product.getDateUpdated());

            productDtoModels.add(model);
        }

        return productDtoModels;
    }

}
