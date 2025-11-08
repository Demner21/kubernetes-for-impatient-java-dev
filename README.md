# Kubernetes for Impatient Java Developers

Project presentation for JConf Peru 2025

This is a basic Quarkus Maven project configured to explore Kubernetes concepts with easy deployment to local clusters (like k3s) and external clusters (like Red Hat OpenShift Sandbox).

## 🚀 Features

- ✅ RESTful API with two endpoints
- ✅ Health checks (liveness, readiness, startup)
- ✅ Automatic Kubernetes manifest generation
- ✅ Container image build with JIB
- ✅ Ready for k3s and OpenShift deployment
- ✅ JSON support with Jackson

## 📋 Prerequisites

- Java 17+
- Maven 3.9+
- Docker (for container image builds)
- kubectl (for Kubernetes deployments)
- k3s, minikube, or access to Red Hat OpenShift Sandbox

## 🏃 Running the Application Locally

### Development Mode

Run the application in dev mode with live coding enabled:

```bash
./mvnw quarkus:dev
```

The application will be available at:
- Main app: http://localhost:8080
- Dev UI: http://localhost:8080/q/dev/
- Health: http://localhost:8080/health

### Test the Endpoints

```bash
# Simple greeting
curl http://localhost:8080/hello

# Application info
curl http://localhost:8080/hello/info

# Health check
curl http://localhost:8080/health
```

## �� Building Container Images

### Build with JIB (no Docker daemon required)

```bash
./mvnw clean package -Dquarkus.container-image.build=true
```

This creates a container image: `jconfperu/quarkus-k8s-demo:1.0.0-SNAPSHOT`

### Push to Registry (optional)

```bash
./mvnw clean package \
  -Dquarkus.container-image.build=true \
  -Dquarkus.container-image.push=true \
  -Dquarkus.container-image.registry=docker.io \
  -Dquarkus.container-image.username=<your-username> \
  -Dquarkus.container-image.password=<your-password>
```

## ☸️ Deploying to Kubernetes

### Generate Kubernetes Manifests

Kubernetes manifests are automatically generated during the build in `target/kubernetes/`:

```bash
./mvnw clean package
```

The generated files include:
- `target/kubernetes/kubernetes.yml` - Complete Kubernetes deployment
- `target/kubernetes/kubernetes.json` - JSON format

### Deploy to Local k3s Cluster

1. **Install k3s** (if not already installed):
```bash
curl -sfL https://get.k3s.io | sh -
```

2. **Build and load the image**:
```bash
# Build the container image
./mvnw clean package -Dquarkus.container-image.build=true

# Import to k3s (if using local registry)
docker save jconfperu/quarkus-k8s-demo:1.0.0-SNAPSHOT | sudo k3s ctr images import -
```

3. **Deploy to k3s**:
```bash
sudo kubectl apply -f target/kubernetes/kubernetes.yml
```

4. **Check deployment status**:
```bash
sudo kubectl get pods
sudo kubectl get svc quarkus-k8s-demo
```

5. **Access the application**:
```bash
# Get the service URL
sudo kubectl get svc quarkus-k8s-demo

# Port forward to access locally
sudo kubectl port-forward svc/quarkus-k8s-demo 8080:80
```

6. **Test the application**:
```bash
curl http://localhost:8080/hello
curl http://localhost:8080/hello/info
```

### Deploy to Red Hat OpenShift Sandbox

1. **Login to OpenShift**:
```bash
oc login --token=<your-token> --server=<your-server>
```

2. **Build and deploy with OpenShift profile**:
```bash
./mvnw clean package -Dquarkus.profile=openshift \
  -Dquarkus.container-image.build=true \
  -Dquarkus.kubernetes.deploy=true
```

This will:
- Build the container image
- Push to OpenShift's internal registry
- Deploy the application
- Create a Route for external access

3. **Get the application URL**:
```bash
oc get route quarkus-k8s-demo
```

4. **Access the application**:
```bash
curl https://<route-url>/hello
curl https://<route-url>/hello/info
```

## 🧪 Running Tests

```bash
./mvnw test
```

## 📦 Packaging Options

### Standard JAR
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Uber JAR
```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/quarkus-k8s-demo-1.0.0-SNAPSHOT-runner.jar
```

### Native Executable
```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
./target/quarkus-k8s-demo-1.0.0-SNAPSHOT-runner
```

## 🔧 Configuration

Key configuration properties in `src/main/resources/application.properties`:

- `quarkus.kubernetes.replicas=2` - Number of replicas
- `quarkus.kubernetes.service-type=LoadBalancer` - Service type
- `quarkus.container-image.group=jconfperu` - Container image group
- `quarkus.container-image.build=false` - Auto-build images

## 📚 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/hello` | GET | Returns a greeting message |
| `/hello/info` | GET | Returns application information (JSON) |
| `/health` | GET | SmallRye health check endpoint |
| `/health/live` | GET | Liveness probe |
| `/health/ready` | GET | Readiness probe |
| `/health/started` | GET | Startup probe |

## 🛠️ Technology Stack

- **Quarkus 3.17.5** - Supersonic Subatomic Java Framework
- **Java 17** - LTS version
- **RESTEasy Reactive** - JAX-RS implementation
- **Jackson** - JSON processing
- **SmallRye Health** - Health check implementation
- **Quarkus Kubernetes** - Automatic manifest generation
- **JIB** - Container image building without Docker

## 📖 Learn More

- [Quarkus](https://quarkus.io/)
- [Quarkus Kubernetes Guide](https://quarkus.io/guides/kubernetes)
- [Quarkus Container Images Guide](https://quarkus.io/guides/container-image)
- [k3s Documentation](https://k3s.io/)
- [Red Hat OpenShift Sandbox](https://developers.redhat.com/developer-sandbox)

## 🤝 Contributing

This is a demo project for JConf Peru 2025. Feel free to fork and experiment!

## 📝 License

This project is open source and available under standard terms.
