package ricciliao.x.starter.common;


import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ricciliao.x.component.response.Response;
import ricciliao.x.component.response.code.Primary;
import ricciliao.x.component.response.code.ResponseCode;
import ricciliao.x.component.response.code.Secondary;
import ricciliao.x.component.response.code.impl.PrimaryEnum;
import ricciliao.x.component.response.data.ResponseData;
import ricciliao.x.component.response.data.SimpleData;

import java.io.Serial;
import java.util.Objects;

public class ResponseDataBlankAdvice implements ResponseBodyAdvice<Response<? extends ResponseData>> {

    private final ResponseCode unkownResponseCode;

    public ResponseDataBlankAdvice() {
        this.unkownResponseCode = new ResponseCode() {
            @Serial
            private static final long serialVersionUID = 5384926569899776189L;

            @Override
            public Primary getPrimary() {

                return PrimaryEnum.UNEXPECTED_ERROR;
            }

            @Override
            public Secondary getSecondary() {

                return null;
            }
        };
    }

    @Override
    public boolean supports(@Nonnull MethodParameter returnType,
                            @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {

        return returnType.getParameterType().isAssignableFrom(Response.class);
    }

    @Override
    public Response<? extends ResponseData> beforeBodyWrite(Response<? extends ResponseData> body,
                                                            @Nonnull MethodParameter returnType,
                                                            @Nonnull MediaType selectedContentType,
                                                            @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                                            @Nonnull ServerHttpRequest request,
                                                            @Nonnull ServerHttpResponse response) {
        if (Objects.isNull(body)) {

            return new Response<>(unkownResponseCode, new SimpleData.Blank());
        }
        if (Objects.isNull(body.getData())) {

            return new Response<>(body.getCode(), new SimpleData.Blank());
        }

        return body;
    }

}
