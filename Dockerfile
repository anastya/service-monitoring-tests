ARG BUILD_REGISTRY
ARG BASE_REGISTRY
ARG IMAGE_TAG__MAVEN
FROM ${BASE_REGISTRY:+$BASE_REGISTRY/}maven:${IMAGE_TAG__MAVEN}

WORKDIR /app
RUN mkdir repository

COPY pom.xml .

RUN mvn	-Dmaven.repo.local=./repository dependency:resolve
RUN mvn	-Dmaven.repo.local=./repository dependency:resolve-plugins
RUN mvn -Dmaven.repo.local=./repository dependency:go-offline

COPY src src

RUN mvn -Dmaven.repo.local=./repository package
RUN mvn -Dmaven.repo.local=./repository package --offline

ENV SERVICE_URL=http://service.monitoring.ru

CMD mvn -Dmaven.repo.local=./.m2 ${MAVEN_CLI_OPTS} verify
#CMD bash