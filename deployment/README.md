# Infrastructure Deployment

It is assumed you have a functional Azure Kubernetes Service (AKS) in your Azure subscription.

> Additonal documentation for this section is planned

## Deploy Workload to AKS

If your Kubernetes cluseter does not already have nginx-ingress installed, run the following commands to install it:

```bash
# Add the nginx repo to your Helm configuration and update it
helm repo add nginx-stable https://helm.nginx.com/stable
helm repo update

# Deploy nginx-ingress
helm install fabrikam-nginx nginx-stable/nginx-ingress
```

Next, from the root directory navigate to `deployment/helm/charts` and create a `values.yaml` file within both `inventory` and `orders`. For each service, copy the data from the provided `values-sample.yaml` and customize as needed (default values should work). When completed, you can deploy both servcies and the Nginx ingress controller by running the following command from `deployment/helm`:

```bash
helm install fabrikam-oms . -f values.yaml
```

To upgrade an existing installation, run the following command from the `deployments/helm` folder:

```bash
helm upgrade -f values.yaml fabrikam-oms .
```
