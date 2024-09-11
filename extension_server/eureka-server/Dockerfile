FROM openjdk:18
EXPOSE 8761
RUN mkdir "/usr/src/vs_code_extension_server"
COPY build/libs/eureka_server-0.0.1-SNAPSHOT.jar /usr/src/vs_code_extension_server
WORKDIR /usr/src/vs_code_extension_server
ENTRYPOINT ["java","-jar","eureka_server-0.0.1-SNAPSHOT.jar"]