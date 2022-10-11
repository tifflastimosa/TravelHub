import {useEffect, useState} from 'react';
import LocationsDataService from '../services/locations';
import TripDataService from '../services/trips';
import { useParams, Link } from 'react-router-dom';
import { Col, Row, Button } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import "../styling/Locations.css";
import {DateTimePicker} from '@progress/kendo-react-dateinputs';
import { DropDownList } from '@progress/kendo-react-dropdowns';
import {guid} from '@progress/kendo-react-common';

const Location = ({ user }) => {

  let params = useParams();

  const [location, setLocation] = useState({
    id: null,
    city: "",
    continent: "",
    country: "",
    image: "",
    activities: []
  });

  const [startDate, setStartDate] = useState(new Date());

  const [endDate, setEndDate] = useState(new Date());

  const [trips, setTrips] = useState([]);

  const [trip, setTrip] = useState({
    id: null
  })

  const addActivity = (id, activity, startDate, endDate) => {
    console.log("id");
    console.log(id);
    console.log("activity");
    console.log(activity);
    let newActivity = {
      id: guid(),
      activity: activity.activity,
      cost: activity.cost,
      start: startDate,
      end: endDate
    }
    TripDataService.addActivityToTrip(id, newActivity);
  }

  useEffect(() => {
    const getTrips = id => {
      TripDataService.getAllTripsByUser(id)
        .then(response => {
          setTrips(response.data);
        })
        .catch(e => {
          console.log(e);
        })
    }
    if (user) {
      getTrips(user.googleId);
    }
  }, [user])

  useEffect(() => {
    const getLocation = id => {
      LocationsDataService.getLocationById(id)
        .then(response => {
          setLocation({
            id: response.data.id,
            city: response.data.city,
            continent: response.data.continent,
            country: response.data.country,
            image: response.data.image,
            activities: response.data.activities
          })
        })
        .catch(e => {
          console.log(e);
        })
    }
    getLocation(params.id);
  }, [params.id])

  const handleStartChange = (event) => {
    setStartDate(event.target.value);
    //setActivitySelected({...activitySelected, start: event.target.value});
    console.log(startDate);
  };

  const handleEndChange = (event) => {
    setEndDate(event.target.value);
  }

  const handleDropDown = (event) => {
    console.log(event.target.value.id);
    setTrip({
      id: event.target.value.id
    })
  }

  const defaultItem = {
    trip_name: "Pick Trip for Activity"
  }

  return (
    <div className="App">
      <header>
        <div>
            <h1>Exploring Activities for {location.city}</h1>
        </div>
      </header>
      <div className="cardActivity">
      <Row className="locationsRow">
        {location.activities.map((activity) => {
          return (
            <Col key={activity.id}>
              <Card className="activitiesCard">
                <Card.Img
                  className="smallImage"
                  src={activity.image}
                />
                <Card.Body>
                  <Card.Title>
                    <a href={activity.link} target="_blank" rel="noreferrer">{activity.activity}</a>
                  </Card.Title>
                  <Card.Text className='mediumFont'>Cost: ${activity.cost}</Card.Text>
                  <Card.Text className="mediumFont">
                    Select Start Date and Time
                    <DateTimePicker
                      format={"MM/dd/yyyy hh:mm a"}
                      width={250}
                      onChange={handleStartChange}
                      value={startDate}
                    />
                  </Card.Text>
                  <Card.Text className="mediumFont">
                    Select End Date and Time
                    <DateTimePicker
                      format={"MM/dd/yyy hh:mm a"}
                      width={250}
                      onChange={handleEndChange}
                      value={endDate}
                    />
                  </Card.Text>
                  <DropDownList 
                    className="locationsButton"
                    defaultItem={defaultItem}
                    data={trips}
                    textField="trip_name"
                    onChange={handleDropDown}/>
                  <Card.Text>
                    <Link to={`/trips/${trip.id}`}>
                      <Button variant="info" className="locationsButton" onClick={() => addActivity(trip.id, activity, startDate, endDate)}>Add Activity to Trip</Button>
                    </Link>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          )
        })}
      </Row>
      </div>
    </div>
  )
}

export default Location;