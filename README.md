# MHDBDB-graph

Dieses Repository enthält den **Graph-basierten (RDF/GraphDB) Software-Stack** der **Mittelhochdeutschen Begriffsdatenbank (MHDBDB)** – kurz: **mhdbdb-graph**.

Enthalten sind:
- **GraphDB / RDF-Triplestore**
- **Backend**: Java / Spring Boot
- **Frontend**: Angular / TypeScript (Webpack)
- **Docker Compose** für lokalen Betrieb und Deployment

Repo: https://github.com/Middle-High-German-Conceptual-Database/MHDBDB

---

## Architektur (High-Level)

Der Stack wird über `docker-compose.yml` orchestriert und umfasst u. a.:

- **graphdb**: Ontotext GraphDB auf `http://localhost:7200`
- **graphdb2**: zweite GraphDB-Instanz auf `http://localhost:7202` (z. B. Test/Parallelbetrieb)
- **backend**: Spring Boot API auf `http://localhost:8081`
- **nginx**: liefert das Frontend auf `http://localhost:9000`
- **portainer**: Container-Management auf `https://localhost:9443`

Das Backend mountet ein TEI-Verzeichnis nach `/tei`:
- `../tei1:/tei`  
Stelle sicher, dass `../tei1` relativ zum Repo-Root existiert.

---

## Quickstart (Docker)

### Voraussetzungen
- Docker + Docker Compose

### Start
```sh
./build.sh
```

`build.sh` baut das Backend (Maven/Java 17 via SDKMAN) und startet anschließend den Docker-Stack (`docker-compose up -d`).

### URLs
- Frontend: http://localhost:9000
- Backend: http://localhost:8081
- GraphDB: http://localhost:7200 (zweite Instanz: http://localhost:7202)
- Portainer: https://localhost:9443

---

## Entwicklung

### Frontend (Angular/TypeScript)

Empfohlen: **Node v14** (siehe auch `frontend/README.md`).

```sh
cd frontend
nvm install 14.21.3
nvm use 14.21.3
npm install
npm run webpack:dev
```

Build:
```sh
cd frontend
npm run webpack:build:main
```

Hinweis (falls du Node > 16 verwendest): bei `ERR_OSSL_EVP_UNSUPPORTED` hilft `--openssl-legacy-provider` (Details im `frontend/README.md`).

### Backend (Spring Boot)

```sh
cd backend
mvn clean install
```

---

## Betriebshinweise (GraphDB)

In `docker-compose.yml` ist für GraphDB ein hoher Heap konfiguriert (`GDB_HEAP_SIZE=24G`).
Für lokale Tests ggf. reduzieren, wenn nicht genug RAM verfügbar ist.

---

## Contributing

Issues und Pull Requests sind willkommen. Bitte bei Bugreports möglichst angeben:
- Reproduktionsschritte
- OS + Docker-Version
- Node-/Java-Version
- relevante Logs (backend/nginx/graphdb)

---

## Kontakt

GitHub Org: https://github.com/Middle-High-German-Conceptual-Database
