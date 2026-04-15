package ricciliao.x.starter.mcp;

import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ricciliao.x.component.payload.SimplePayloadData;
import ricciliao.x.component.payload.response.Response;
import ricciliao.x.component.payload.response.ResponseUtils;
import ricciliao.x.component.rest.ResponseVoReferenceUtils;
import ricciliao.x.mcp.ConsumerCache;
import ricciliao.x.mcp.ConsumerCacheData;
import ricciliao.x.mcp.McpCacheIdListDto;
import ricciliao.x.mcp.McpIdentifier;
import ricciliao.x.mcp.McpProviderInfo;
import ricciliao.x.mcp.query.McpQuery;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class McpConsumerRestService<T extends ConsumerCacheData> {

    protected final RestClient restClient;
    protected final McpConsumerProperties.OperationProperties props;
    protected final McpIdentifier identifier;
    protected final Class<T> storeClassName;

    public McpConsumerRestService(McpConsumerProperties.OperationProperties props,
                                  McpIdentifier identifier,
                                  Class<T> storeClassName,
                                  final RestClient restClient) {
        this.props = props;
        this.identifier = identifier;
        this.restClient = restClient;
        this.storeClassName = storeClassName;
    }

    @Nullable
    public SimplePayloadData.Str create(ConsumerCache<T> cache) throws RestClientException {
        cache.setUid(cache.getData().generateUid());
        ResponseEntity<Response<SimplePayloadData.Str>> response =
                restClient
                        .method(props.getCreate().toHttpMethod())
                        .uri(props.getCreate().getPath())
                        .body(cache)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Str.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimplePayloadData.Bool update(ConsumerCache<T> cache) throws RestClientException {
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getUpdate().toHttpMethod())
                        .uri(props.getUpdate().getPath())
                        .body(cache)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimplePayloadData.Bool delete(String id) throws RestClientException {
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getDelete().toHttpMethod())
                        .uri(props.getDelete().getPath(), Map.of("id", id))
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

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
    public SimplePayloadData.Bool create(List<ConsumerCache<T>> cacheList) throws RestClientException {
        cacheList.forEach(item -> item.setUid(item.getData().generateUid()));
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getBatchCreate().toHttpMethod())
                        .uri(props.getBatchCreate().getPath())
                        .body(SimplePayloadData.of(cacheList))
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimplePayloadData.Bool delete(McpCacheIdListDto dto) throws RestClientException {
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getBatchDelete().toHttpMethod())
                        .uri(props.getBatchDelete().getPath())
                        .body(dto)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    public SimplePayloadData.Collection<ConsumerCache<T>> list(McpQuery query) {
        ResponseEntity<Response<SimplePayloadData.Collection<ConsumerCache<T>>>> response =
                restClient
                        .method(props.getList().toHttpMethod())
                        .uri(props.getList().getPath())
                        .body(query)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Collection.class, ConsumerCache.class, storeClassName));
        SimplePayloadData.Collection<ConsumerCache<T>> result = ResponseUtils.safetyGetResponseData(response);
        if (Objects.isNull(result)) {

            return SimplePayloadData.of(Collections.emptyList());
        }

        return result;
    }

    @Nullable
    public McpProviderInfo info() throws RestClientException {
        ResponseEntity<Response<McpProviderInfo>> response =
                restClient
                        .method(props.getInfo().toHttpMethod())
                        .uri(props.getInfo().getPath())
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(McpProviderInfo.class));
        ResponseVoReferenceUtils.withGenerics(McpProviderInfo.class);

        return ResponseUtils.safetyGetResponseData(response);
    }

}
