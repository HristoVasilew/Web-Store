package io.pliant.webstore.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordJpaConverter implements AttributeConverter<String, String> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String convertToDatabaseColumn(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public String convertToEntityAttribute(String encryptedPassword) {
        return encryptedPassword;
    }
}
