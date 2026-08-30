# Railwise

Train Journey Mapper hackathon project (Geekulcha 2026). Track train routes and
log real journeys against them — departures, delays, cancellations — as the
foundation for a commuter-facing journey mapper.

## Project structure
```
railwise/
├── backend/         # Spring Boot REST API (Java)
│   ├── src/main/java/com/railwise/journey/
│   │   ├── domain/   # Route, Journey entities + repositories
│   │   ├── security/ # SecurityConfig
│   │   ├── web/      # controllers, DTOs, exception handler
│   │   └── config/   # CorsConfig
│   ├── src/test/java/...
│   ├── SUBMISSION.md
│   └── pom.xml
├── frontend/        # React (Vite) app
│   ├── src/
│   │   ├── pages/    # RoutesPage, JourneysPage
│   │   └── lib/      # api.js, config.js
│   └── package.json
├── docs/            # architecture diagram goes here
└── README.md        # this file
```

## Backend — Spring Boot API

### Prerequisites
- JDK 17+
- PostgreSQL running locally:
  ```bash
  createdb railwise
  ```
  (Update `backend/src/main/resources/application.properties` if your username/password differ.)

### Run it
**In IntelliJ:** open the `backend/` folder, let it import as a Maven project,
then right-click `RailwiseApplication.java` → Run.

**From the command line:**
```bash
cd backend
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

### Endpoints
- `GET/POST /api/v1/routes`, `GET/DELETE /api/v1/routes/{id}`
- `GET/POST /api/v1/journeys` (filter with `?routeId=`), `GET/DELETE /api/v1/journeys/{id}`
- `PATCH /api/v1/journeys/{id}/status` — update a journey's status (e.g. `DELAYED`, `COMPLETED`, `CANCELLED`)

### Quick test
```bash
# create a route
curl -X POST http://localhost:8080/api/v1/routes \
  -H "Content-Type: application/json" \
  -d '{"routeName":"Cape Flats Line","originStation":"Cape Town","destinationStation":"Khayelitsha","distanceKm":32,"operator":"Metrorail"}'

# log a journey (use the route id returned above)
curl -X POST http://localhost:8080/api/v1/journeys \
  -H "Content-Type: application/json" \
  -d '{"routeId":1,"scheduledDeparture":"2026-09-01T07:00:00Z","scheduledArrival":"2026-09-01T07:45:00Z"}'
```

## Frontend — React (Vite)

### Run it
```bash
cd frontend
npm install
npm run dev
```

Opens on `http://localhost:5173`. It talks to the backend at
`http://localhost:8080` by default — change that in `frontend/src/lib/config.js`
if you deploy the backend elsewhere.

Two pages are wired up out of the box:
- **Routes** — add and list train routes
- **Journeys** — log a journey against a route, mark it delayed/completed/cancelled

## Before demo day
- Add real auth if commuters/admins need to log in
- Update `CorsConfig` (backend) and `API_BASE_URL` (frontend) once you deploy
- Consider adding a map view (e.g. Leaflet/Mapbox) to actually plot routes visually — a strong fit for "Train Journey Mapper"
