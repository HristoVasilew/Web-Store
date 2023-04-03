package io.pliant.webstore.repository;

import io.pliant.webstore.model.Barcode;
import io.pliant.webstore.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Barcode> {

    boolean existsByBarcode(Barcode barcode);

    Optional<ProductEntity> deleteByBarcode(Barcode barcode);

    Optional<ProductEntity> findByBarcode(Barcode barcode);
}
