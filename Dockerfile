FROM openjdk:8-jdk-alpine
COPY ./target/motorent-1.0.0-SNAPSHOT.jar /home/tpm/docker/motorent/
WORKDIR /home/tpm/docker/motorent
EXPOSE 8888
CMD ["java", "-jar", "motorent-1.0.0-SNAPSHOT.jar"]