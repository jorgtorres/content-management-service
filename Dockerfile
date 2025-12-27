FROM maven:3.9.12-amazoncorretto-21 AS build
ARG uest_version
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
# Copy custom Maven libraries into the container's local Maven repo
COPY mavenlibs/repository/com/unionestate/rds/persistence \
     /root/.m2/repository/com/unionestate/rds/persistence

RUN mvn -f /usr/src/app/pom.xml -Duest_version=${uest_version} -DskipTests=true clean package

FROM amazoncorretto:21
COPY --from=build /usr/src/app/target/cm*.jar cm.jar
COPY --from=build /usr/src/app/src/main/resources/application*.properties /
COPY init.sh /init.sh

ENV ENVIRONMENT=bldint
ENV SPRING_PROFILES_ACTIVE=bldint

ENTRYPOINT ["sh","init.sh"]

EXPOSE 8080