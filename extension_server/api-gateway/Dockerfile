FROM openjdk:18
EXPOSE 8761
RUN mkdir "/usr/src/vs_code_extension_server"
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /usr/src/vs_code_extension_server
WORKDIR /usr/src/vs_code_extension_server
ENTRYPOINT ["java","-jar","api-gateway-0.0.1-SNAPSHOT.jar"]
