$rgname = "fabrikam-oms"
$identifier = ""
$appinsights = ""
$aikey = ""

az login
az account set --subscription ""

az group create -l centralus -n $rgname

# Log Analytics
$logWorkspaceName  = "oms-$identifier"
az monitor log-analytics workspace create --resource-group $rgname --workspace-name $logWorkspaceName --location centralus --sku "PerGB2018" --retention-time 30

# Service Bus
$sbNamespaceName = "sbns-$identifier"
az servicebus namespace create --name $sbNamespaceName --resource-group $rgname --location centralus --sku Standard
az servicebus topic create --name "orders" --namespace-name $sbNamespaceName --resource-group $rgname 
az servicebus topic subscription create --name "inventoryService" --topic-name "orders" --namespace-name $sbNamespaceName --resource-group $rgname
az servicebus topic create --name "fulfillment" --resource-group $rgname --namespace-name $sbNamespaceName
az servicebus topic subscription create --name "orderService" --topic-name "fulfillment" --namespace-name $sbNamespaceName --resource-group $rgname

# Container Registry
az deployment group create --resource-group $rgname --template-file acr.json

# PaaS Base Infrastructure
$funcAppName = "orders-$identifier"
az storage account create --name "functions${identifier}" --location centralus --resource-group $rgname --sku Standard_LRS
az functionapp plan create --resource-group $rgname --name "asp-$identifier" --location centralus --number-of-workers 2 --sku EP1 --is-linux

# Function App - Orders
az storage account create --name "functions${identifier}" --location centralus --resource-group $rgname --sku Standard_LRS
az functionapp plan create --resource-group $rgname --name "functions-$identifier" --location centralus --number-of-workers 2 --sku EP1 --is-linux
az functionapp create --name $funcAppName `
    --resource-group $rgname `
    --plan "orders-asp-$identifier" `
    --runtime node `
    --storage-account "orders${identifier}" `
    --functions-version 3 `
    --app-insights $appinsights `
    --app-insights-key $aikey `
    --disable-app-insights false

az functionapp identity assign -g $rgname -n $funcAppName

# API - Inventory
$invAppName = "inventory-$identifier"
az appservice plan create --name $invAppName  --resource-group $rgname --location centralus --sku P1V2 --number-of-workers 2 --is-linux
az webapp create --name --plan --resource-group


# Cosmos DB


# APIM



