# ISP Backend Kubernetes Deployment

This directory contains Kubernetes manifests for deploying the International Student Platform (ISP) backend application.

## Prerequisites

- Kubernetes cluster (local or cloud-based)
- kubectl configured to communicate with your cluster
- Docker installed (for building the container image)

## Deployment Steps

### 1. Build the Docker Image

From the root directory of the project:

```bash
docker build -t isp-backend:latest .
```

If you're using a container registry (like Docker Hub, GCR, ECR, etc.), tag and push the image:

```bash
docker tag isp-backend:latest your-registry/isp-backend:latest
docker push your-registry/isp-backend:latest
```

Then update the image reference in `deployment.yaml`.

### 2. Create Kubernetes Resources

Apply the Kubernetes manifests in the following order:

```bash
# Create secrets first
kubectl apply -f kubernetes/secrets.yaml

# Create ConfigMap
kubectl apply -f kubernetes/configmap.yaml

# Deploy PostgreSQL
kubectl apply -f kubernetes/postgres.yaml

# Wait for PostgreSQL to be ready
kubectl wait --for=condition=ready pod -l app=postgres --timeout=120s

# Deploy the application
kubectl apply -f kubernetes/deployment.yaml

# Create the service and ingress
kubectl apply -f kubernetes/service.yaml
```

### 3. Verify the Deployment

Check if all pods are running:

```bash
kubectl get pods
```

Check the services:

```bash
kubectl get services
```

Check the ingress:

```bash
kubectl get ingress
```

### 4. Access the Application

If you're using a local Kubernetes cluster (like Minikube or Docker Desktop):

1. Add an entry to your hosts file:
   ```
   127.0.0.1 isp-backend.local
   ```

2. Access the application at http://isp-backend.local

If you're using a cloud-based Kubernetes cluster, the ingress controller will provision a load balancer. You can get the external IP with:

```bash
kubectl get ingress isp-backend-ingress
```

## Configuration

### Environment Variables

The application uses the following environment variables:

- `DB_USERNAME`: PostgreSQL username (from secret)
- `DB_PASSWORD`: PostgreSQL password (from secret)
- `JWT_SECRET`: Secret key for JWT token generation (from secret)

### Scaling

To scale the application, use:

```bash
kubectl scale deployment isp-backend --replicas=3
```

### Updating the Application

To update the application:

1. Build a new Docker image with a new tag
2. Update the image in the deployment
3. Apply the updated deployment

```bash
kubectl set image deployment/isp-backend isp-backend=your-registry/isp-backend:new-tag
```

## Troubleshooting

### Viewing Logs

```bash
kubectl logs -l app=isp-backend
```

### Checking Pod Status

```bash
kubectl describe pod -l app=isp-backend
```

### Database Connection Issues

If the application cannot connect to the database:

1. Check if the PostgreSQL pod is running:
   ```bash
   kubectl get pods -l app=postgres
   ```

2. Check PostgreSQL logs:
   ```bash
   kubectl logs -l app=postgres
   ```

3. Verify the database service is working:
   ```bash
   kubectl describe service postgres
   ```