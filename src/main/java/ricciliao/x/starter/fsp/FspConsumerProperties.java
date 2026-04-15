package ricciliao.x.starter.fsp;

import org.springframework.http.HttpMethod;
import ricciliao.x.component.props.ApplicationProperties;
import ricciliao.x.component.rest.RestPathProperties;

public class FspConsumerProperties implements ApplicationProperties {

    private boolean enabled = false;
    private String url = "http://ricci-fsp-svc:8087/file";
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
        private FspRestPathProperties create = new FspRestPathProperties("", HttpMethod.POST);
        private FspRestPathProperties update = new FspRestPathProperties("", HttpMethod.PUT);
        private FspRestPathProperties delete = new FspRestPathProperties("", HttpMethod.DELETE);
        private FspRestPathProperties get = new FspRestPathProperties("", HttpMethod.GET);
        private FspRestPathProperties batchCreate = new FspRestPathProperties("/batch", HttpMethod.POST);
        private FspRestPathProperties batchUpdate = new FspRestPathProperties("/batch", HttpMethod.PUT);
        private FspRestPathProperties batchDelete = new FspRestPathProperties("/batch/delete", HttpMethod.POST);
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
