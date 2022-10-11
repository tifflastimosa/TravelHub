import axios from "axios";

class MovieDataService {

    // each of these functions use Axios to make GET request to the API which was implemented
    // in the backend
    getAll(page = 0) {
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies?page=${page}`);
    }

    find(query, by="title", page =0) {
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies?${by}=${query}&page=${page}`)
    }

    getRatings() {
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/ratings`);
    }

    getMovieById(id) {
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/id/${id}`)
    }

    createReview(data) {
        return axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/review`, data);
    }

    updateReview(data, reviewId) {
        console.log("Put Request");
        let newData = { review_id: reviewId, review: data.review, user_id: data.user_id, name: data.name };
        console.log("New Data: ", newData);
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/review`, newData);
    }
    
    deleteReview(data) {
        console.log("delete review data: ", data)
        return axios.delete(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/review`, { data });
    }
} 

export default new MovieDataService();