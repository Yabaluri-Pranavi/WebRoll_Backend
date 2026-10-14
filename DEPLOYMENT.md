# Deploy the backend to Render with Aiven MySQL

## Aiven

Create a MySQL service and database in Aiven. Copy the service host, port,
database name, username, and password from its connection information. Configure
the database URL as:

```text
jdbc:mysql://<AIVEN_HOST>:<AIVEN_PORT>/<AIVEN_DATABASE>?sslMode=REQUIRED
```

Use the host, port, and database name exactly as shown by Aiven. Keep the
username and password separate; do not put credentials in the URL or commit
them to source control.

## Render

Push this backend repository to a Git provider, then create a Render Blueprint
from that repository and select `render.yaml` at its root. The blueprint builds
the Docker image using Java 17 and starts Spring Boot on Render's assigned port.

Set the following values in the Render service environment:

| Variable | Value |
| --- | --- |
| `SPRING_DATASOURCE_URL` | Aiven JDBC URL shown above |
| `SPRING_DATASOURCE_USERNAME` | Aiven database username |
| `SPRING_DATASOURCE_PASSWORD` | Aiven database password |
| `JWT_SECRET` | A private random secret at least 32 characters long |
| `APP_CORS_ALLOWED_ORIGINS` | Comma-separated deployed frontend origins, e.g. `https://your-frontend.example.com`; use `http://localhost:5173,http://localhost:3000` for local frontends |

Enter secrets only in Render's environment settings. `JWT_SECRET` must remain
the same across deploys so existing JWTs remain valid. The database user needs
permission to create and alter tables because Hibernate is configured with
`ddl-auto=update`.

After the first successful deploy, check the Render logs for a successful
database connection and use the Render service URL as the backend API base URL
in the frontend configuration.

Student passwords are stored as BCrypt hashes and are never included in student
record API responses. Student sign-in uses the student's roll number and
password. Student records created before password support do not have a
password and cannot sign in until one is set.
