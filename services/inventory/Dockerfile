FROM openjdk:11.0.1-jre-slim

# Add App Insights Java Agent
COPY applicationinsights-agent-3.0.0-PREVIEW.5.jar /usr/share/inventory/applicationinsights-agent.jar
COPY ApplicationInsights.json /usr/share/inventory/ApplicationInsights.json

# Add the service itself
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/inventory/service.jar

EXPOSE 8080/tcp
ENTRYPOINT ["java", "-javaagent:/usr/share/inventory/applicationinsights-agent.jar", "-jar", "/usr/share/inventory/service.jar"]
# ENTRYPOINT ["java", "-jar", "/usr/share/inventory/service.jar"]