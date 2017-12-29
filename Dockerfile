FROM java:8-jre
COPY build/libs/com.metlife.provider-1.0.0.jar /opt/app/app.jar
WORKDIR /opt/app
CMD ["java", "-jar", "app.jar"]