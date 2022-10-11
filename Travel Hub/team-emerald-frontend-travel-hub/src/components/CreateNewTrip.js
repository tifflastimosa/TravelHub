import React, { useState } from "react";
import '../styling/CreateTrip.css'
import { DateRangePicker } from "@progress/kendo-react-dateinputs";
import ProgressBar from "react-bootstrap/ProgressBar";
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import TripDataService from "../services/trips.js"
import Trips from "../components/Trips";
import { useNavigate, Route, Routes } from "react-router-dom";

const PlanNewTrip = ({ user }) => {

  const navigate = useNavigate();

  const navigateTrip = () => {
    navigate("/trips");
  };

  const [now, setNow] = useState(0);

  const [defaultValue, setValue] = useState({
    start: null, 
    end: null,
  })


  const [values, setValues] = useState({
      trip_name: "",
      destination: "", 
      start_date: "", // will have to parse to create LocalDateTime object
      end_date: "", // will have to parse to create LocalDateTime object
      collaborators: [],
      group_budget: 0.0,
      user_id: user.googleId
  }); // object is going to have values

  const tripNameInputChange = (event) => {
      setValues({...values, trip_name: event.target.value })
      console.log(values)
      setNow(20)
  }

  const tripDestinationInputChange = (event) => {
      setValues({...values, destination: event.target.value })
      setNow(40)
  }
  
  const [collabList, setCollabList] = useState([{ email: "" }]);

  const changeCollab = (e, index) => {
    const { name, value }= e.target;
    console.log(value);
    const list = [...collabList];
    list[index] = value;
    setCollabList(list);
    console.log(list);
  };


  const removeCollab = (index) => {
    const list = [...collabList];
    list.splice(index, 1);
    setCollabList(list);
    setValues({...values, collaborators: collabList});
  };

  const tripDate = (event) => {
    // console.log(event.target.value)
    // console.log(event.target.value.start)
    // console.log(event.target.value.end)
    setNow(60);
    setValue(event.target.value);
    // how to convert dates
    setValues({...values, start_date: event.target.value.start, end_date: event.target.value.end});
  }

  const tripBudget = (event) => {
    setNow(80);
    setValues({...values, group_budget: event.target.value});
  }

  const addCollab = () => {
    setCollabList([...collabList, ""]);
    console.log(collabList);
    setValues({...values, collaborators: collabList});
    setNow(100);
  };


  const postTrip = () => {
    console.log(values);
    TripDataService.createTrip(values);          
    navigateTrip();
  }

  return (
    <div className="App">
      <ToastContainer />
      <h1> Let's Plan a New Trip </h1>
      <div className="form-content">
      
      <ProgressBar variant="info" animated now={now} />
      
      <form className="form">
        <h6> Trip Name: 
          <input 
            placeholder="Ex: Adventure in Tahiti"
            onChange={tripNameInputChange} 
            className="input-field"
            type="text"
            name="trip_name"/>
        </h6>
                
        <h6> Where are you traveling to? 
          <input 
            placeholder="Papeete, Tahiti"
            onChange={tripDestinationInputChange} 
            className="input-field"
            type="text"
            name="trip_name"/> 
        </h6>

        <h6>Departure and Return Dates:</h6>
          <div className="dates">
            <div className="dates">
              <DateRangePicker 
                defaultValue={defaultValue}
                onChange={tripDate}
                format="MM-dd-yyyy"
                required={true}> {console.log(defaultValue)}</DateRangePicker> 
            </div> 
          </div>

        <h6>Budget: </h6>
          <div class="form-group">
            <div class="input-group mb-3">
              <span class="input-group-text">$</span>
              <input type="text" class="form-control" onChange={tripBudget}></input>
              <span class="input-group-text">.00</span>
            </div>
          </div>

        <h6>Collaborators: </h6>
        {collabList.map((collaborator, index) => (
          <div className="first-division">
            <input
                placeholder="E-mail"
                class="form-control"
                name="email"
                type="text"
                id="email"
                className="collab"
                value={collaborator.email}
                onChange={(e) => changeCollab(e, index)}
                required
            />
          {collabList.length - 1 === index && collabList.length < 50 && (
              <button
                type="button" 
                class="btn btn-info"
                onClick={addCollab}
              > + </button>
          )}
          {collabList.length !== 1 && (
              <button
                onClick={() => removeCollab(index)}
                className="remove-btn"
                type="button" 
                class="btn btn-info">-</button>
          )}
          </div>
        ))}
      </form>
            <br></br>
            <button type="button" class="btn btn-info" onClick={postTrip}>Submit
            
            </button>

            <Routes>
           <Route exact path={"/trips"} element={
                <Trips
            />}
            />
           </Routes>
      </div>
    </div>
  )
}

export default PlanNewTrip;