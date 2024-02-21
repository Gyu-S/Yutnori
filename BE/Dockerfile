FROM openjdk:17-alpine
ENV JASYPT_KEY gumissafy
EXPOSE 5000
ARG JAR_FILE=BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
