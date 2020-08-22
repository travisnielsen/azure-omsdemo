import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { ServiceBusClient, SendableMessageInfo } from "@azure/service-bus";
import * as msRestNodeAuth from "@azure/ms-rest-nodeauth";

// Load the .env file if it exists
import * as dotenv from "dotenv";
dotenv.config();

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const name = (req.query.name || (req.body && req.body.name));
    const serviceBusEndpoint = process.env.SERVICE_BUS_ENDPOINT;
    const topicName = process.env.SERVICE_BUS_TOPIC_NAME;
    const msiEndpoint = process.env.MSI_ENDPOINT;
    const testSecret = process.env.TEST_SECRET

    var tokenCreds;

    if (msiEndpoint) {
        console.log("application has msi endpoint. Key Vault Reference value is: " + testSecret);

        const clientId = process.env.CLIENT_ID_MSI;
        
        const options: msRestNodeAuth.MSIAppServiceOptions = {
            resource: "https://servicebus.azure.net/"
        }
        
        tokenCreds = await msRestNodeAuth.loginWithAppServiceMSI(options);

    } else {

        console.log("no msi endpoint - using service principal");

        const clientId = process.env.CLIENT_ID;
        const tenantId = process.env.TENANT_ID;
        const clientSecret = process.env.CLIENT_SECRET;

        tokenCreds = await msRestNodeAuth.loginWithServicePrincipalSecret(clientId, clientSecret, tenantId, {
            tokenAudience: "https://servicebus.azure.net/" });
        
        context.log.info(tokenCreds);
    }

    console.log(`Creating client for: ${serviceBusEndpoint} for topic ${topicName}`);

    // Set up Service Bus connection
    const sbClient = ServiceBusClient.createFromAadTokenCredentials(serviceBusEndpoint, tokenCreds);
    const queueClient = sbClient.createTopicClient(topicName);
    const sender = queueClient.createSender();

    try {
        const message: SendableMessageInfo = {
        body: `test message: ${name}`,
        label: "test message"
        };
    
        console.log(`Sending message: ${message.body} - ${message.label}`);
        await sender.send(message);
        await queueClient.close();
    } catch (e) {
        console.log(e);
        context.res = {
            body: e,
            status: 500
        };
    }
    finally {
        await sbClient.close();
    }

    const responseMessage = name
        ? "Hello, " + name + ". This HTTP triggered function executed successfully."
        : "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.";

    context.res = {
        // status: 200, /* Defaults to 200 */
        body: responseMessage
    };

};

export default httpTrigger;