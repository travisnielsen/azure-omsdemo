#!/bin/bash

/etc/init.d/ssh start
java -javaagent:/usr/share/inventory/applicationinsights-agent.jar -jar /usr/share/inventory/service.jar -Xmx512m -XX:+UseG1GC