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

Before building and deploying these services, the following configuration files must be created and updated. For each file, replace `[AI_KEY]` with the key for your Application Insights instance. Also, any value tokenized as `[UPDATE]` must be replaced with actual values that match your environment.

#### ApplicationInsights.json (both services)

Directories: `services/inventory`, `services/orders`

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

#### application.properties (Inventory)

Directory: `services/inventory/src/main/resources`

```bash
## This is needed for app map to show the service name
spring.application.name=inventory-service

spring.jms.servicebus.connection-string=Endpoint=sb://[UPDATE].servicebus.windows.net/;SharedAccessKeyName=order-receiver;SharedAccessKey=[UPDATE];EntityPath=orders
spring.jms.servicebus.topic-client-id=[UPDATE]
spring.jms.servicebus.idle-timeout=1800000
```

#### application.properties (Orders)

Directory: `services/orders/src/main/resources`

```bash
## This is needed for app map to show the service name
spring.application.name=order-service

# COSMOS DB
# Specify the DNS URI of your Azure Cosmos DB.
azure.cosmosdb.uri=https://[UPDATE].documents.azure.com:443/
# Specify the access key for your database.
azure.cosmosdb.key=[UPDATE]
# Specify the name of your database.
azure.cosmosdb.database=orders

# SERVICE BUS TOPIC
spring.jms.servicebus.connection-string=Endpoint=sb://[UPDATE].servicebus.windows.net/;SharedAccessKeyName=order-producer;SharedAccessKey=[UPDATE];EntityPath=orders
spring.jms.servicebus.idle-timeout=1800000
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

See detailed instructions for deploying these services to your AKS environment [here](../deployment/README.MD).
