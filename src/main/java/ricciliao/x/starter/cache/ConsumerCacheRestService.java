package ricciliao.x.starter.cache;

import jakarta.annotation.Nullable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ricciliao.x.cache.ConsumerCacheData;
import ricciliao.x.cache.XCacheConstants;
import ricciliao.x.cache.pojo.ConsumerCacheStore;
import ricciliao.x.cache.pojo.ConsumerIdentifier;
import ricciliao.x.cache.pojo.ProviderInfo;
import ricciliao.x.cache.query.CacheBatchQuery;
import ricciliao.x.component.response.Response;
import ricciliao.x.component.response.ResponseUtils;
import ricciliao.x.component.response.data.SimpleData;
import ricciliao.x.component.rest.ResponseVoReferenceUtils;

import java.util.List;
import java.util.Map;

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

    @Nullable
    public SimpleData.Str create(ConsumerCacheStore<T> store) throws RestClientException {
        store.setCacheKey(store.getData().generateCacheKey());
        UriComponentsBuilder uriComponentsBuilder = props.getCreate().toBuilder();
        ResponseEntity<Response<SimpleData.Str>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getCreate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(store),
                        ResponseVoReferenceUtils.withGenerics(SimpleData.Str.class)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool update(ConsumerCacheStore<T> store) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getUpdate().toBuilder();
        ResponseEntity<Response<SimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getUpdate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(store),
                        ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool delete(String id) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getDelete().toBuilder();
        uriComponentsBuilder.uriVariables(Map.of("id", id));
        ResponseEntity<Response<SimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getDelete().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public ConsumerCacheStore<T> get(String id) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getGet().toBuilder();
        uriComponentsBuilder.uriVariables(Map.of("id", id));
        ResponseEntity<Response<ConsumerCacheStore<T>>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getGet().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.forClassWithGenerics(ConsumerCacheStore.class, storeClassName)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool batchCreate(List<ConsumerCacheStore<T>> storeList) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getBatchCreate().toBuilder();
        ResponseEntity<Response<SimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getBatchCreate().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(storeList),
                        ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool batchDelete(CacheBatchQuery query) throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getBatchDelete().toBuilder();
        ResponseEntity<Response<SimpleData.Bool>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getBatchDelete().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(query),
                        ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class)
                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public ConsumerCacheStore.Batch<T> list(CacheBatchQuery query) {
        UriComponentsBuilder uriComponentsBuilder = props.getList().toBuilder();
        ResponseEntity<Response<ConsumerCacheStore.Batch<T>>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getList().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .body(query),
                        ResponseVoReferenceUtils.forClassWithGenerics(ConsumerCacheStore.Batch.class, storeClassName)

                );

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public ProviderInfo providerInfo() throws RestClientException {
        UriComponentsBuilder uriComponentsBuilder = props.getProviderInfo().toBuilder();
        ResponseEntity<Response<ProviderInfo>> response =
                restTemplate.exchange(
                        RequestEntity
                                .method(props.getProviderInfo().toHttpMethod(), uriComponentsBuilder.build().encode().toUri())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_STORE, identifier.getStore())
                                .header(XCacheConstants.HTTP_HEADER_FOR_CACHE_CUSTOMER, identifier.getConsumer())
                                .build(),
                        ResponseVoReferenceUtils.withGenerics(ProviderInfo.class)
                );
        ResponseVoReferenceUtils.withGenerics(ProviderInfo.class);

        return ResponseUtils.safetyGetResponseData(response);
    }

}
