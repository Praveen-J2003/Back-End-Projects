# E-Commerce System Backend

Simple Java **Servlet + SQLite** REST backend.

## Build & Run

```bash
mvn clean package
# Deploy the resulting WAR in Apache Tomcat (≥10) or Jetty (≥11).
```

## Key Endpoints

* `GET /api/products` – list products  
* `POST /api/products` – add product (`name`, `price`)  
* `POST /api/cart` – add to cart (`userId`, `productId`, `quantity`)  
* `POST /api/orders` – place order (`userId`, `total`)

---

*Generated: 2025-06-14.*
