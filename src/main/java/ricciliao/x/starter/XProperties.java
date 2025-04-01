package ricciliao.x.starter;

import ricciliao.x.starter.aop.DynamicAspectAutoProperties;
import ricciliao.x.starter.cache.ConsumerCacheAutoProperties;
import ricciliao.x.starter.common.CommonAutoProperties;

public record XProperties(CommonAutoProperties commonProps,
                          DynamicAspectAutoProperties dynamicAspectProps) {

}
