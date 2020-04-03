# QuarkusDemo


## Demo features

- rest + jwt security
- mybatis cdi integration
- mongodb integration
- reactive streams
- kafka integration

## Build and run

```yaml
./mvnw clean compile quarkus:dev
```
or
```yaml
./mvnw clean install

java -jar <path-to-jar>/qd-0.0.1.jar
```

Server is running on port 8020;
SwaggerUI: http://localhost:8020/swagger-ui

