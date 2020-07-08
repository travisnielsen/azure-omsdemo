# Inventory Service

This is a sample API that simulates an inventory service. It is based on JDK11 and is fully instrumented with Azure Application Insights for complete telemetry. Currently, it uses a combination of agent-based telemetry and the App Insights SDK.

## Prerequisites

To work with this service locally, you must have the following installed and working in your enviornment:

* OpenJDK 11
* Maven 3.6.x
* Docker

Furthermore, you must have an Application Insights instance created with the service key documented.

## Configuration Files

Before building and deploying this service, the following configuration files must be created. For each file, replace `[AI_KEY]` with the key for your Application Insights instance.

### ApplicationInsights.json

Directory: `services/inventory`

```json
{
    "instrumentationSettings": {
      "connectionString": "InstrumentationKey=[AI_KEY]",
      "preview": {
        "roleName": "inventory-service",
        "instrumentation": {
          "micrometer": {
            "enabled": true
          }
        }
      }
    }
}
```

### application.properties

Directory: `services/inventory/src/main/resources`

```bash
azure.application-insights.enabled=true

## Application Insights Instrumentation Key
azure.application-insights.instrumentation-key=[AI_KEY]

## This enables trace level SDK debugging
azure.application-insights.logger.level=trace

## This is needed for app map to show the service name
spring.application.name=fabrkiam-inventory

## Azure Monitor for metrics
management.metrics.export.azuremonitor.instrumentation-key=[AI_KEY]
```

### logback.xml

Directory: `services/inventory/src/main/resources`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <include resource="org/springframework/boot/logging/logback/base.xml"/>

  <appender name="aiAppender" class="com.microsoft.applicationinsights.logback.ApplicationInsightsAppender">
    <instrumentationKey>[AI_KEY]</instrumentationKey>
  </appender>
  
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>
        %d{dd-MM-yyyy HH:mm:ss.SSS} %magenta([%thread]) %highlight(%-5level) %logger{36}.%M - %msg%n
      </pattern>
    </encoder>
  </appender>

  <root level="info">
    <appender-ref ref="aiAppender" />
    <appender-ref ref="STDOUT" />
  </root>
  
  <logger name="com.fabrikam.inventory.controller" level="trace" additivity="false">
      <appender-ref ref="aiAppender" />
      <appender-ref ref="STDOUT" />
  </logger>

</configuration>
```

## Build and Run

To run the serice locally, execute the following command from the `inventory` directory:

```bash
mvn spring-boot:run
```

## Build and Run Docker Container

This project uses a plug-in that automatically creates a local container image via the following Maven comamnd: `mvn install`. Once the command completes, you can run the container and test the service via the auto-assigned port:

```bash
docker run -d -P [your_docker_id]/inventory-service:0.0.7-SNAPSHOT
```

You should be able to reach test URLs such as `http://localhost:[PORT]/dependency` where [PORT] is the assignment made by Docker for local use, which can be found by running the `docker ps` command.

## Publish to Docker Hub

Finally, the image can be published to Docker Hub via [docker push](https://docs.docker.com/engine/reference/commandline/push/)
