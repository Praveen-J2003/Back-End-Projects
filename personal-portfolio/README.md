# Personal Portfolio Backend

Simple Java **Servlet + SQLite** REST backend.

## Build & Run

```bash
mvn clean package
# Deploy the resulting WAR in Apache Tomcat (≥10) or Jetty (≥11).
```

## Key Endpoints

* `GET /api/projects` – list projects  
* `POST /api/projects` – add project (`title`, `description`)  
* `GET /api/contacts` – list contact messages  
* `POST /api/contact` – submit contact (`name`, `email`, `message`)

---

*Generated: 2025-06-14.*
