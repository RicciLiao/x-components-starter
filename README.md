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

This **Starter** is base on spring starter design and integrates a series of x-components,
you can config the components properties in your `application.yml`,
then the starter will auto define components by your properties,
it makes easier to use components to you.

#### Components

1. `dynamic-aop-component`
2. `mcp-component` *(for consumer)*
3. `common-component`
    - kafka

---

#### 🚩 dynamic-aop-component

*Please refer to `x-dynamic-aop-component`*

#### 📝 Configuration

```yaml
ricciliao:
  x:
    dynamic-aspect:
      aspect-list:
        - expression: #AspectJ pointcut expression.
          aspect: #Fully qualified name of the AspectJ which must extends {@link ricciliao.x.aop.DynamicAspect}.
          bean-name: #Unique AspectJ bean name.
```

---

#### 🚩 mcp-component

*Please refer to `x-mcp-components`*

***Just for the consumer which use **Cache Provider** as data store.***

#### 📝 Configuration

```yaml
ricciliao:
  x:
    mcp:
      url: #MCP service interface address. Defaults to {@value ricciliao.x.mcp.McpConstants#DEFAULT_PROVIDER_OPERATION_PATH}.
      operation-list:
        - store: #Identification of the cache data. The metadata of {@link ricciliao.x.mcp.McpIdentifier}.
          data-type: #Fully qualified name of the cache data type which must extends {@link ricciliao.x.mcp.ConsumerCacheData}.
          create: #Endpoint and http method for creating cache. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("", HttpMethod.POST)}.
            path:
            method:
          update: #Endpoint and http method for updating cache. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("", HttpMethod.PUT)}.
            path:
            method:
          delete: #Endpoint and http method for deleting cache. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("", HttpMethod.DELETE)}.
            path:
            method:
          get: #Endpoint and http method for querying cache.This Endpoint only return a single of data. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("", HttpMethod.GET)}.
            path:
            method:
          batchCreate: #Endpoint and http method for batch creating cache. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("/batch", HttpMethod.POST)}.
            path:
            method:
          batchDelete: #Endpoint and http method for batch deleting cache. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("/batch", HttpMethod.DELETE)}.
            path:
            method:
          list: #Endpoint and http method for batch querying cache. This Endpoint can return a batch of data. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("/list", HttpMethod.POST)}.
            path:
            method:
          info: #Endpoint and http method for batch querying MCP information. Defaults to {@code new ricciliao.x.mcp.ConsumerCacheData("/extra/info", HttpMethod.GET)}.
            path:
            method:
```

The **Starter** will auto register spring bean by your configuration, for example:

```yaml
ricciliao:
  x:
    mcp:
      operation-list:
        - store: apple
          data-type: com.example.Apple
        - store: orange
          data-type: com.example.Orange
```

then you can inject the spring bean with name, like:

```java
private ConsumerCacheRestService<Apple> appleConsumerCacheRestService;
private ConsumerCacheRestService<Orange> orangeConsumerCacheRestService;

@Qualifier("appleConsumerCacheRestService")
@Autowired
public void setAppleConsumerCacheRestService(ConsumerCacheRestService<Apple> appleConsumerCacheRestService) {
    this.appleConsumerCacheRestService = appleConsumerCacheRestService;
}

@Qualifier("orangeConsumerCacheRestService")
@Autowired
public void setOrangeConsumerCacheRestService(ConsumerCacheRestService<Orange> orangeConsumerCacheRestService) {
    this.orangeConsumerCacheRestService = orangeConsumerCacheRestService;
}
```

**Spring bean name is composed of `ricciliao.x.mcp.operation-list[].store` and ConsumerCacheRestService.**

---

#### 🚩 common-component

*please refer to `x-common-components`*

#### 📝 Configuration

**Common**

```yaml
ricciliao:
  x:
    common:
      consumer: #Application unique name.
      crypto-password:
      springdoc:
        contact:
        license:
        enable:
        title:
        description:
        summary:
```

---

**Kafka**

- #### Consumer

    - #### 📝 Configuration

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

- #### Producer

    - #### 📝 Configuration

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

#### KafkaProducerAutoProperties.class

* `producerList`: your kafka producer list.

#### Producer.class

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
