<img src="/logo.webp" width="92px"/>

# Mancala
[![Hits-of-Code](https://hitsofcode.com/github/andreoss/mancala)](https://hitsofcode.com/view/github/andreoss/mancala)
[![codecov](https://codecov.io/gh/andreoss/mancala/branch/master/graph/badge.svg)](https://codecov.io/gh/andreoss/mancala)
[![Build Status](https://travis-ci.com/andreoss/mancala.svg?branch=master)](https://travis-ci.com/andreoss/mancala)

Simple implementation of [Mancala](https://en.wikipedia.org/wiki/Mancala).


## Build and run

With JDK11+
```bash
mvn package
java -jar target/mancala.jar
```

## Exercise the application
Swagger is available at  http://localhost:8080/openapi-ui/index.html

### Create a game
```
*  curl  -v -X POST -H "Content-Type: application/json" http://0.0.0.0:8080/games
> POST /games HTTP/1.1
> Host: 0.0.0.0:8080
> User-Agent: curl/7.70.0
> Accept: */*
> Content-Type: application/json
>
< HTTP/1.1 201 Created
< Content-Length: 0
< Date: Mon, 12 Oct 2020 01:25:41 +0300
< Location: http://127.0.0.1:8080/2
< connection: keep-alive
<
* Connection #0 to host 0.0.0.0 left intact
```

### See status of a game
```
* curl  -v -X GET -H "Content-Type: application/json" http://0.0.0.0:8080/games/1
* Connected to 0.0.0.0 (127.0.0.1) port 8080 (#0)
> GET /games/1 HTTP/1.1
> Host: 0.0.0.0:8080
> User-Agent: curl/7.70.0
> Accept: */*
> Content-Type: application/json
>
< HTTP/1.1 200 OK
< Content-Type: application/json
< Date: Mon, 12 Oct 2020 01:23:32 +0300
< connection: keep-alive
< content-length: 108
<
* Connection #0 to host 0.0.0.0 left intact
{"id":1,"status":{"1":6,"2":6,"3":6,"4":6,"5":6,"6":6,"7":0,"8":6,"9":6,"10":6,"11":6,"12":6,"13":6,"14":0}}

```
### Make a move
```

*  curl -v -X PUT -H "Content-Type: application/json" http://0.0.0.0:8080/games/2/pits/1
*   Trying 0.0.0.0:8080...
* Connected to 0.0.0.0 (127.0.0.1) port 8080 (#0)
> PUT /games/2/pits/1 HTTP/1.1
> Host: 0.0.0.0:8080
> User-Agent: curl/7.70.0
> Accept: */*
> Content-Type: application/json
>
< HTTP/1.1 202 Accepted
< Content-Length: 0
< Date: Mon, 12 Oct 2020 01:27:06 +0300
< Location: http://127.0.0.1:8080/2
< connection: keep-alive
<

```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```

## Build the Docker Image

```
docker build -t mancala .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 mancala:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f app.yaml                   # Deploy application
kubectl get pods                             # Wait for quickstart pod to be RUNNING
kubectl get service helidon-quickstart-mp    # Verify deployed service
```

Note the PORTs. You can now exercise the application as you did before but use the second
port number (the NodePort) instead of 8080.

After you’re done, cleanup.

```
kubectl delete -f app.yaml
```

## Build a native image with GraalVM

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:
* With a local installation of GraalVM
* Using Docker

### Local build

Download Graal VM at https://www.graalvm.org/downloads, the version
 currently supported for Helidon is `20.1.0`.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-native-image
 for more information.

Start the application:

```
./target/mancala
```

### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t mancala-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 mancala-native:latest
```


## Build a Java Runtime Image using jlink

You can build a custom Java Runtime Image (JRI) containing the application jars and the JDK modules
on which they depend. This image also:

* Enables Class Data Sharing by default to reduce startup time.
* Contains a customized `start` script to simplify CDS usage and support debug and test modes.

You can build a custom JRI in two different ways:
* Local
* Using Docker


### Local build

```
# build the JRI
mvn package -Pjlink-image
```

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-jlink-image
 for more information.

Start the application:

```
./target/mancala/bin/start
```

### Multi-stage Docker build

Build the "jlink" Docker Image

```
docker build -t mancala-jlink -f Dockerfile.jlink .
```

Start the application:

```
docker run --rm -p 8080:8080 mancala-jlink:latest
```

See the start script help:

```
docker run --rm mancala-jlink:latest --help
```
