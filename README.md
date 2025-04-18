# X-Components

## *Starter `🚀️ V1.0.0`*

### 📚 Dependency

Please refer to `dependencies-control-center` for the version number.

| groupId                        | artifactId                | scope    |
|--------------------------------|---------------------------|----------|
| org.springframework.boot       | spring-boot-autoconfigure | compile  |
| ricciliao.x                    | common-components         | compile  |
| org.springframework            | spring-context            | provided |
| ricciliao.x                    | cache-consumer-common     | provided |
| com.fasterxml.jackson.core     | jackson-databind          | provided |
| com.fasterxml.jackson.datatype | jackson-datatype-jsr310   | provided |
| org.springframework            | spring-web                | provided |

### 📌 Usage

The **Starter** base on spring starter design and contains a series of x-component starter,
you just need to config components properties in your `application.yml`,
it makes easier to use it.

#### Components

1. `dynamic-aop-component`
2. `cache-component` *(for consumer)*
3. `common-component`

### 📝 Configuration

#### 🚩 dynamic-aop-component

*Please refer to `x-dynamic-aop-component`*

```yaml
ricciliao:
  x:
    dynamic-aspect:
      aspect-list:
        - expression:
          aspect:
          bean-name: 
```

```java
public class DynamicAspectAutoProperties {

    private List<ExpressionAspect> aspectList = new ArrayList<>();
    //getter
    //setter

    public static class ExpressionAspect {

        private String beanName;
        private String expression;
        private Class<? extends DynamicAspect> aspect;
        //getter
        //setter

    }
}
```

##### ExpressionAspect.class

* `beanName`: your unique aspect bean name.
* `expression`: AspectJ pointcut expression
* `aspect`: your aspect class, must extends `DynamicAspect.class`

---

#### 🚩 cache-component

*Please refer to `x-cache-component`*

It is just for the consumer which use **Cache Provider** as data store.

```yaml
ricciliao:
  x:
    cache-consumer:
      consumer:
      operation-list:
        - store:
          store-class-name:
          create:
            path:
            method:
              name:
          update:
            path:
            method:
              name:
          delete:
            path:
            method:
              name:
          get:
            path:
            method:
              name:
          batchCreate:
            path:
            method:
              name:
          list:
            path:
            method:
              name:
          providerInfo:
            path:
            method:
              name: 
```

```java
public class ConsumerCacheAutoProperties extends ConsumerCacheProperties {
}
```

```java
public class ConsumerCacheProperties {
    private String consumer;
    private List<OperationProperties> operationList = new ArrayList();

    //getter
    //setter
    public static class OperationProperties {
        private String store;
        private Class<CacheDto> storeClassName;
        private CacheRestPathProperties create;
        private CacheRestPathProperties update;
        private CacheRestPathProperties delete;
        private CacheRestPathProperties get;
        private CacheRestPathProperties batchCreate;
        private CacheRestPathProperties list;
        private CacheRestPathProperties providerInfo;

        public OperationProperties() {
            this.create = new CacheRestPathProperties("/operation", HttpMethod.POST);
            this.update = new CacheRestPathProperties("/operation", HttpMethod.PUT);
            this.delete = new CacheRestPathProperties("operation/{id}", HttpMethod.DELETE);
            this.get = new CacheRestPathProperties("/operation/{id}", HttpMethod.GET);
            this.batchCreate = new CacheRestPathProperties("/operation/batch", HttpMethod.POST);
            this.list = new CacheRestPathProperties("/operation/list", HttpMethod.POST);
            this.providerInfo = new CacheRestPathProperties("/operation/extra/providerInfo", HttpMethod.GET);
        }

        //getter
        //setter
        public static class CacheRestPathProperties extends RestPathProperties {
            public CacheRestPathProperties() {
            }

            public CacheRestPathProperties(String path, HttpMethod method) {
                this.setPath(path);
                this.setMethod(new RestPathProperties.HttpMethodWrapper(method.name()));
            }
            //getter
            //setter
        }
    }
}
```

##### ConsumerCacheProperties.java

* `consumer`: define the identity code of service which use **Cache Provider**,
  it must same code as **Cache Provider**.

##### OperationProperties .java

* `store`: define the identity code of consumer data,
  it must same code as **Cache Provider**.
* `storeClassName`: your data POJO class,
  it must extends `CacheDto.class` and same class as **Cache Provider**.
* `create`:
    * `path`: interface path for creating data which in **Cache Provider**.
    * `method`: http method for this interface.
* `update `:
    * `path`: interface path for updating data which in **Cache Provider**.
    * `method`: http method for this interface.
* `delete `:
    * `path`: interface path for deleting data which in **Cache Provider**.
    * `method`: http method for this interface.
* `get `:
    * `path`: interface path for retrieving data by ID which in **Cache Provider**.
    * `method`: http method for this interface.
* `batchCreate `:
    * `path`: interface path for creating batch data which in **Cache Provider**.
    * `method`: http method for this interface.
* `list `:
    * `path`: interface path for retrieving data list which in **Cache Provider**.
    * `method`: http method for this interface.
* `providerInfo `:
    * `path`: interface path for retrieving provider information which in **Cache Provider**.
    * `method`: http method for this interface.

---

#### 🚩 common-component

*please refer to `x-common-components`*

```yaml
ricciliao:
  x:
    common:
      time-zone:
```

```java
public class CommonAutoProperties extends CommonProperties {
}
```

```java
public class CommonProperties {
    private TimeZone timeZone;
    //getter
    //setter
}
```

##### CommonProperties.class

* `timeZone`: the ObjectMapper time zone.

---

🤖 Best wishes and enjoy it ~~