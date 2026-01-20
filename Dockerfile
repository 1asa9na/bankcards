FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /usr/src/app

COPY . .

CMD ["mvn", "clean", "package"]