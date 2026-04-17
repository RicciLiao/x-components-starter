package ricciliao.x.starter.fsp;

import org.springframework.http.HttpMethod;
import ricciliao.x.component.props.ApplicationProperties;
import ricciliao.x.component.rest.RestPathProperties;
import ricciliao.x.fsp.FspConstants;

public class FspConsumerProperties implements ApplicationProperties {

    private boolean enabled = false;
    /**
     * FSP service interface address.
     * Defaults to {@value FspConstants#DEFAULT_PROVIDER_FILE_PATH}
     */
    private String url = FspConstants.DEFAULT_PROVIDER_FILE_PATH;
    private OperationProperties operation = new OperationProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OperationProperties getOperation() {
        return operation;
    }

    public void setOperation(OperationProperties operation) {
        this.operation = operation;
    }

    public static class OperationProperties {
        /**
         * Endpoint and http method for creating file.
         * Defaults to {@code new FspRestPathProperties("", HttpMethod.POST)}.
         */
        private FspRestPathProperties create = new FspRestPathProperties("", HttpMethod.POST);
        /**
         * Endpoint and http method for updating file.
         * Defaults to {@code new FspRestPathProperties("", HttpMethod.PUT)}.
         */
        private FspRestPathProperties update = new FspRestPathProperties("", HttpMethod.PUT);
        /**
         * Endpoint and http method for deleting file.
         * Defaults to {@code new FspRestPathProperties("", HttpMethod.DELETE)}.
         */
        private FspRestPathProperties delete = new FspRestPathProperties("", HttpMethod.DELETE);
        /**
         * Endpoint and http method for querying file.
         * This Endpoint only return a single file.
         * Defaults to {@code new FspRestPathProperties("", HttpMethod.GET)}.
         */
        private FspRestPathProperties get = new FspRestPathProperties("", HttpMethod.GET);
        /**
         * Endpoint and http method for batch creating file.
         * Defaults to {@code new FspRestPathProperties("/batch", HttpMethod.POST)}.
         */
        private FspRestPathProperties batchCreate = new FspRestPathProperties("/batch", HttpMethod.POST);
        /**
         * Endpoint and http method for batch updating file.
         * Defaults to {@code new FspRestPathProperties("/batch", HttpMethod.PUT)}.
         */
        private FspRestPathProperties batchUpdate = new FspRestPathProperties("/batch", HttpMethod.PUT);
        /**
         * Endpoint and http method for batch deleting file.
         * Defaults to {@code new CacheRestPathProperties("/batch/delete", HttpMethod.POST)}.
         */
        private FspRestPathProperties batchDelete = new FspRestPathProperties("/batch/delete", HttpMethod.POST);
        /**
         * Endpoint and http method for batch querying file.
         * This Endpoint can return a batch file.
         * Defaults to {@code new CacheRestPathProperties("/list", HttpMethod.POST)}.
         */
        private FspRestPathProperties list = new FspRestPathProperties("/list", HttpMethod.POST);

        public FspRestPathProperties getCreate() {
            return create;
        }

        public void setCreate(FspRestPathProperties create) {
            this.create = create;
        }

        public FspRestPathProperties getUpdate() {
            return update;
        }

        public void setUpdate(FspRestPathProperties update) {
            this.update = update;
        }

        public FspRestPathProperties getDelete() {
            return delete;
        }

        public void setDelete(FspRestPathProperties delete) {
            this.delete = delete;
        }

        public FspRestPathProperties getGet() {
            return get;
        }

        public void setGet(FspRestPathProperties get) {
            this.get = get;
        }

        public FspRestPathProperties getBatchCreate() {
            return batchCreate;
        }

        public void setBatchCreate(FspRestPathProperties batchCreate) {
            this.batchCreate = batchCreate;
        }

        public FspRestPathProperties getBatchUpdate() {
            return batchUpdate;
        }

        public void setBatchUpdate(FspRestPathProperties batchUpdate) {
            this.batchUpdate = batchUpdate;
        }

        public FspRestPathProperties getBatchDelete() {
            return batchDelete;
        }

        public void setBatchDelete(FspRestPathProperties batchDelete) {
            this.batchDelete = batchDelete;
        }

        public FspRestPathProperties getList() {
            return list;
        }

        public void setList(FspRestPathProperties list) {
            this.list = list;
        }

        public static class FspRestPathProperties extends RestPathProperties {

            public FspRestPathProperties() {
                //default constructor
            }

            /**
             *
             */
            public FspRestPathProperties(String path, HttpMethod method) {
                super();
                this.setPath(path);
                this.setMethod(new HttpMethodWrapper(method.name()));
            }
        }
    }

}
