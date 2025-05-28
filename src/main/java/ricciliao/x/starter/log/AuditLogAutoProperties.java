package ricciliao.x.starter.log;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.component.props.ApplicationProperties;

@ConfigurationProperties("ricciliao.x.log")
public class AuditLogAutoProperties extends ApplicationProperties {

    private Executor executor;

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public static class Executor {
        private Boolean enable = Boolean.FALSE;
        private Integer corePoolSize = 1;
        private Integer maxPoolSize = Integer.MAX_VALUE;
        private Integer keepAliveSeconds = 60;
        private Integer queueCapacity = Integer.MAX_VALUE;
        private String threadNamePrefix = "MDC Executor - ";

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public Integer getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(Integer corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public Integer getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(Integer maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public Integer getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public void setKeepAliveSeconds(Integer keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public Integer getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(Integer queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }

}
