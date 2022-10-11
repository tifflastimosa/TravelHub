import axios from 'axios';

class ItineraryDataService {

    createItinerary(data) {
        return axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/v1/itinerary`, data);
    }

    getItineraryById(id) {
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/itineraryne/${id}`);
    }

    deleteItinerary(id) {
        return axios.delete(`${process.env.REACT_APP_API_BASE_URL}/api/v1/itinerary/delete${id}`);
    }
    
    updateItinerary(id) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/itinerary/update${id}`);
    }

    addItineraryActivity(data, id) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/interary/activity${id}`, data);
    }
    
    deleteItineraryActivity(data, id) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/interary/activity${id}`, data);
    }

    deleteItineraryBooking(data, id) {
        return axios.delete(`${process.env.REACT_APP_API_BASE_URL}/api/v1/interary/activity/delete${id}`, data);
    }

    addItineraryBooking(data, id) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/itinerary/activity${id}`, data);
    }
}

export default new ItineraryDataService();