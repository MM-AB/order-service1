# Order microservice 1

## Prerequisites

Create network **rsonetwork** if there is none:

```bash
docker network create rsonetwork
```

Install MongoDB and start MongoDB server with:
```bash
mongod
```

Clean install Maven package:
```bash
mvn clean install
```


## Build and run Docker containers

Building **order-service1** container:
```bash
docker build -t order-service1 .
```

Run mongo:
```bash
docker run --name=mongo --rm --network=rsonetwork mongo
```

Run app:
```bash
docker run --name=order-service1 --rm --network=rsonetwork -p 8080:8080 -e MONGO_ORDER_URL=MONGO_URL=mongodb://mongo:27017/dev order-service1
```

## Rename Docker image and push to repository

Rename image:
```bash
docker tag order-service1 rsoreg.azurecr.io/order-service:**tag**
```

Push to repositorty:
```bash
docker push rsoreg.azurecr.io/order-service:**tag**
```
