# X-Components

## *Starter `🚀️ V1.0.0`*

### 📚 Dependency

Please refer to `dependencies-control-center` for the version number.

| groupId                        | artifactId                       | scope    |
|--------------------------------|----------------------------------|----------|
| org.springframework.boot       | spring-boot-autoconfigure        | compile  |
| org.springdoc                  | springdoc-openapi-starter-common | compile  |
| ricciliao.x                    | common-components                | compile  |
| ricciliao.x                    | audit-logging-component          | compile  |
| ricciliao.x                    | fsp-component                    | compile  |
| ricciliao.x                    | mcp-consumer-common              | provided |
| org.springframework            | spring-context                   | provided |
| com.fasterxml.jackson.core     | jackson-databind                 | provided |
| com.fasterxml.jackson.datatype | jackson-datatype-jsr310          | provided |
| org.springframework            | spring-web                       | provided |
| org.springframework            | spring-webmvc                    | provided |
| jakarta.servlet                | jakarta.servlet-api              | provided |
| org.springframework.kafka      | spring-kafka                     | provided |
| org.springframework.boot       | spring-boot-starter-actuator     | provided |

### 📌 Usage

This **Starter** is base on spring starter design and integrates a series of x-components,
you can config the components properties in your `application.yml`,
then the starter will auto define components by your properties,
it makes easier to use components for you.

#### Components

1. `dynamic-aop-component`
2. `mcp-component` *(for consumer)*
3. `audit-logging-component`
4. `fsp-component` *(for consumer)*
5. `common-component`
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

then you can inject the spring bean by name, like:

```java
private McpConsumerRestService<Apple> appleMcpConsumerRestService;
private McpConsumerRestService<Orange> orangeMcpConsumerRestService;

@Qualifier("appleMcpConsumerRestService")
@Autowired
public void setAppleMcpConsumerRestService(McpConsumerRestService<Apple> appleMcpConsumerRestService) {
    this.appleMcpConsumerRestService = appleMcpConsumerRestService;
}

@Qualifier("orangeMcpConsumerRestService")
@Autowired
public void setOrangeMcpConsumerRestService(McpConsumerRestService<Orange> orangeMcpConsumerRestService) {
    this.orangeMcpConsumerRestService = orangeMcpConsumerRestService;
}
```

**Spring bean name is composed of `ricciliao.x.mcp.operation-list[].store` and `McpConsumerRestService.class.getSimpleName()`.**

---

#### 🚩 audit-logging-component

*please refer to `x-audit-logging-component`*

#### 📝 Configuration

```yaml
ricciliao:
  x:
    log:
      enable: #Defaults to false
      corePoolSize: #Defaults to 1
      maxPoolSize: #Defaults to Integer.MAX_VALUE
      keepAliveSeconds: #Defaults to 60
      queueCapacity: #Defaults to Integer.MAX_VALUE
      threadNamePrefix: #Defaults to "MDC Executor - "
```

---

#### 🚩 fsp-component

***Just for the consumer which use **File Storage Provider** as file store.***

#### 📝 Configuration

```yaml
ricciliao:
  x:
    fsp:
      enable: #Default false
      url:  #FSP service interface address.Defaults to {@value FspConstants#DEFAULT_PROVIDER_FILE_PATH}
      operation:
        create: #Endpoint and http method for creating file.Defaults to {@code new FspRestPathProperties("", HttpMethod.POST)}.
          path:
          method:
        update: #Endpoint and http method for updating file.Defaults to {@code new FspRestPathProperties("", HttpMethod.PUT)}.
          path:
          method:
        delete: #Endpoint and http method for deleting file.Defaults to {@code new FspRestPathProperties("", HttpMethod.DELETE)}.
          path:
          method:
        get: #Endpoint and http method for querying file.This Endpoint only return a single file.Defaults to {@code new FspRestPathProperties("", HttpMethod.GET)}.
          path:
          method:
        batchCreate: #Endpoint and http method for batch creating file.Defaults to {@code new FspRestPathProperties("/batch", HttpMethod.POST)}.
          path:
          method:
        batchUpdate: #Endpoint and http method for batch updating file.Defaults to {@code new FspRestPathProperties("/batch", HttpMethod.PUT)}.
          path:
          method:
        batchDelete: #Endpoint and http method for batch deleting file.Defaults to {@code new CacheRestPathProperties("/batch/delete", HttpMethod.POST)}.
          path:
          method:
        list: #Endpoint and http method for batch querying file.This Endpoint can return a batch file.Defaults to {@code new CacheRestPathProperties("/list", HttpMethod.POST)}.
          path:
          method:
```

The **Starter** will auto register spring bean by your configuration, for example:

```yaml
ricciliao:
  x:
    fsp:
      enabled: true
```

then you can inject the spring bean by type, like:

```java
private FspConsumerRestService fspConsumerRestService;

@Autowired
public void setFspConsumerRestService(FspConsumerRestService fspConsumerRestService) {
    this.fspConsumerRestService = fspConsumerRestService;
}
```

**Spring bean name is `FspConsumerRestService.class.getSimpleName()`, and autowire mode is `AbstractBeanDefinition.AUTOWIRE_BY_TYPE`.**


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
        consumer-list:  #Kafka consumer list.
          - topic:  #Kafka consumer topic.
            group:  #Kafka consumer group.
            handler:  #Kafka consumer message handler class, it should be implemented with {@link ricciliao.x.component.kafka.KafkaConsumerHandler}.
```

- #### Producer

    - #### 📝 Configuration

```yaml
ricciliao:
  x:
    kafka:
      provider:
        provider-list:  #Kafka producer list.
          - topic:  #Kafka producer topic.
            message-class:  #Kafka producer message POJO class, it should be implemented with {@link ricciliao.x.component.kafka.KafkaMessageDto}.
```

---

🤖 Best wishes and enjoy it ~~
