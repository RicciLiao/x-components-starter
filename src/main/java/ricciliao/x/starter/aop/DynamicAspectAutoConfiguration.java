package ricciliao.x.starter.aop;

import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = DynamicAspectAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.dynamic-aspect.aspect-list[0].expression",
        imports = {DynamicAspectDefinitionRegistrar.class}
)
public class DynamicAspectAutoConfiguration {

}
