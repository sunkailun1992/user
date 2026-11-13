FROM eclipse-temurin:21-jre
COPY build/libs/user.jar /app/user.jar
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "/app/user.jar"]
