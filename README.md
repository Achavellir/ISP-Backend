# International Student Platform (ISP) Backend

This is the backend service for the International Student Platform, a comprehensive solution for international students.

## Features

- User authentication and authorization with JWT
- Password security checking
- Student accommodation management
- Community forum
- Marketplace for buying/selling items
- And more...

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL / H2 Database
- JWT Authentication
- Spring Boot Actuator for monitoring
- Prometheus metrics
- Redis caching (optional)
- AWS S3 integration

## Development Setup

### Prerequisites

- JDK 17+
- Maven 3.8+
- PostgreSQL (optional, can use H2 in-memory database for development)

### Running Locally

1. Clone the repository
2. Configure application properties in `src/main/resources/application.properties`
3. Run the application:

```bash
./mvnw spring-boot:run
```

The application will be available at http://localhost:8083

### API Documentation

API documentation is available via SpringDoc OpenAPI at http://localhost:8083/swagger-ui.html when the application is running.

## Kubernetes Deployment

This project includes Kubernetes manifests for deploying to a Kubernetes cluster. See the [Kubernetes README](kubernetes/README.md) for detailed deployment instructions.

### Quick Deployment Steps

1. Build the Docker image:
```bash
docker build -t isp-backend:latest .
```

2. Deploy to Kubernetes:
```bash
kubectl apply -f kubernetes/secrets.yaml
kubectl apply -f kubernetes/configmap.yaml
kubectl apply -f kubernetes/postgres.yaml
kubectl apply -f kubernetes/deployment.yaml
kubectl apply -f kubernetes/service.yaml
```

## Project Structure

- `src/main/java/org/isp/controller/` - REST API controllers
- `src/main/java/org/isp/service/` - Business logic services
- `src/main/java/org/isp/model/` - Domain models
- `src/main/java/org/isp/repository/` - Data access repositories
- `src/main/java/org/isp/security/` - Security configuration
- `src/main/java/org/isp/dto/` - Data transfer objects
- `src/main/java/org/isp/exception/` - Exception handling
- `src/main/resources/` - Configuration files and resources

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
