import {useState, useEffect, useCallback} from 'react';
import { Col, Row } from 'react-bootstrap';
import LocationsDataService from '../services/locations';
import Card from 'react-bootstrap/Card';
import "../styling/Locations.css";
import {Link} from 'react-router-dom';

const Locations = ({user}) => {

  const [locationsList, setLocations] = useState([]);

  const getLocations = useCallback(() => {
      LocationsDataService.getLocations()
        .then(response => {
          setLocations(response.data);
        })
        .catch(e => {
          console.log(e);
        })
  }, [])

  useEffect(() => {
    getLocations();
  }, [getLocations])

  return (
    <div className="App">
      <header>
              <h1>Exploring Locations</h1>
      </header>
      <div className="cardActivity">
      <Row className="locationsRow">
      {locationsList.map((location) => {
        return (
          <Col key={location.id}>
            <Card className="locationsCard">
              <Card.Img
                className="smallImage"
                src={location.image}
              />
              <Card.Body>
                <Card.Title>{location.city}</Card.Title>
                <Card.Text>{location.country}, {location.continent}</Card.Text>
                <Link to={"/locations/"+location.id}>
                  Explore Location
                </Link>
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

export default Locations;