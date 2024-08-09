FROM amazoncorretto:17-alpine

COPY /book/build/libs/*SNAPSHOT.jar /opt/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
