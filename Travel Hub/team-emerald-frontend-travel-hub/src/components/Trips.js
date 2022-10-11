import React, { useState, useEffect, useCallback } from "react";
import { Button, Card } from "react-bootstrap"
import TripDataService from "../services/trips"
import "../styling/Trips.css"
import Itinerary from "./Itinerary.js"
import { Link, Route, Routes, useNavigate } from "react-router-dom";

const TripsList = ({ user }) => {
    // useState hooks - React hooks new approach to handling state and effects in react    
    // functional components

    const navigate = useNavigate();

    const navigatePlanNewTrip = () => {
      navigate("/plan-new-trip");
    };

    // useState to set state values, get all the trips
    const [trips, setTrips] = useState([]);

    const getTrips = useCallback(() => {
        if (user) {
            TripDataService.getAllTripsByUser(user.googleId)
                .then(response=> {
                    // console.log(response)
                    setTrips(response.data);
                    // setTrips([...trips, response.data]);
                    // console.log(trips);
                })
                .catch(e => {
                    console.log(e);
                })
        }
    }, [user])

    useEffect(() => {
        if (user) {
            getTrips();
        }
    }, [user]);

    const parseDateTime = (date) => {
       const parsedDate = new Date(date).toLocaleDateString('en-us', {weekday: "long", year: "numeric", month:"short", day:"numeric"});
       console.log("parsed date"); 
       console.log(parsedDate);
       return parsedDate;
    };

    return (
        <div className="App">
            <div className="button-trip">
            <Button 
                variant="info" 
                id="button" 
                onClick={navigatePlanNewTrip}
                > 
                    + Add New Trip 
            </Button>
            </div>


            <div className="card-trip">
            {trips.map((trip) => {
                return (
                    <Card bg='light' style={{ margin: '40px'}}>
                        <Card.Img src={process.env.PUBLIC_URL + 'images/placeholder/travel-placeholder.jpeg'} ></Card.Img>
                        <Card.Header bg='info' className="card" as="h3">{trip.trip_name}</Card.Header>
                        <Card.Body>
                            <Card.Title as="h5"> {trip.destination}</Card.Title>
                            <Card.Text> 
                                Start Date: {(parseDateTime(trip.start_date))}
                                <br></br>
                                End Date: {(parseDateTime(trip.end_date))}
                            </Card.Text>
                            {/* <Button variant="info" > Go to {trip.trip_name}</Button> */}
                            <Link to={"/trips/"+trip.id}> 
                                <Button variant="info">
                                    Go to {trip.trip_name}
                                </Button>
                            </Link>
                        </Card.Body>
                    </Card>
                )
            })}
             
            </div>


            <div className="add-trip-button">
               
                   
            
            </div>


        
           <Routes>
           <Route exact path={"/itnerary"} element={
                <Itinerary
            />}
            />
           </Routes>
        </div>
    )
}

export default TripsList;