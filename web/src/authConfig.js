// Config object to be passed to Msal on creation
export const msalConfig = {
    auth: {
        clientId: "9d10c7ee-47f1-4514-bad8-f16222333ac3",
        authority: 'https://nielskilab.b2clogin.com/nielskilab.onmicrosoft.com/B2C_1A_SUSI',
        knownAuthorities: [ 'nielskilab.b2clogin.com' ]
    }
};

// Add here scopes for id token to be used at MS Identity Platform endpoints.
export const loginRequest = {
    scopes: ["openid", "profile", "offline_access"]
    // extraQueryParameters: { "p": "B2C_1A_SUSI" }
};

// Add here the endpoints for MS Graph API services you would like to use.
export const graphConfig = {
    graphMeEndpoint: "https://graph.microsoft.com/oidc/userinfo"
};