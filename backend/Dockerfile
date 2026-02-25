FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get install -y \
    curl git make gcc ant xsltproc \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /opt/teic

RUN git clone https://github.com/TEIC/Stylesheets.git /opt/teic/Stylesheets

WORKDIR /opt/teic/Stylesheets
RUN make install

RUN mkdir /opt/app
COPY target/*.jar /opt/app/mhdbdb.jar
# to use a profile dhinfra, specify it on the CMD or via env:
# CMD ["java", "-jar", "-Dspring.profiles.active=dhinfra", "/opt/app/mhdbdb.jar"] 
# or set env var SPRING_PROFILES_ACTIVE via docker-compose.yml
CMD ["java", "-jar", "/opt/app/mhdbdb.jar"]
