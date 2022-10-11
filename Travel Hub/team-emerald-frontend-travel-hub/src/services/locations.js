import axios from 'axios';

class LocationsDataService {

  getLocations() {
    return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/locations/all`);
  }

  getLocationById(id) {
    return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/locations/${id}`);
  }
}

export default new LocationsDataService();