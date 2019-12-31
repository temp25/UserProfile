# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="gowtham25alaguraj@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 2573 available to the world outside this container
EXPOSE 2573

# The application's jar file
ARG JAR_FILE=build/libs/userprofile-1.0.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} build/libs/userprofile-1.0.0-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","userprofile-1.0.0-SNAPSHOT.jar"]
