package ricciliao.x.starter.cache;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ricciliao.x.cache.ConsumerCacheData;
import ricciliao.x.cache.XCacheConstants;
import ricciliao.x.cache.pojo.ConsumerIdentifier;
import ricciliao.x.cache.pojo.ConsumerOp;
import ricciliao.x.cache.pojo.ProviderInfo;
import ricciliao.x.cache.query.CacheBatchQuery;
import ricciliao.x.component.response.ResponseSimpleData;
import ricciliao.x.component.response.ResponseVo;
import ricciliao.x.component.rest.ResponseVoReferenceUtils;

import java.util.Map;
import java.util.Objects;

public class ConsumerCacheRestService<T extends ConsumerCacheData> {

    protected final RestTemplate restTemplate;
    protected final ConsumerCacheProperties.OperationProperties props;
    protected final ConsumerIdentifier identifier;
    protected final Class<T> storeClassName;

    public ConsumerCacheRestService(ConsumerCacheProperties.OperationProperties props,
                                    ConsumerIdentifier identifier,
                                    RestTemplate restTemplate,
                                    Class<T> storeClassName) {
        this.props = props;
        this.identifier = identifier;
        this.restTemplate = restTemplate;
        this.storeClassName = storeClassName;
    }

    public ResponseSimpleData.Str create(ConsumerOp.Single<T> operation) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getCreate().toBuilder();
        ResponseEntity<ResponseVo<ResponseSimpleData.Str>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getCreate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(operation),
                        ResponseVoReferenceUtils.withGenerics(ResponseSimpleData.Str.class)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ResponseSimpleData.Str> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ResponseSimpleData.Bool update(ConsumerOp.Single<T> operation) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getUpdate().toBuilder();
        ResponseEntity<ResponseVo<ResponseSimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getUpdate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(operation),
                        ResponseVoReferenceUtils.withGenerics(ResponseSimpleData.Bool.class)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ResponseSimpleData.Bool> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ResponseSimpleData.Bool delete(String id) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getDelete().toBuilder();
        uriComponentsBuilder.uriVariables(Map.of("id", id));
        ResponseEntity<ResponseVo<ResponseSimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getDelete().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.withGenerics(ResponseSimpleData.Bool.class)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ResponseSimpleData.Bool> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ConsumerOp.Single<T> get(String id) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getGet().toBuilder();
        uriComponentsBuilder.uriVariables(Map.of("id", id));
        ResponseEntity<ResponseVo<ConsumerOp.Single<T>>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getGet().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.forClassWithGenerics(ConsumerOp.Single.class, storeClassName)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ConsumerOp.Single<T>> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ResponseSimpleData.Bool batchCreate(ConsumerOp.Batch<T> operation) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getBatchCreate().toBuilder();
        ResponseEntity<ResponseVo<ResponseSimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getBatchCreate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(operation),
                        ResponseVoReferenceUtils.withGenerics(ResponseSimpleData.Bool.class)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ResponseSimpleData.Bool> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ResponseSimpleData.Bool batchDelete(CacheBatchQuery query) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getBatchDelete().toBuilder();
        ResponseEntity<ResponseVo<ResponseSimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getBatchDelete().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(query),
                        ResponseVoReferenceUtils.withGenerics(ResponseSimpleData.Bool.class)
                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ResponseSimpleData.Bool> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

    public ConsumerOp.Batch<T> list(CacheBatchQuery query) {
        UriComponentsBuilder uriComponentsBuilder = props.getList().toBuilder();
        ResponseEntity<ResponseVo<ConsumerOp.Batch<T>>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getList().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(query),
                        ResponseVoReferenceUtils.forClassWithGenerics(ConsumerOp.Batch.class, storeClassName)

                );
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ConsumerOp.Batch<T>> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }
        return null;
    }

    public ProviderInfo providerInfo() throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getProviderInfo().toBuilder();
        ResponseEntity<ResponseVo<ProviderInfo>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getProviderInfo().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.withGenerics(ProviderInfo.class)
                );
        ResponseVoReferenceUtils.withGenerics(ProviderInfo.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseVo<ProviderInfo> body = response.getBody();
            if (Objects.nonNull(body)) {

                return body.getData();
            }
        }

        return null;
    }

}
