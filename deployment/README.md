# Infrastructure Deployment

It is assumed you have a functional Azure Kubernetes Service (AKS) in your Azure subscription.

> Additonal documentation for this section is planned

## Deploy Workload to AKS

First, create a dedicated namespace for hosting this sample application:

```bash
kubectl create namespace fabrikam
```

Next, from the root directory navigate to `deployment/helm/charts` and create a `values.yaml` file within both `inventory` and `orders`. For each service, copy the data from the provided `values-sample.yaml` and customize as needed (default values should work). When completed, you can deploy both servcies and the Nginx ingress controller by running the following command from `deployment/helm`:

```bash
helm install -n fabrikam -f values.yaml fabrikam-oms
```

To upgrade an existing installation, run the following command from the `deployments/helm` folder:

```bash
helm upgrade -n fabrikam -f values.yaml fabrikam-oms .
```
