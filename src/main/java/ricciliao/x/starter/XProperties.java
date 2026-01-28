package ricciliao.x.starter;

import org.springframework.boot.info.BuildProperties;
import ricciliao.x.starter.aop.DynamicAspectAutoProperties;
import ricciliao.x.starter.common.CommonAutoProperties;
import ricciliao.x.starter.mcp.ConsumerCacheAutoProperties;

public record XProperties(BuildProperties buildProps,
                          CommonAutoProperties commonProps,
                          DynamicAspectAutoProperties dynamicAspectProps,
                          ConsumerCacheAutoProperties consumerCacheAutoProps) {
}
