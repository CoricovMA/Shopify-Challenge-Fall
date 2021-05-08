FROM openjdk:11

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar

CMD [ "java","-Xmx300m","-Xss512k","-XX:CICompilerCount=2","-Dfile.encoding=UTF-8","-Dspring.profiles.active=heroku","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar" ]