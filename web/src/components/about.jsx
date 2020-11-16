import React from "react";
import { Helmet } from 'react-helmet';

export const About = () => (
    <div>
        <Helmet><title>Fabrikam | About</title></Helmet>
        <div className="container App-content">
        <div className="row">
            <h5>About Page</h5>
        </div>    
        </div>
    </div>
);

export default About;