FROM openjdk:17
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar","/app.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
EXPOSE 12345
