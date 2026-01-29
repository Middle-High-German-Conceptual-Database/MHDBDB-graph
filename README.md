# Mittelhochdeutsche Begriffsdatenbank

Node: 14.21.3

## Deployment
### Backend

Source  [build.sh](./build.sh):

```bash
cd /home/schlager/MHDBDB
. build.sh
```

or run manually:

```bash
cd /home/schlager/MHDBDB
sdk use java 21.0.9-tem
cd backend
mvn clean install
cd ..
docker-compose stop && docker-compose build && docker-compose up -d
# docker logs -ft backend
```

s.a. [build.sh](./build.sh)

A note about **sdk**: [SDKMan](https://sdkman.io/) ia a component to install various SDKs from various sources and hase been used for the JDK here.

### Frontend

```bash
cd /home/schlager/MHDBDB/frontend
nvm use 22
npm run webpack:build:main
```
