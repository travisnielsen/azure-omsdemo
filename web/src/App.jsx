import React from 'react';
// import {BrowserRouter, Link, Route} from 'react-router-dom';

import { MsalProvider } from "@azure/msal-react";
import { PublicClientApplication } from "@azure/msal-browser";
import { msalConfig } from "./authConfig";
import { PageLayout } from "./ui.jsx";
import "./styles/App.css";

export default function App() {
    const msalInstance = new PublicClientApplication(msalConfig);
    return (
        <MsalProvider instance={msalInstance}>
            <PageLayout />
        </MsalProvider>
    );
}

/*

const App = () => {
    let appInsights = null;
    let apiKey = process.env.REACT_APP_API_KEY;
    let requestUrl = process.env.REACT_APP_REQUEST_URL;

    function trackException() {
        appInsights.trackException({ error: new Error('some error'), severityLevel: SeverityLevel.Error });
    }

    function trackTrace() {
        appInsights.trackTrace({ message: 'some trace', severityLevel: SeverityLevel.Information });
    }

    function trackEvent() {
        appInsights.trackEvent({ name: 'some event' });
    }

    function throwError() {
        let foo = {
            field: { bar: 'value' }
        };

        // This will crash the app; the error will show up in the Azure Portal
        return foo.fielld.bar;
    }

    function reqListener () {
        console.log(this.responseText);
      }

    function ajaxRequest() {
        let xhr = new XMLHttpRequest();
        xhr.addEventListener("load", reqListener);
        xhr.open('POST', requestUrl);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.setRequestHeader('Ocp-Apim-Subscription-Key', apiKey);
        xhr.send(JSON.stringify({ "id": "order123", "itemName": "trashcans", "itemQuantity": 100, "customerId": "joe" }));
        // xhr.send();
    }

    async function fetchRequest() {
        let response = await fetch(requestUrl, {
            headers: {
                'Ocp-Apim-Subscription-Key': apiKey
            }
        });

        console.log(response.text());
    }

    return (
      <BrowserRouter>
        <TelemetryProvider instrumentationKey={apiKey} after={() => { appInsights = getAppInsights() }}>
          <div >
            <Header />
            <Route exact path="/" component={Home} />
            <Route path="/about" component={About} />
          </div>
          <div className="App">
            <button onClick={trackException}>Track Exception</button>
            <button onClick={trackEvent}>Track Event</button>
            <button onClick={trackTrace}>Track Trace</button>
            <button onClick={throwError}>Autocollect an Error</button>
            <button onClick={ajaxRequest}>Create Order (XMLHttpRequest)</button>
            <button onClick={fetchRequest}>Autocollect a dependency (Fetch)</button>
          </div>
        </TelemetryProvider>
      </BrowserRouter>
    );
};

export default App;
*/