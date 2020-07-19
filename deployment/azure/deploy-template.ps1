az login
az account set --subscription ""

az group create -l centralus -n $rgname

# Log Analytics
az deployment group create --resource-group $rgname --template-file loganalytics.json

# Service Bus
az deployment group create --resource-group $rgname --template-file servicebus.json