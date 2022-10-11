import axios from "axios";

class FavoritesDataService {

    getFavorites(id) {
        console.log("getFavorites - id: " + id)
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/favorites/` + id)
    }

    getFavoritesMovies(id) {
        console.log("getFavorite - movies: " + id);
        return axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/favorites/movies/` + id);
    }

    updateFavorite(data) {
        // console.log("updateFavorite: " + JSON.stringify(data));
        return axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/v1/movies/favorites`, data );
    }

}

export default new FavoritesDataService(); 