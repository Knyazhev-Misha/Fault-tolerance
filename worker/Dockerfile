FROM openjdk:17

WORKDIR /app

ADD /build/libs/worker-0.0.1-SNAPSHOT.jar MD5-backend.jar

CMD ["java", "-jar", "MD5-backend.jar"]