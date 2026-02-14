package ricciliao.x.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AutoConfiguration
@EnableConfigurationProperties
@Conditional(PropsAutoConfigurationCondition.class)
@Import(value = {})
public @interface PropsAutoConfiguration {

    @AliasFor(annotation = AutoConfiguration.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = AutoConfiguration.class, attribute = "before")
    Class<?>[] before() default {};

    @AliasFor(annotation = AutoConfiguration.class, attribute = "beforeName")
    String[] beforeName() default {};

    @AliasFor(annotation = AutoConfiguration.class, attribute = "after")
    Class<?>[] after() default {};

    @AliasFor(annotation = AutoConfiguration.class, attribute = "afterName")
    String[] afterName() default {};

    @AliasFor(annotation = EnableConfigurationProperties.class, attribute = "value")
    Class<?>[] properties();

    @AliasFor(annotation = Import.class, attribute = "value")
    Class<?>[] imports() default {};

    String[] conditionalOnProperties() default {};

}
