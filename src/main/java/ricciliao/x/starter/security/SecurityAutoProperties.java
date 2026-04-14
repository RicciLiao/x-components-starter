package ricciliao.x.starter.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ricciliao.x.component.props.ApplicationProperties;

@ConfigurationProperties("ricciliao.x.security")
public class SecurityAutoProperties implements ApplicationProperties {

    /**
     * AES key file with binary.
     */
    private Resource aesResource = new ClassPathResource("aes-key.bin");
    private Rsa rsa = new Rsa();
    /**
     * Enable the {@link ricciliao.x.starter.security.PropsSecurityProviderFactory}.
     */
    private boolean active = false;

    public Resource getAesResource() {
        return aesResource;
    }

    public void setAesResource(Resource aesResource) {
        this.aesResource = aesResource;
    }

    public Rsa getRsa() {
        return rsa;
    }

    public void setRsa(Rsa rsa) {
        this.rsa = rsa;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static class Rsa {
        /**
         * PKCS12 file which contains RSA key.
         */
        private Resource resource = new ClassPathResource("keystore.p12");
        /**
         * PKCS12 file password.
         */
        private String password = "ricciliao";
        /**
         * PKCS12 item alias name which is RSA key.
         */
        private String alias = "X-RSA";

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

}
