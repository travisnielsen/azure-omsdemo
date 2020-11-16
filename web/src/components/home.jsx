import React from "react";
import Button from "react-bootstrap/Button";
import "../styles/App.css";
import { callOrderApi } from "./order.jsx";

const Home = () => {

    function randomString(length) {
        var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
        var string_length = length;
        var randomstring = '';
        for (var i=0; i<string_length; i++) {
            var rnum = Math.floor(Math.random() * chars.length);
            randomstring += chars.substring(rnum,rnum+1);
        }
        return randomstring;
    }

    function CreateOrder() {
        var orderId = randomString(12);
        var order = { id: orderId, itemName: "trashcans", itemQuantity: 100, customerId: "joe" };
        callOrderApi(order).then(response => console.log(response));
    }

    return (
        <>
            <div className="container App-content">
                <div className="row">
                    <Button variant="secondary" onClick={CreateOrder}>Request Profile Information</Button>
                </div>
            </div>
        </>
    );
};

export default Home;