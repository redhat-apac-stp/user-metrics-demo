# Metrics Demo for OpenShift

If you have already deployed User Workload Monitoring you can use this repo to show how the metrics and queries work.

1. Clone this repo.
2. Confirm it can build:

```bash
$ mvn compile quarkus:dev
```

3. If it passes build, create a new project in your cluster:

```bash
$ oc new-project ns1
```

4. Deploy your app to the cluster:

```bash
mvn clean package -Dquarkus.kubernetes.deploy=true -Dquarkus.openshift.expose=true -Dquarkus.openshift.labels.app-with-metrics=quarkus-app
```

5. Create the following service monitor for your app:

```yaml
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  labels:
    k8s-app: prometheus-app-monitor
  name: prometheus-app-monitor
  namespace: ns1
spec:
  endpoints:
  - interval: 30s
    targetPort: 8080
    path: /q/metrics
    scheme: http
  selector:
    matchLabels:
      app-with-metrics: 'quarkus-app'
```

6. Run some calls to the app:

```bash
watch curl http://<your app and cluster route>/hello/quarkus
```
7. Run your custom query from the developer UI in the observability tab for namespace:ns1

```text
greeting_counter_total{name = "quarkus"}
```

# About this quarkus-getting-started Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-getting-started-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
