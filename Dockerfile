FROM java:8-jre
COPY build/libs/gs-spring-boot-0.1.0.jar /opt/app/app.jar
WORKDIR /opt/app
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]