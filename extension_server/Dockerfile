FROM openjdk:18
EXPOSE 8761
EXPOSE 8081
RUN mkdir "/usr/src/vs_code_extension_server"
COPY start.sh /usr/src/vs_code_extension_server
COPY client-listener/target/client-listener-0.0.1-SNAPSHOT.jar /usr/src/vs_code_extension_server
COPY api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar /usr/src/vs_code_extension_server
COPY eureka-server/target/eureka_server-0.0.1-SNAPSHOT.jar /usr/src/vs_code_extension_server
WORKDIR /usr/src/vs_code_extension_server
CMD ["bash", "start.sh"]