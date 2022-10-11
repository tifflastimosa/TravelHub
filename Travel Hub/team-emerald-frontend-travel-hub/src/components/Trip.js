import React, {useState, useEffect} from "react";
import TripDataService from "../services/trips";
import { useParams, useNavigate } from 'react-router-dom';
import Itinerary from "./Itinerary";
import "../styling/Trip.css";
import LandingPage from "./LandingPage";


const Trip = ({ user }) => {

    let params = useParams();
    console.log("params")
    console.log(params)

    const navigate = useNavigate();

    const navigateLandingPage = () => {
        navigate("/");
    }

    const [trip, setTrip] = useState({
        id: null,
        trip_name: "",
        destination: "",
        start_date: "", 
        end_date: "",
        collaborators: [], 
        budget: 0,
        user_id: "", 
        activities_booked: [],
        other_bookings: []
    })

    useEffect(() => {
        const getTrip = id => {
            TripDataService.getTripById(params.id)
            .then(response => {
                // console.log("response")
                // console.log(response.data)
                setTrip(response.data)
            })
            .catch(e => {
                console.log(e)
            }) 
        }
        getTrip(params.id);
        // console.log("after getTrip")
        // console.log(trip)
    }, [params.id]);


    const parseDateTime = (date) => {
        const parsedDate = new Date(date).toLocaleDateString('en-us', {weekday: "long", year: "numeric", month:"short", day:"numeric"});
        console.log("parsed date"); 
        console.log(parsedDate);
        return parsedDate;
     };
 

    return (
        <div variant="info">
            <div className="display-info">
                <h1 variant="info">{trip.trip_name}</h1>
                <h5> Destination: {trip.destination} </h5>
                <p> Collaborators: {trip.collaborators}</p>
                <p> <b> Start Date: </b> {parseDateTime(trip.start_date)}    <b>End Date:</b> {parseDateTime(trip.end_date)} </p>
                <p> <b> Budget: </b> ${trip.group_budget} </p>

            </div>
            
            <div className="trip-itinerary"> 
                {user ? 
                    <Itinerary trip={trip} />
                :
                    navigateLandingPage()
                }            
                
            </div>
        </div>
    )

}

export default Trip;