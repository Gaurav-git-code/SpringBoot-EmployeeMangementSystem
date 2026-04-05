#stage 1
#get the maven+java image and use it temproraly mark as builder
FROM maven:3.9-eclipse-temurin-17 AS builder
#Working directory
WORKDIR /app
#copy the pom.xml and cache it
COPY pom.xml .
RUN mvn dependency:go-offline -B
#copy the source and package it
COPY src ./src
RUN mvn package -DskipTests -B
#stage 2
# get light weight java and linux and mark as runtime
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
#copy only jar from stage 1 no dependency
COPY --from=builder /app/target/EmployeeManagementSystem-0.0.1-SNAPSHOT.jar app.jar
#which port container listen to
EXPOSE 8080
#command run when container start
ENTRYPOINT ["java","-jar","app.jar"]
