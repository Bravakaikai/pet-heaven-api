# STEP 1 BUILD Kotlin PROJECT
FROM gradle:latest AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# STEP 2
FROM openjdk:latest
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ /app/
ENTRYPOINT ["java", "-jar", "/app/pet-heaven-0.0.1-SNAPSHOT.jar"]

# Should run => .\gradlew build
#FROM openjdk:latest
#ADD /build/libs/pet-heaven-0.0.1-SNAPSHOT.jar pet-heaven.jar
#ENTRYPOINT ["java", "-jar", "pet-heaven.jar"]