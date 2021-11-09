FROM openjdk:11-slim
WORKDIR /home/taskapp
ARG JAR_FILE="target/task-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} taskapp.jar
ENTRYPOINT ["java","-jar","taskapp.jar"]