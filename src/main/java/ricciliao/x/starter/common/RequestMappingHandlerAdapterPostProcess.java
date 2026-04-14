package ricciliao.x.starter.common;

import jakarta.annotation.Nonnull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import ricciliao.x.component.beans.TypedLifecycleBeanPostProcessor;
import ricciliao.x.component.payload.response.ResponseHandler;
import ricciliao.x.component.payload.response.ResponseHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class RequestMappingHandlerAdapterPostProcess extends TypedLifecycleBeanPostProcessor<RequestMappingHandlerAdapter> {

    private final ObjectProvider<ResponseHttpMessageConverter> responseHttpMessageConverter;

    /**
     * @param responseHttpMessageConverter must be an {@link BeanPostProcessor}, lazy injection.
     */
    public RequestMappingHandlerAdapterPostProcess(ObjectProvider<ResponseHttpMessageConverter> responseHttpMessageConverter) {
        this.responseHttpMessageConverter = responseHttpMessageConverter;
    }

    @Override
    public boolean supports() {

        return true;
    }

    @Override
    public Class<RequestMappingHandlerAdapter> getBeanType() {

        return RequestMappingHandlerAdapter.class;
    }

    @Override
    public RequestMappingHandlerAdapter afterInitialization(@Nonnull RequestMappingHandlerAdapter bean, @Nonnull String beanName) {
        List<HandlerMethodReturnValueHandler> originalHandlers = bean.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(originalHandlers)) {
            newHandlers.addAll(originalHandlers);
        }
        newHandlers.addFirst(new ResponseHandler(responseHttpMessageConverter.getIfAvailable()));
        bean.setReturnValueHandlers(newHandlers);

        return bean;
    }
}
