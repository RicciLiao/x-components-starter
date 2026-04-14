package ricciliao.x.starter.aop;


import org.springframework.boot.context.properties.ConfigurationProperties;
import ricciliao.x.aop.DynamicAspect;
import ricciliao.x.component.props.ApplicationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("ricciliao.x.dynamic-aspect")
public class DynamicAspectAutoProperties implements ApplicationProperties {

    private List<ExpressionAspect> aspectList = new ArrayList<>();

    public List<ExpressionAspect> getAspectList() {
        return aspectList;
    }

    public void setAspectList(List<ExpressionAspect> aspectList) {
        this.aspectList = aspectList;
    }

    public static class ExpressionAspect {

        /**
         * Unique AspectJ bean name.
         */
        private String beanName;
        /**
         * AspectJ pointcut expression.
         */
        private String expression;
        /**
         * Fully qualified name of the AspectJ which must extends {@link ricciliao.x.aop.DynamicAspect}.
         */
        private Class<? extends DynamicAspect> aspect;

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public Class<? extends DynamicAspect> getAspect() {
            return aspect;
        }

        public void setAspect(Class<? extends DynamicAspect> aspect) {
            this.aspect = aspect;
        }
    }
}
