import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import DropdownButton from "react-bootstrap/DropdownButton";
import { AuthenticatedTemplate, UnauthenticatedTemplate, useMsal, useIsAuthenticated } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import Dropdown from "react-bootstrap/esm/Dropdown";
import { BrowserRouter, Route } from 'react-router-dom';
import { LinkContainer } from 'react-router-bootstrap'
import "./styles/App.css";
import About from "./components/about"
import Home from "./components/home"

// import {SeverityLevel} from '@microsoft/applicationinsights-web';
import { getAppInsights } from './TelemetryService';
import TelemetryProvider from './telemetry-provider';

const SignInSignOutButton = () => {
    const { instance, accounts } = useMsal();
    const isAuthenticated = useIsAuthenticated();
    return (
        <>
            <AuthenticatedTemplate>
                <div className="nav-link">
                    {isAuthenticated && ( accounts[0].name )}
                    {!isAuthenticated && ( <p>No users are signed in!</p> )}
                </div>
                <Button variant="secondary" onClick={() => instance.logout()} className="ml-auto">Sign Out</Button>
            </AuthenticatedTemplate>
            <UnauthenticatedTemplate>
                <DropdownButton variant="secondary" className="ml-auto" drop="left" title="Sign In">
                    <Dropdown.Item as="button" onClick={() => instance.loginPopup(loginRequest)}>Sign in using Popup</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => instance.loginRedirect(loginRequest)}>Sign in using Redirect</Dropdown.Item>
                </DropdownButton>
            </UnauthenticatedTemplate>
        </>
    );
};


export const PageLayout = (props) => {

    // let appInsights = null;
    let apiKey = process.env.REACT_APP_API_KEY;

    return (
        <>
            <BrowserRouter>
                <TelemetryProvider instrumentationKey={apiKey} after={() => { getAppInsights() }}>
                    <Route exact path="/" component={Home} />
                    <Route path="/about" component={About} />
                    <Navbar bg="primary" variant="dark" fixed="top">
                        <Navbar.Brand href="/">Fabrikam</Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="mr-auto">
                                <LinkContainer to="/about">
                                    <Nav.Link>About</Nav.Link>
                                </LinkContainer>
                            </Nav>
                        </Navbar.Collapse>
                        <SignInSignOutButton/>
                    </Navbar>
                    <h5><center></center></h5>
                    <br/>
                    <br/>
                    {props.children}
                </TelemetryProvider>
            </BrowserRouter>
        </>
    );
};