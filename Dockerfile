FROM openjdk:8-jdk-alpine
COPY ./target/motorent-0.0.1-SNAPSHOT.jar /home/tpm/docker/motorent_test/
WORKDIR /home/tpm/docker/motorent_test
EXPOSE 8888
CMD ["java", "-jar", "motorent-0.0.1-SNAPSHOT.jar"]