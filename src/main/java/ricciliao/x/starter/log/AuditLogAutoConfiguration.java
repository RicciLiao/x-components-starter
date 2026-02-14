package ricciliao.x.starter.log;


import ricciliao.x.starter.PropsAutoConfiguration;

@PropsAutoConfiguration(
        properties = AuditLogAutoProperties.class,
        conditionalOnProperties = "ricciliao.x.log.executor.enable",
        imports = {AuditLogDefinitionRegistrar.class}
)
public class AuditLogAutoConfiguration {

}
