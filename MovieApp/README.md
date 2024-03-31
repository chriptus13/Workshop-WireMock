# Movie Application

This folder contains a SpringBoot application for searching and retrieving movie information from [TMDB](https://www.themoviedb.org).

## How to run

In order to run the application locally you will need an access token from TMDB, see more [here](https://developer.themoviedb.org/docs/getting-started).

When you have your access token simply run the following

```bash
TMDB_ACCESS_TOKEN=<your-token> ./gradlew bootRun
```

### Mocked environment

In case you want to run the application pointing to WireMock service instead you need to run WireMock via [docker compose file](../compose.yaml) and then use the `mock` profile when running the application.

```bash
# From root repository folder
docker compose up -d --build
cd MovieApp
SPRING_PFOILES_ACTIVE=mock ./gradlew bootRun
```

* WireMock will be available at `localhost:8090`
* Movie application will be available at `localhost:8080`

## Movie requests

### Search movies

```http
GET localhost:8080/movies?query=<query>
```

### Get movie details

```http
GET localhost:8080/movies/<movie-id>
```

## WireMock requests

### Check mappings

```http
GET localhost:8090/__admin/mappings
```

### Check requests made

```http
GET localhost:8090/__admin/requests
```
