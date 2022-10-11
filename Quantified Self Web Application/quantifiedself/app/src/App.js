import React, { Component } from 'react';
import "bootswatch/dist/superhero/bootstrap.min.css";
import DistanceWidgetRoot from "./Widgets/ActivityDistanceDisplayWidget/DistanceWidgetRoot";
import {Container, Nav, Navbar} from "react-bootstrap";
import PlaceWidgetRoot from "./Widgets/PlaceCountDetailWidget/PlaceWidgetRoot";
import {BrowserRouter, Link, Switch, Route} from "react-router-dom";

class App extends Component {

    render() {
        return (
            <BrowserRouter>
            <React.Fragment>
                <Navbar variant="dark" bg="primary">
                    <Container>
                        <Navbar.Brand as={Link} to={'/'}>Welcome to the Quantified Self</Navbar.Brand>

                    <Nav className="me-auto">
                        <Nav.Link as={Link} to={'/distances'} className="nav-link"> Distances </Nav.Link>
                        <Nav.Link as={Link} to={'/places'} className="nav-link"> Places </Nav.Link>
                        <Nav.Link href={"http://localhost:8080/activity/calories"}> Calories </Nav.Link>
                        <Nav.Link href={"http://localhost:8080/activity/count"}> Count </Nav.Link>
                    </Nav>
                    </Container>
                </Navbar>
                {/*<Container className={"py-5"}>*/}
                {/*    <DistanceWidgetRoot />*/}
                {/*    <PlaceWidgetRoot />*/}
                {/*</Container>*/}
                <Switch>
                    <Route exact path='/'></Route>
                    <Route exact path='/distances' component={DistanceWidgetRoot} />
                    <Route exact path='/places' component={PlaceWidgetRoot} />
                </Switch>
            </React.Fragment>
            </BrowserRouter>
        )
    }


}

export default App;