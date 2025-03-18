package ricciliao.x.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import ricciliao.x.starter.aop.DynamicAspectAutoProperties;
import ricciliao.x.starter.cache.ConsumerCacheAutoProperties;
import ricciliao.x.starter.common.CommonAutoProperties;

@AutoConfiguration
public class XStarterAutoConfiguration {

    @Bean
    public XProperties xProperties(@Autowired CommonAutoProperties commonProps,
                                   @Autowired(required = false) DynamicAspectAutoProperties dynamicAspectProps,
                                   @Autowired(required = false) ConsumerCacheAutoProperties consumerProperties) {

        return
                new XProperties(
                        commonProps,
                        dynamicAspectProps,
                        consumerProperties
                );
    }

}
