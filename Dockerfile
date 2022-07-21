FROM openjdk:17
EXPOSE 80/tcp
COPY walnut-1.0.1.jar walnut.jar
ENTRYPOINT java -jar walnut.jar