package ricciliao.x.starter.mcp;

import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = ConsumerCacheAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.mcp.operation-list[0].store",
        imports = {ConsumerCacheDefinitionRegistrar.class}
)
public class ConsumerCacheAutoConfiguration {

}
