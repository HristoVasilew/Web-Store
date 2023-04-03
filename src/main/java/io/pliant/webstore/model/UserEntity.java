package io.pliant.webstore.model;

import io.pliant.webstore.convertor.MemberTypeJpaConverter;
import io.pliant.webstore.convertor.PasswordJpaConverter;
import io.pliant.webstore.model.enums.MemberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "User")
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(nullable = false,unique = true)
    @Size(min = 2, max = 50)
    private String email;

    @Column(nullable = false)
    @Convert(converter = PasswordJpaConverter.class)
    @Size(min = 5, max = 100)
    private String password;

    @Column(nullable = false)
    @Convert(converter = MemberTypeJpaConverter.class)
    private MemberTypeEnum type;

    @Transient
    private Boolean isAdmin;

    @Transient
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dateAdded;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    @PrePersist
    public void prePersist() {
        this.dateAdded = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("FirstName: %s, LastName: %s, Email: %s",
                getFirstName(),
                getLastName(),
                getEmail());
    }
}
