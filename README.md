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

java -jar target/quarkus-app/quarkus-run.jar
```

## Access 

Server is running on port 8020

SwaggerUI: http://localhost:8020/q/swagger-ui
Health: http://localhost:8020/q/health
DEV Endpoint: http://localhost:8020/q/dev/

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


# Ruuning profiles

- Prod - need external stuff
- Test - test containers are used
- Dev - dev profile with dev tools enabled.
 To active profile via cmd: run with -Dquarkus.profile=profile-name
