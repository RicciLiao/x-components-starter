package ricciliao.x.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import ricciliao.x.starter.aop.DynamicAspectAutoProperties;
import ricciliao.x.starter.common.CommonAutoProperties;

@AutoConfiguration
public class XStarterAutoConfiguration {

    @Bean
    public XProperties xProperties(@Autowired(required = false) DynamicAspectAutoProperties dynamicAspectProps,
                                   @Autowired CommonAutoProperties commonProps){

        return new XProperties(dynamicAspectProps, commonProps);
    }

}
