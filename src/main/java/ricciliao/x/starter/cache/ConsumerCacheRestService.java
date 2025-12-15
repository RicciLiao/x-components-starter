package ricciliao.x.starter.cache;

import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ricciliao.x.cache.ConsumerStore;
import ricciliao.x.cache.pojo.ConsumerCache;
import ricciliao.x.cache.pojo.ConsumerOperation;
import ricciliao.x.cache.pojo.ProviderInfo;
import ricciliao.x.cache.pojo.StoreIdentifier;
import ricciliao.x.cache.query.CacheBatchQuery;
import ricciliao.x.component.payload.SimpleData;
import ricciliao.x.component.payload.response.Response;
import ricciliao.x.component.payload.response.ResponseUtils;
import ricciliao.x.component.rest.ResponseVoReferenceUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ConsumerCacheRestService<T extends ConsumerStore> {

    protected final RestClient restClient;
    protected final ConsumerCacheProperties.OperationProperties props;
    protected final StoreIdentifier identifier;
    protected final Class<T> storeClassName;

    public ConsumerCacheRestService(ConsumerCacheProperties.OperationProperties props,
                                    StoreIdentifier identifier,
                                    Class<T> storeClassName,
                                    final RestClient restClient) {
        this.props = props;
        this.identifier = identifier;
        this.restClient = restClient;
        this.storeClassName = storeClassName;
    }

    @Nullable
    public SimpleData.Str create(ConsumerOperation.Single<T> operation) throws RestClientException {
        operation.getData().setCacheKey(operation.getData().getStore().generateCacheKey());
        ResponseEntity<Response<SimpleData.Str>> response =
                restClient
                        .method(props.getCreate().toHttpMethod())
                        .uri(props.getCreate().getPath())
                        .body(operation)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Str.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool update(ConsumerOperation.Single<T> operation) throws RestClientException {
        ResponseEntity<Response<SimpleData.Bool>> response =
                restClient
                        .method(props.getUpdate().toHttpMethod())
                        .uri(props.getUpdate().getPath())
                        .body(operation)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool delete(String id) throws RestClientException {
        ResponseEntity<Response<SimpleData.Bool>> response =
                restClient
                        .method(props.getDelete().toHttpMethod())
                        .uri(props.getDelete().getPath(), Map.of("id", id))
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public ConsumerCache<T> get(String id) throws RestClientException {
        ResponseEntity<Response<ConsumerCache<T>>> response =
                restClient
                        .method(props.getGet().toHttpMethod())
                        .uri(props.getGet().getPath(), Map.of("id", id))
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(ConsumerCache.class, storeClassName));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool batchCreate(ConsumerOperation.Batch<T> operation) throws RestClientException {
        ResponseEntity<Response<SimpleData.Bool>> response =
                restClient
                        .method(props.getBatchCreate().toHttpMethod())
                        .uri(props.getBatchCreate().getPath())
                        .body(operation)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimpleData.Bool batchDelete(CacheBatchQuery query) throws RestClientException {
        ResponseEntity<Response<SimpleData.Bool>> response =
                restClient
                        .method(props.getBatchDelete().toHttpMethod())
                        .uri(props.getBatchDelete().getPath())
                        .body(query)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    public SimpleData.Collection<ConsumerCache<T>> list(CacheBatchQuery query) {
        ResponseEntity<Response<SimpleData.Collection<ConsumerCache<T>>>> response =
                restClient
                        .method(props.getList().toHttpMethod())
                        .uri(props.getList().getPath())
                        .body(query)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimpleData.Collection.class, ConsumerCache.class, storeClassName));
        SimpleData.Collection<ConsumerCache<T>> result = ResponseUtils.safetyGetResponseData(response);
        if (Objects.isNull(result)) {

            return SimpleData.of(Collections.emptyList());
        }

        return result;
    }

    @Nullable
    public ProviderInfo providerInfo() throws RestClientException {
        ResponseEntity<Response<ProviderInfo>> response =
                restClient
                        .method(props.getProviderInfo().toHttpMethod())
                        .uri(props.getProviderInfo().getPath())
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(ProviderInfo.class));
        ResponseVoReferenceUtils.withGenerics(ProviderInfo.class);

        return ResponseUtils.safetyGetResponseData(response);
    }

}
