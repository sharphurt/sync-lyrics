ARG BUILD_HOME=/synclyrics
FROM gradle:8.10.2-jdk21 AS build-image
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
RUN gradle --no-daemon build -x test

FROM openjdk:21
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME

COPY --from=build-image $APP_HOME/build/libs/synclyrics-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar