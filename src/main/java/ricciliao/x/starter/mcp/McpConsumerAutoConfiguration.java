package ricciliao.x.starter.mcp;

import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = McpConsumerAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.mcp.operation-list[0].store",
        imports = {McpConsumerDefinitionRegistrar.class}
)
public class McpConsumerAutoConfiguration {

}
