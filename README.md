# spring-amqp-demo
simple spring boot amqp microservice system


## Run

Start the rabbitmq in docker
```bash
cd docker\rabbitmq
docker compose up -d
```

Build the project
```bash
mvn clean package -DskipTests; 
```

Start the spring boot application
```bash
java -Dspring.profiles.active=receiver -jar target/spring-amqp-demo-0.0.1-SNAPSHOT.jar
```

```bash
java -Dspring.profiles.active=sender -jar target/spring-amqp-demo-0.0.1-SNAPSHOT.jar
```