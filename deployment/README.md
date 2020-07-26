# Infrastructure Deployment

## Container Registry

It is assumed Docker Hub is being used for hosting container images. If required, Azure Container Registry may be used as a substitute.

## Azure Kubernetes Servic (AKS)

First, create a dedicated namespace for hosting this sample application:

```bash
kubectl create namespace fabrikam
```

Next, from the root directory use the Helm chart provided in this repo to deploy these microservices to your AKS cluster.

```bash
helm install fabrikam-oms deployment/helm --namespace fabrikam
```