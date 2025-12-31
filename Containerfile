FROM eclipse-temurin:25
WORKDIR /app

COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]