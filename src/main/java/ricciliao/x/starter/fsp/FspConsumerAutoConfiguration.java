package ricciliao.x.starter.fsp;

import ricciliao.x.starter.PropsAutoConfiguration;
import ricciliao.x.starter.mcp.McpConsumerAutoProperties;

@PropsAutoConfiguration(
        properties = McpConsumerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.fsp.enabled",
        imports = {FspConsumerDefinitionRegistrar.class}
)
public class FspConsumerAutoConfiguration {

}
