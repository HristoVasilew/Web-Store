package io.pliant.webstore.dto.response;

import io.pliant.webstore.model.enums.MemberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoModel {

    private String firstName;

    private String lastName;

    private String email;

    private MemberTypeEnum type;

    private LocalDateTime dateAdded;

    private List<String> orders;

    private List<String> addresses;

}
