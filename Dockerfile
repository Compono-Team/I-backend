FROM openjdk:17.0.2-jdk
ARG JAR_FILE_PATH=build/libs/*.jar
ARG ARG_PROFILE=dev
ENV PROFILE=${ARG_PROFILE}
COPY $JAR_FILE_PATH app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "app.jar"]
