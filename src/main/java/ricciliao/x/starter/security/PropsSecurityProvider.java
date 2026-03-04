package ricciliao.x.starter.security;

import org.springframework.util.StreamUtils;
import ricciliao.x.component.security.SecurityProvider;
import ricciliao.x.component.security.crypto.aes.AbstractAES;
import ricciliao.x.component.security.crypto.aes.AbstractAESDecryptor;
import ricciliao.x.component.security.crypto.aes.AbstractAESEncryptor;
import ricciliao.x.component.security.crypto.rsa.AbstractRSADecryptor;
import ricciliao.x.component.security.crypto.rsa.AbstractRSAEncryptor;
import ricciliao.x.component.utils.CoreUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Objects;
import java.util.function.Function;

public class PropsSecurityProvider implements SecurityProvider {

    private final byte[] aesKey;
    private final PublicKey rsaPublicKey;
    private final PrivateKey rsaPrivateKey;

    public PropsSecurityProvider() throws IOException, GeneralSecurityException {
        SecurityAutoProperties props = CoreUtils.convert2Properties(SecurityAutoProperties.class);
        if (Objects.isNull(props)) {
            props = new SecurityAutoProperties();
        }
        //AES
        try (InputStream inputStream = props.getAesResource().getInputStream()) {
            this.aesKey = StreamUtils.copyToByteArray(inputStream);
        }
        //RSA
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream inputStream = props.getRsa().getResource().getInputStream()) {
            keyStore.load(inputStream, props.getRsa().getPassword().toCharArray());
        }
        Certificate cert = keyStore.getCertificate(props.getRsa().getAlias());
        rsaPublicKey = cert.getPublicKey();
        KeyStore.PasswordProtection protection = new KeyStore.PasswordProtection(props.getRsa().getPassword().toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(props.getRsa().getAlias(), protection);
        rsaPrivateKey = privateKeyEntry.getPrivateKey();
    }

    @Override
    public Function<byte[], AbstractAESDecryptor> aesDecryptor() {

        return bytes -> new AbstractAESDecryptor(bytes) {

            @Override
            protected SecretKey getKey() {

                return new SecretKeySpec(aesKey, AbstractAES.SECRET_KEY_ALGO);
            }
        };
    }

    @Override
    public Function<byte[], AbstractAESEncryptor> aesEncryptor() {

        return bytes -> new AbstractAESEncryptor(bytes) {
            @Override
            protected SecretKey getKey() {

                return new SecretKeySpec(aesKey, AbstractAES.SECRET_KEY_ALGO);
            }
        };
    }

    @Override
    public Function<byte[], AbstractRSADecryptor> rsaDecryptor() {

        return bytes -> new AbstractRSADecryptor(bytes) {

            @Override
            protected Key getKey() {

                return rsaPrivateKey;
            }
        };
    }

    @Override
    public Function<byte[], AbstractRSAEncryptor> rsaEncryptor() {

        return bytes -> new AbstractRSAEncryptor(bytes) {

            @Override
            protected Key getKey() {

                return rsaPublicKey;
            }
        };
    }

}
