FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/api.jar
COPY wallet/cwallet.sso /wallet/
COPY wallet/ewallet.p12 /wallet/
COPY wallet/keystore.jks /wallet/
COPY wallet/ojdbc.properties /wallet/
COPY wallet/sqlnet.ora /wallet/
COPY wallet/tnsnames.ora /wallet/
COPY wallet/truststore.jks /wallet/

EXPOSE 3000

CMD ["java", "-jar", "api.jar"]
