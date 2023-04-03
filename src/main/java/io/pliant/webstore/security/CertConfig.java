package io.pliant.webstore.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
public class CertConfig {

    @Value("${security.cert.keystore-location:#{null}}")
    private Resource keystoreResource;

    @Value("${security.cert.keystore-pass:#{null}}")
    private String keystorePass;

    @Value("${security.cert.auth-certificate-alias:#{null}}")
    private String authCertificateAlias;

    @Value("${security.cert.auth-certificate-pass:${security.cert.keystore-pass:#{null}}}")
    private String authCertificatePass;

    @Bean
    public KeyPair authCertificateKeyPair() {
        return getKeyPair(authCertificateAlias, authCertificatePass);
    }

    private KeyPair getKeyPair(String certificateAlias, String certificatePass) {
        if (keystoreResource != null) {
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keystoreResource, keystorePass.toCharArray());
            return keyStoreKeyFactory.getKeyPair(certificateAlias, certificatePass.toCharArray());
        } else {
            return null;
        }
    }
}
