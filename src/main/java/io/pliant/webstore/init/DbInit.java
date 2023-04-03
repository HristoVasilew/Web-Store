package io.pliant.webstore.init;

import io.pliant.webstore.model.UserEntity;
import io.pliant.webstore.model.enums.MemberTypeEnum;
import io.pliant.webstore.repository.OrderRepository;
import io.pliant.webstore.repository.ProductRepository;
import io.pliant.webstore.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class DbInit {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public DbInit(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    private void postConstruct() {

//
//        ProductEntity product = new ProductEntity();
//        product.setName("potato");
//        product.setPrice(new BigDecimal("12.5"));
//        product.setBarcode(UUID.fromString("a731d217-9e9b-4c46-b69d-41b0e49793c6"));
//        product.setDateUpdated(LocalDateTime.now());
//        productRepository.save(product);
//
        UserEntity admin = new UserEntity();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@admin.admin");
        admin.setPassword("password");
        admin.setType(MemberTypeEnum.PREMIUM);
        admin.setDateAdded(LocalDateTime.now());
        admin.setGrantedAuthoritiesList(Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        admin.setIsAdmin(true);
//
//
//        AddressEntity addressEntity = new AddressEntity();
//        addressEntity.setNumber(15);
//        addressEntity.setStreet("Luvska");
//        addressEntity.setCity("Monaco");
//        addressEntity.setUserAddress(user);
//        addressRepository.save(addressEntity);
//
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setTotalPrice(new BigDecimal("233"));
//        orderEntity.setDate(LocalDateTime.now());
//        orderEntity.setUserOrder(user);
//        orderRepository.save(orderEntity);

    }
}
