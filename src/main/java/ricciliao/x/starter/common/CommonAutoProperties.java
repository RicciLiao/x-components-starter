package ricciliao.x.starter.common;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.props.ApplicationProperties;

@ConfigurationProperties("ricciliao.x.common")
public class CommonAutoProperties implements ApplicationProperties {

    /**
     * Application unique name. The metadata of {@link ricciliao.x.mcp.McpIdentifier}.
     */
    private String consumer;
    private SpringdocProperties springdoc;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public SpringdocProperties getSpringdoc() {
        return springdoc;
    }

    public void setSpringdoc(SpringdocProperties springdoc) {
        this.springdoc = springdoc;
    }

    public static class SpringdocProperties {
        private final Contact contact = new Contact().name("RicciLiao").name("riccix@163.com");
        private final License license = new License();
        private boolean enable = false;
        private String title = "";
        private String description;
        private String summary = "";

        public Contact getContact() {
            return contact;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public License getLicense() {
            return license;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
