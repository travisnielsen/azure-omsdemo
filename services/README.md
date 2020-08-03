# Services

This project includes sample microservices that roughly approximate a sample commerce system.

## Prerequisites

To work with this service locally, you must have the following installed and working in your enviornment:

* OpenJDK 11
* Maven 3.6.x
* Docker
* Helm (version 3)
* Visual Studio Code (recommended)
* AKS deployed (with kubectl configured)

Furthermore, you must have an Application Insights instance created with the service key documented.

## Status Service (NodeJS)



## Inventory and Orders Services (Java Spring Boot)

These services are deployed to Azure Kubernetes Service (AKS) and require some configuration to match your environment.

### Configuration Files (inventory and orders)

Before building and deploying these services, the following configuration files must be created. For each file, replace `[AI_KEY]` with the key for your Application Insights instance. Replace each instance of `[servicename]` with the actual name of the service (i.e. `inventory` or `orders`).

#### ApplicationInsights.json

Directory: `services/[servicename]`

```json
{
    "instrumentationSettings": {
      "connectionString": "InstrumentationKey=[AI_KEY]",
      "preview": {
        "roleName": "[servicename]-service",
        "instrumentation": {
          "micrometer": {
            "enabled": true
          }
        }
      }
    }
}
```

#### application.properties

Directory: `services/[servicename]/src/main/resources`

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

#### logback.xml

Directory: `services/[servicename]/src/main/resources`

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
  
  <logger name="com.fabrikam.[servicename].controller" level="trace" additivity="false">
      <appender-ref ref="aiAppender" />
      <appender-ref ref="STDOUT" />
  </logger>

</configuration>
```

### Build and Run

To run the serice locally, execute the following command from the project root directory of the service:

```bash
mvn spring-boot:run
```

### Build and Run Docker Container

This project uses a plug-in that automatically creates a local container image via the following Maven comamnd: `mvn install`. Once the command completes, you can run the container and test the service via the auto-assigned port:

#### Inventory

```bash
docker run -d -P [your_docker_id]/inventory-service:0.0.3 -m 512m
```

#### Orders

```bash
docker run -d -P -m 512m -e STATUS_URL=https://[your-function-app-name].azurewebsites.net/api/getstatus?code=[your-function-app-code] [your_docker_id]/order-service:0.0.3
```

For testing locally, you should be able to reach test URLs such as `http://localhost:[PORT]/dependency` where [PORT] is the assignment made by Docker for local use, which can be found by running the `docker ps` command.

### Publish images to Docker Hub

Finally, push the images for both the Inventory and Orders to Docker Hub via [docker push](https://docs.docker.com/engine/reference/commandline/push/).

### Deploy workload to AKS

Both services are deployed to AKS via the provided Helm charts. For demonstration and testing purposes, the Orders service is exposed via an Nginx ingress controller. Prior to deployment, make sure the `values.yaml` files for both `orders` and `inventory` match your environment details, such as image names (and tags), as well as the URL for the Status function app. These files can be found in the `deployment\helm\charts\inventory` and `deployment\helm\charts\orders` directories respectively. Once these files are confirmed for correctness, navigate to the `helm` directory and deploy via the following command:

> This applicaiton sample assumes the workload is deployed into a dedicated namespace called 'fabrikam'. Ensure this namespace exists in your AKS cluster. If you chose to deploy to a different namespace, change the following commands accordingly.

```bash
helm install -n fabrikam fabrikam-oms .
```

Use the following command to update the existing deployment:

```bash
helm upgrade -n fabrikam fabrikam-oms .
```
