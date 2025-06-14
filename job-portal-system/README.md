# Job Portal System Backend

Simple Java **Servlet + SQLite** REST backend.

## Build & Run

```bash
mvn clean package
# Deploy the resulting WAR in Apache Tomcat (≥10) or Jetty (≥11).
```

## Key Endpoints

* `GET /api/jobs` – list jobs  
* `POST /api/jobs` – post job (`title`, `company`, `location`)  
* `POST /api/applications` – apply (`jobId`, `name`, `email`, `resume`)

---

*Generated: 2025-06-14.*
