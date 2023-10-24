# Get version of Playwright
FROM mcr.microsoft.com/playwright/java:v1.27.0-focal
# Get the needed libraries to run Playwright
RUN apt-get update && apt-get -y install libnss3 libatk-bridge2.0-0 libdrm-dev libxkbcommon-dev libgbm-dev libasound-dev libatspi2.0-0 libxshmfence-dev

LABEL maintainer=@docker.maintainer@

ENV APP=app
ENV WORKDIR=/${APP}
ENV JAR=@project.build.finalName@.jar
ENV EXECUTABLE=run.sh
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"

RUN apt-get -y update && apt-get clean all
RUN apt-get install -y dos2unix
RUN useradd --create-home --home-dir ${WORKDIR} --shell /bin/bash ${APP}

COPY ${JAR} ${WORKDIR}
COPY ${EXECUTABLE} ${WORKDIR}

RUN dos2unix ${WORKDIR}/${EXECUTABLE}; \
   chmod 755 ${WORKDIR}/${EXECUTABLE}; \
   chown -R ${APP}:users ${WORKDIR}
USER ${APP}

EXPOSE 8080
EXPOSE 8081

CMD ${WORKDIR}/${EXECUTABLE}
