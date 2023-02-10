FROM adoptopenjdk/openjdk11:jre
ARG JAR_FILE=build/libs/nexus-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./api.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./api.jar"]