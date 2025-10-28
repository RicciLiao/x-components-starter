package ricciliao.x.starter.cache;


import org.springframework.http.HttpMethod;
import ricciliao.x.cache.ConsumerStore;
import ricciliao.x.cache.XCacheConstants;
import ricciliao.x.component.props.ApplicationProperties;
import ricciliao.x.component.rest.RestPathProperties;

import java.util.ArrayList;
import java.util.List;

public class ConsumerCacheProperties extends ApplicationProperties {

    private String consumer;
    private List<OperationProperties> operationList = new ArrayList<>();

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public List<OperationProperties> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<OperationProperties> operationList) {
        this.operationList = operationList;
    }

    public static class OperationProperties {

        private String store;
        private Class<? extends ConsumerStore> storeClassName;
        private CacheRestPathProperties create = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH, HttpMethod.POST);
        private CacheRestPathProperties update = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH, HttpMethod.PUT);
        private CacheRestPathProperties delete = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/{id}", HttpMethod.DELETE);
        private CacheRestPathProperties get = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/{id}", HttpMethod.GET);
        private CacheRestPathProperties batchCreate = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/batch", HttpMethod.POST);
        private CacheRestPathProperties batchDelete = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/batch", HttpMethod.DELETE);
        private CacheRestPathProperties list = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/list", HttpMethod.POST);
        private CacheRestPathProperties providerInfo = new CacheRestPathProperties(XCacheConstants.DEFAULT_PROVIDER_OPERATION_PATH + "/extra/providerInfo", HttpMethod.GET);

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

        public CacheRestPathProperties getProviderInfo() {
            return providerInfo;
        }

        public void setProviderInfo(CacheRestPathProperties providerInfo) {
            this.providerInfo = providerInfo;
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

        public Class<? extends ConsumerStore> getStoreClassName() {
            return storeClassName;
        }

        public void setStoreClassName(Class<? extends ConsumerStore> storeClassName) {
            this.storeClassName = storeClassName;
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
