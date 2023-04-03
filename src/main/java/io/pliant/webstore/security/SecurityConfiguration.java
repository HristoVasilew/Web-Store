package io.pliant.webstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.security.KeyPair;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.security-realm}")
    private String securityRealm;

    @Value("${security.jwt.signing-key:${random.value}}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(encodingStrength);
    }

    @Bean
    public TokenStore tokenStore(@Qualifier("authCertificateKeyPair") KeyPair certificateKeyPair) {
        return new JwtTokenStore(accessTokenConverter(certificateKeyPair));
    }

    @Bean
    public DefaultTokenServices tokenServices(
            TokenStore tokenStore,
            TokenEnhancer tokenEnhancer,
            ClientDetailsService clientDetailsService
    ) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setSupportRefreshToken(false);
        return tokenServices;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().realmName(securityRealm)
                .and()
                .csrf().disable();
    }

    @Bean
    @Autowired
    public JwtAccessTokenConverter accessTokenConverter(
            @Qualifier("authCertificateKeyPair") KeyPair certificateKeyPair
    ) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        if (certificateKeyPair != null &&
                certificateKeyPair.getPrivate() != null &&
                certificateKeyPair.getPublic() != null) {

            converter.setKeyPair(certificateKeyPair);
        } else {
            converter.setSigningKey(signingKey);
        }

        return converter;
    }

    @Bean
    @Primary
    public HttpFirewall relaxedHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedPeriod(true);
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

}
