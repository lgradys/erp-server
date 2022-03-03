FROM openjdk:17
ADD target/erp-client-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar erp-client-0.0.1-SNAPSHOT.jar