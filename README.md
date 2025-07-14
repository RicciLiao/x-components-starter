# X-Components

## *Starter `🚀️ V1.0.0`*

### 📚 Dependency

Please refer to `dependencies-control-center` for the version number.

| groupId                        | artifactId                | scope    |
|--------------------------------|---------------------------|----------|
| org.springframework.boot       | spring-boot-autoconfigure | compile  |
| ricciliao.x                    | common-components         | compile  |
| ricciliao.x                    | audit-log-component       | compile  |
| org.springframework            | spring-context            | provided |
| ricciliao.x                    | cache-consumer-common     | provided |
| com.fasterxml.jackson.core     | jackson-databind          | provided |
| com.fasterxml.jackson.datatype | jackson-datatype-jsr310   | provided |
| org.springframework            | spring-web                | provided |
| jakarta.servlet                | jakarta.servlet-api       | provided |

### 📌 Usage

The **Starter** base on spring starter design and integrates a series of x-components,
you just need to config the components properties in your `application.yml`,
then the starter will auto define components by your properties,
it makes easier to use components to you.

#### Components

1. `dynamic-aop-component`
2. `cache-component` *(for consumer)*
3. `common-component`
    - kafka

---

#### 🚩 dynamic-aop-component

*Please refer to `x-dynamic-aop-component`*

##### 📝 Configuration

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

    public static class ExpressionAspect {

        private String beanName;
        private String expression;
        private Class<? extends DynamicAspect> aspect;
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

***It is just for the consumer which use **Cache Provider** as data store.***

##### 📝 Configuration

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

        public static class CacheRestPathProperties extends RestPathProperties {
            public CacheRestPathProperties() {
            }

            public CacheRestPathProperties(String path, HttpMethod method) {
                this.setPath(path);
                this.setMethod(new RestPathProperties.HttpMethodWrapper(method.name()));
            }
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

##### 📝 Coding

* ##### ConsumerCacheRestService.class

```java
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
    }

    public ResponseSimpleData.Bool update(ConsumerOp.Single<T> operation) throws RestClientException {
    }

    public ResponseSimpleData.Bool delete(String id) throws RestClientException {
    }

    public ConsumerOp.Single<T> get(String id) throws RestClientException {
    }

    public ResponseSimpleData.Bool batchCreate(ConsumerOp.Batch<T> operation) throws RestClientException {
    }

    public ResponseSimpleData.Bool batchDelete(CacheBatchQuery query) throws RestClientException {
    }

    public ConsumerOp.Batch<T> list(CacheBatchQuery query) {
    }

    public ProviderInfo providerInfo() throws RestClientException {
    }

}
```

Cache CURD operation class for consumer.

---

* ##### ConsumerCacheRestServiceFactoryBean.class

```java
public class ConsumerCacheRestServiceFactoryBean<T extends ConsumerCacheData> implements FactoryBean<ConsumerCacheRestService<T>>, ApplicationContextAware {

    private final String consumer;
    private final ConsumerCacheProperties.OperationProperties props;

    private ApplicationContext applicationContext;

    public ConsumerCacheRestServiceFactoryBean(@Nonnull String consumer,
                                               @Nonnull ConsumerCacheProperties.OperationProperties props) {
        this.consumer = consumer;
        this.props = props;
    }

    @Override
    public ConsumerCacheRestService<T> getObject() {
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);

        return new ConsumerCacheRestService<>(
                props,
                new ConsumerIdentifier(consumer, props.getStore()),
                restTemplate,
                (Class<T>) props.getStoreClassName()
        );
    }

    @Override
    public Class<?> getObjectType() {

        return ConsumerCacheRestService.class;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```

Spring bean registration for **ConsumerCacheRestService**, spring name will be `props.getStore()`
+ConsumerCacheRestService,
you can inject that with name, like:

```java
private ConsumerCacheRestService<MessageCodeCacheDto> codeConsumerCacheRestService;

@Qualifier("codeConsumerCacheRestService")
@Autowired
public void setCodeConsumerCacheRestService(ConsumerCacheRestService<MessageCodeCacheDto> codeConsumerCacheRestService) {
    this.codeConsumerCacheRestService = codeConsumerCacheRestService;
}
```

---

#### 🚩 common-component

*please refer to `x-common-components`*

##### 📝 Configuration

- **Common**

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
}
```

##### CommonProperties.class

* `timeZone`: the ObjectMapper time zone.

---

- **Kafka**

##### Consumer

##### 📝 Configuration

```yaml
ricciliao:
  x:
    kafka:
      consumer:
        consumer-list:
          - topic:
            group:
            handler:
            bean-name:
```

```java
public class KafkaConsumerAutoProperties extends ApplicationProperties {

    private List<Consumer> consumerList;

    public static class Consumer {
        private String topic;
        private String group;
        private Class<KafkaHandler<KafkaMessageDto>> handler;
        private String beanName;
    }
}
```

##### KafkaConsumerAutoProperties.class

* `consumerList`: your kafka consumer list.

##### Consumer.class

* `topic`: your kafka consumer topic.
* `group`: your kafka consumer group.
* `handler`: your kafka consumer message handler class, it should be implemented with `KafkaHandler.class`.
* `beanName`: your kafka consumer message handler bean name.

---

##### Producer

##### 📝 Configuration

```yaml
ricciliao:
  x:
    kafka:
      provider:
        provider-list:
          - topic:
            message-class:
            bean-name: 
```

```java
public class KafkaProducerAutoProperties extends ApplicationProperties {

    private List<Producer> producerList;

    public static class Producer {
        private String topic;
        private Class<KafkaMessageDto> messageClass;
        private String beanName;
    }
}
```

##### KafkaProducerAutoProperties.class

* `producerList`: your kafka producer list.

##### Producer.class

* `topic`: your kafka producer topic.
* `beanName`: your kafka producer bean name.
* `messageClass`: your kafka producer message POJO class, it should be implemented with `KafkaMessageDto.class`.

---

#### 🚩 audit-log-component

*please refer to `x-audit-log-component`*

📝 Configuration

```yaml
ricciliao:
  x:
    log:
      executor:
        enable:
        corePoolSize:
        maxPoolSize:
        keepAliveSeconds:
        queueCapacity:
        threadNamePrefix:

```

```java
public class AuditLogAutoProperties extends ApplicationProperties {
    private Executor executor;

    public static class Executor {
        private Boolean enable = Boolean.FALSE;
        private Integer corePoolSize = 1;
        private Integer maxPoolSize = Integer.MAX_VALUE;
        private Integer keepAliveSeconds = 60;
        private Integer queueCapacity = Integer.MAX_VALUE;
        private String threadNamePrefix = "MDC Executor - ";
    }
}
```

---

🤖 Best wishes and enjoy it ~~
