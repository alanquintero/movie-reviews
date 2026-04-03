# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn clean install

# Run application (available at http://localhost:8080)
mvn spring-boot:run

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=TrieTest

# Run a specific test method
mvn test -Dtest=TrieTest#testSearch
```

## Architecture

This is a Spring Boot 3 application with an H2 in-memory database, serving both a REST API and a static frontend.

**Layered structure:**
- `controller/` → `service/` → `repository/` → `entity/`
- `mapper/` converts entities to DTOs for API responses
- `dto/` holds four DTO types: `MovieDetailsDto` (full detail), `MovieSummaryDto` (list view), `MovieSearchResultDto` (search autocomplete), `MovieDto` (JSON parsing only)

**Startup flow:** `InitDBService` (`@PostConstruct`) reads `top_movies_imdb.json` from the classpath, persists ~1000 movies with their Directors/Genres/CastMembers via JPA, then populates the `Trie` singleton with all movie titles for prefix search.

**Trie search:** The `search/` package contains a custom prefix-tree implementation. `Trie` is a singleton; `TrieManager` is the Spring `@Service` wrapper. Search returns movie IDs which are then hydrated from the DB and sorted by IMDB rating (top 5 returned). Movie titles are URL-encoded before insertion/lookup.

**Two controllers:**
- `MovieController` — `/api/v1/movies/{id}`, `/api/v1/movies/all` (paginated), `/api/v1/movies/search?titlePrefix=`
- `TopMovieController` — `/api/v1/movies/top[?n=]`

**Frontend:** Single-page app in `src/main/resources/static/`. `js/api.js` is a thin fetch wrapper; `js/main.js` handles all UI. Bootstrap 5.3.8 and Bootstrap Icons are vendored locally.

**Database:** H2 in-memory (`moviesdb`). Console enabled at `/h2-console`. Schema is auto-created by Hibernate on startup (`ddl-auto=create`).
