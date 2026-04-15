package ricciliao.x.starter.fsp;

import jakarta.annotation.Nullable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import ricciliao.x.component.payload.SimplePayloadData;
import ricciliao.x.component.payload.response.Response;
import ricciliao.x.component.payload.response.ResponseUtils;
import ricciliao.x.component.rest.ResponseVoReferenceUtils;
import ricciliao.x.fsp.FspConstants;
import ricciliao.x.fsp.FspContentDto;
import ricciliao.x.fsp.FspSavingDto;
import ricciliao.x.fsp.FspTokenListDto;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FspConsumerRestService {

    protected final RestClient restClient;
    protected final FspConsumerProperties.OperationProperties props;

    public FspConsumerRestService(FspConsumerProperties.OperationProperties props,
                                  final RestClient restClient) {
        this.restClient = restClient;
        this.props = props;
    }

    @Nullable
    public FspSavingDto create(ByteArrayResource resource) throws RestClientException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.set(FspConstants.REQUEST_NAME_FILE, resource);

        ResponseEntity<Response<FspSavingDto>> response =
                restClient
                        .method(props.getCreate().toHttpMethod())
                        .uri(props.getCreate().getPath())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(FspSavingDto.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public FspSavingDto update(String token, ByteArrayResource resource) throws RestClientException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.set(FspConstants.REQUEST_NAME_TOKEN, token);
        body.set(FspConstants.REQUEST_NAME_FILE, resource);

        ResponseEntity<Response<FspSavingDto>> response =
                restClient
                        .method(props.getUpdate().toHttpMethod())
                        .uri(props.getUpdate().getPath())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(FspSavingDto.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public SimplePayloadData.Bool delete(String token) throws RestClientException {
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getDelete().toHttpMethod())
                        .uri(uriBuilder ->
                                UriComponentsBuilder
                                        .fromUri(uriBuilder.build())
                                        .path(props.getDelete().getPath())
                                        .queryParam(FspConstants.REQUEST_NAME_TOKEN, UriUtils.encode(token, StandardCharsets.UTF_8))
                                        .build(true)
                                        .toUri()
                        )
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    @Nullable
    public FspContentDto get(String token) throws RestClientException {
        ResponseEntity<Response<FspContentDto>> response =
                restClient
                        .method(props.getGet().toHttpMethod())
                        .uri(uriBuilder ->
                                UriComponentsBuilder
                                        .fromUri(uriBuilder.build())
                                        .path(props.getGet().getPath())
                                        .queryParam(FspConstants.REQUEST_NAME_TOKEN, UriUtils.encode(token, StandardCharsets.UTF_8))
                                        .build(true)
                                        .toUri()
                        )
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(FspContentDto.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    public SimplePayloadData.Collection<FspSavingDto> create(List<ByteArrayResource> resourceList) throws RestClientException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        resourceList.forEach(resource -> body.add(FspConstants.REQUEST_NAME_FILE_ARRAY, resource));

        ResponseEntity<Response<SimplePayloadData.Collection<FspSavingDto>>> response =
                restClient
                        .method(props.getBatchCreate().toHttpMethod())
                        .uri(props.getBatchCreate().getPath())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Collection.class, FspSavingDto.class));
        SimplePayloadData.Collection<FspSavingDto> result = ResponseUtils.safetyGetResponseData(response);
        if (Objects.isNull(result)) {

            return SimplePayloadData.of(Collections.emptyList());
        }

        return result;
    }

    public SimplePayloadData.Collection<FspSavingDto> update(List<String> tokenList,
                                                             List<ByteArrayResource> resourceList) throws RestClientException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        resourceList.forEach(resource -> body.add(FspConstants.REQUEST_NAME_FILE_ARRAY, resource));
        body.set(FspConstants.REQUEST_NAME_TOKEN_ARRAY, new HttpEntity<>(tokenList, new LinkedMultiValueMap<>(Map.of("content-type", List.of(MediaType.APPLICATION_JSON_VALUE)))));
        ResponseEntity<Response<SimplePayloadData.Collection<FspSavingDto>>> response =
                restClient
                        .method(props.getBatchUpdate().toHttpMethod())
                        .uri(props.getBatchUpdate().getPath())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Collection.class, FspSavingDto.class));
        SimplePayloadData.Collection<FspSavingDto> result = ResponseUtils.safetyGetResponseData(response);
        if (Objects.isNull(result)) {

            return SimplePayloadData.of(Collections.emptyList());
        }

        return result;
    }

    @Nullable
    public SimplePayloadData.Bool delete(FspTokenListDto dto) throws RestClientException {
        ResponseEntity<Response<SimplePayloadData.Bool>> response =
                restClient
                        .method(props.getBatchDelete().toHttpMethod())
                        .uri(props.getBatchDelete().getPath())
                        .body(dto)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Bool.class));

        return ResponseUtils.safetyGetResponseData(response);
    }

    public SimplePayloadData.Collection<FspContentDto> list(FspTokenListDto dto) {
        ResponseEntity<Response<SimplePayloadData.Collection<FspContentDto>>> response =
                restClient
                        .method(props.getList().toHttpMethod())
                        .uri(props.getList().getPath())
                        .body(dto)
                        .retrieve()
                        .toEntity(ResponseVoReferenceUtils.withGenerics(SimplePayloadData.Collection.class, FspContentDto.class));
        SimplePayloadData.Collection<FspContentDto> result = ResponseUtils.safetyGetResponseData(response);
        if (Objects.isNull(result)) {

            return SimplePayloadData.of(Collections.emptyList());
        }

        return result;
    }

}
