import { AzureFunction, Context, HttpRequest } from "@azure/functions"

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    var num = Math.floor((Math.random() * 10) + 1);

    if (num === 1) {
        
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