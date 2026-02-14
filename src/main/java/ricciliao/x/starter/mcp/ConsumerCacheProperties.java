package ricciliao.x.starter.mcp;


import org.springframework.http.HttpMethod;
import ricciliao.x.component.props.ApplicationProperties;
import ricciliao.x.component.rest.RestPathProperties;
import ricciliao.x.mcp.ConsumerCacheData;
import ricciliao.x.mcp.McpConstants;

import java.util.ArrayList;
import java.util.List;

public class ConsumerCacheProperties implements ApplicationProperties {

    private String url = McpConstants.DEFAULT_PROVIDER_OPERATION_PATH;
    private List<OperationProperties> operationList = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<OperationProperties> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<OperationProperties> operationList) {
        this.operationList = operationList;
    }

    public static class OperationProperties {

        private String store;
        private Class<? extends ConsumerCacheData> dataType;
        private CacheRestPathProperties create = new CacheRestPathProperties("", HttpMethod.POST);
        private CacheRestPathProperties update = new CacheRestPathProperties("", HttpMethod.PUT);
        private CacheRestPathProperties delete = new CacheRestPathProperties("/{id}", HttpMethod.DELETE);
        private CacheRestPathProperties get = new CacheRestPathProperties("/{id}", HttpMethod.GET);
        private CacheRestPathProperties batchCreate = new CacheRestPathProperties("/batch", HttpMethod.POST);
        private CacheRestPathProperties batchDelete = new CacheRestPathProperties("/batch", HttpMethod.DELETE);
        private CacheRestPathProperties list = new CacheRestPathProperties("/list", HttpMethod.POST);
        private CacheRestPathProperties info = new CacheRestPathProperties("/extra/info", HttpMethod.GET);

        public CacheRestPathProperties getBatchDelete() {
            return batchDelete;
        }

        public void setBatchDelete(CacheRestPathProperties batchDelete) {
            this.batchDelete = batchDelete;
        }

        public CacheRestPathProperties getList() {
            return list;
        }

        public void setList(CacheRestPathProperties list) {
            this.list = list;
        }

        public CacheRestPathProperties getInfo() {
            return info;
        }

        public void setInfo(CacheRestPathProperties info) {
            this.info = info;
        }

        public CacheRestPathProperties getBatchCreate() {
            return batchCreate;
        }

        public void setBatchCreate(CacheRestPathProperties batchCreate) {
            this.batchCreate = batchCreate;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public Class<? extends ConsumerCacheData> getDataType() {
            return dataType;
        }

        public void setDataType(Class<? extends ConsumerCacheData> dataType) {
            this.dataType = dataType;
        }

        public CacheRestPathProperties getCreate() {
            return create;
        }

        public void setCreate(CacheRestPathProperties create) {
            this.create = create;
        }

        public CacheRestPathProperties getUpdate() {
            return update;
        }

        public void setUpdate(CacheRestPathProperties update) {
            this.update = update;
        }

        public CacheRestPathProperties getDelete() {
            return delete;
        }

        public void setDelete(CacheRestPathProperties delete) {
            this.delete = delete;
        }

        public CacheRestPathProperties getGet() {
            return get;
        }

        public void setGet(CacheRestPathProperties get) {
            this.get = get;
        }

        public static class CacheRestPathProperties extends RestPathProperties {

            public CacheRestPathProperties() {
                //default constructor
            }

            public CacheRestPathProperties(String path, HttpMethod method) {
                super();
                this.setPath(path);
                this.setMethod(new HttpMethodWrapper(method.name()));
            }
        }
    }

}
