FROM eclipse-temurin:21-alpine

ADD target/bank-api.jar bank-api.jar

ENTRYPOINT ["java","-jar","/bank-api.jar"]
