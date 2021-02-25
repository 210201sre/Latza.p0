FROM maven:3.6.3-openjdk-8 as builder

#base image with jdk8 & maven
#Copy our pom.xml and our source-code
COPY pom.xml pom.xml
COPY src/ src/

#build our app
RUN mvn clean package

#as a seperate stagem to save on resulting image size we discard everything from previous stages
FROM java:8 as runner
#base image only needs JRE8

#expose port for web-app. this is the port of the virtual context, not the computer that it is running on
EXPOSE 7000

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

#copy the jar file form our previous stage
COPY --from=builder target/project000-jar-with-dependencies.jar app.jar

#run the program
ENTRYPOINT [ "java", "-jar", "app.jar"]
#this becomes 'ENTERYPORT java -jar app.jar' when executed