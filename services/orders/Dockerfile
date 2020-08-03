FROM openjdk:11.0.1-jre-slim

# Add App Insights Java Agent
COPY applicationinsights-agent-3.0.0-PREVIEW.5.jar /usr/share/orders/applicationinsights-agent.jar
COPY ApplicationInsights.json /usr/share/orders/ApplicationInsights.json

# Configure SSH for App Service
# RUN apt-get update && apt-get install -y openssh-server
# RUN echo 'root:Docker!' | chpasswd
# COPY sshd_config /etc/ssh/

# Copy in the service and startup script
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/orders/service.jar
# COPY startup.sh /scripts/startup.sh
# RUN ["chmod", "+x", "/scripts/startup.sh"]

# Expose the API and SSH
# EXPOSE 8080/tcp 2222
EXPOSE 8080

# ENTRYPOINT ["/scripts/startup.sh"]

# Assume 512MB RAM request limit allocated to container. See: https://github.com/dsyer/spring-boot-memory-blog/blob/main/cf.md
ENTRYPOINT ["java", "-javaagent:/usr/share/orders/applicationinsights-agent.jar", "-jar", "/usr/share/orders/service.jar", "-Xmx382293K", "-XX:MaxMetaspaceSize=64M", "-Xss995K"]
