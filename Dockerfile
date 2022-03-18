FROM openjdk:11
ADD target/erp-server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar erp-server-0.0.1-SNAPSHOT.jar