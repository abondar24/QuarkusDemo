# QuarkusDemo


## Demo features

- rest + jwt security
- mybatis cdi integration
- mongodb integration
- reactive streams
- kafka integration
- camel integration

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
SwaggerUI: http://localhost:8020/q/swagger-ui

### Camel endpoints

POST http://localhost:8020/job

Body:
```yaml
{
"id":101,
"jobName":"Drink"
}
```

GET http://localhost:8020/job

DEV Endpoint: http://localhost:8020/q/dev/

# Ruuning profiles

- Prod - need external stuff
- Test - test containers are used
- Dev - dev profile with dev tools enabled.
