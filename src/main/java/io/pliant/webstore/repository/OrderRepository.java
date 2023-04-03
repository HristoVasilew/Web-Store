package io.pliant.webstore.repository;

import io.pliant.webstore.model.OrderEntity;
import io.pliant.webstore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUser(UserEntity user);

    void deleteAllByUser(UserEntity user);
}
