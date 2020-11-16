export async function callOrderApi(order) {
    const headers = new Headers();
    // const bearer = `Bearer ${accessToken}`;
    // headers.append("Authorization", bearer);
    headers.append("Ocp-Apim-Subscription-Key", process.env.REACT_APP_API_KEY);
    headers.append("Content-Type", "application/json");

    const options = {
        method: "POST",
        headers: headers,
        body: JSON.stringify(order)
    };

    return fetch(process.env.REACT_APP_REQUEST_URL, options)
        .then(response => response.json())
        .catch(error => console.error(error));
}