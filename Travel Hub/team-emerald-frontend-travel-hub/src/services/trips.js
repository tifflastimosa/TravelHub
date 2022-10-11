import axios from 'axios';

class TripDataService {

    createTrip(data) {
        console.log(data);
        return axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips`, data);
    }

    getAllTripsByUser(id) {
        console.log(id)
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/all/${id}`);
    }

    getTripById(id) {
        console.log(id)
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/${id}`);
    }

    addCollaborator(id, userId) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/collaborator` + id, userId);
    }

    updateTrip(data) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips`, data);
    }

    deleteTrip(id) {
        return axios.delete(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/` + id);
    }

    addActivityToTrip(id, data) {
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/add-activity/${id}`, data);
    }
    
    deleteActivityToTrip(id, data) {
        return axios.delete(`${process.env.REACT_APP_API_BASE_URL}/api/v1/trips/delete-activity/${id}`, data);
    }
}

export default new TripDataService();