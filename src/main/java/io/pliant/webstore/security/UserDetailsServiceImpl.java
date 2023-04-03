package io.pliant.webstore.security;

import io.pliant.webstore.model.CustomUser;
import io.pliant.webstore.model.UserEntity;
import io.pliant.webstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Map<String, UserEntity> exampleUsers;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    OAuthDao oauthDao;

    @Override
    public CustomUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        try {
            userEntity = oauthDao.getUserDetails(email);
            return new CustomUser(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
    }
}