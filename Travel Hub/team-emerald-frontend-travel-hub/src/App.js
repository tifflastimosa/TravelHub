import "bootswatch/dist/litera/bootstrap.min.css"
import React from 'react';
import './App.css';
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import {Link, Route, Routes, useNavigate } from "react-router-dom";
import TripsList from "./components/Trips";
import Locations from "./components/Locations"
import LandingPage from "./components/LandingPage"
import PlanNewTrip from "./components/CreateNewTrip";
import Itinerary from "./components/Itinerary";
import { useEffect, useState } from 'react';
import { GoogleOAuthProvider } from '@react-oauth/google';
import Login from "./components/Login";
import Logout from "./components/Logout";
import { userForm, Controller } from "react-hook-form";
import Location from "./components/Location";
import Trip from "./components/Trip";

import './App.css';


const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

function App() {

  const [user, setUser] = useState(null);

  useEffect(() => {
    let loginData = JSON.parse(localStorage.getItem("login"));
    if (loginData) {
      let loginExp = loginData.exp;
      let now = Date.now()/1000;
      if (now < loginExp) {
        // Not expired
        setUser(loginData);
      } else {
        // Expired
        localStorage.setItem("login", null);
      }
    }
  }, []);

  return (
    <GoogleOAuthProvider clientId={clientId}>
    <div className="App">
      <header>
      <Navbar class="navbar navbar-expand-lg navbar-light bg-light">
        <Container className="container-fluid">
          <Navbar.Brand className="brand" as={Link} to={"/"}>
            Travel Hub
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav" >
            
            <Nav className="ml-auto">

              { user && ( <NavDropdown title="Trips" id="nav-dropdown">
                <NavDropdown.Item as={Link} to={"/trips"}>My Trip</NavDropdown.Item>
                <NavDropdown.Item as={Link} to={"/plan-new-trip"}>Plan New Trip</NavDropdown.Item>
              </NavDropdown>)}
             


              <Nav.Link as={Link} to={"/locations"}>
                Explore
              </Nav.Link>
            </Nav>

          </Navbar.Collapse>
          { user ? (
              <Logout setUser={setUser} />
              ) : (
              <Login setUser={setUser} />
              )
        }
        </Container>
      </Navbar>
      </header>


      <Routes>
        <Route exact path={"/"} element={
          <LandingPage />
        }/>
        <Route exact path={"/trips"} element={
          user ?
          <TripsList
            user = {user} />
            : 
            <LandingPage />
        }/>
        <Route exact path={"/plan-new-trip"} element={
          user ? 
          <PlanNewTrip
              user = {user} />
              :
              <LandingPage />
        }/>
        <Route exact path={"/trips/:id"} element={
          <Trip 
            user = {user}/>
        }/>
        <Route path={"/locations"} element={
          <Locations user={user}/>
        }/>
        <Route path={"/locations/:id"} element={
          <Location user={user}/>
        }
        />
        <Route path={"/itinerary"} element={
          <Itinerary
            user = {user}
          />
        }
        />
      </Routes>
      </div>
      </GoogleOAuthProvider>
  );
}

export default App;
