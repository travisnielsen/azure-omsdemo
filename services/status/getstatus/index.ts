import { AzureFunction, Context, HttpRequest } from "@azure/functions"
// import * as appInsights from "applicationinsights"

const appInsightsKey = process.env.APPINSIGHTS_INSTRUMENTATIONKEY;
const cloudRoleName = process.env.CLOUDROLE_NAME;

/*
if (appInsightsKey) {
    console.log("App Insights key found. Initializing.")
    appInsights
        .setup(appInsightsKey)
        .setDistributedTracingMode(appInsights.DistributedTracingModes.AI_AND_W3C);
    appInsights.start();
    appInsights.defaultClient.context.tags[appInsights.defaultClient.context.keys.cloudRole] = cloudRoleName;
    appInsights.defaultClient.context.tags["ai.cloud.role"] = cloudRoleName;
    // appInsights.defaultClient.context.tags["ai.cloud.roleInstance"] = "your role instance";
}
*/

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    // const webresponse = await fetch("http://www.google.com");

    var num = Math.floor((Math.random() * 10) + 1);

    if (num === 1 && process.env.PERFORMANCE_BUG === "true") {
        
        setTimeout(function () { console.log('delay') }, 100)
        var end = Date.now() + 10000
        while (Date.now() < end) ;

        context.res = { 
            body: "UNLUCKY"
        }
    } else {
        context.res = {
            // status: 200, /* Defaults to 200 */
            body: "OK"

        }
    };

};

export default httpTrigger;