import MoviesDAO from "../dao/moviesDAO.js";

export default class MoviesController {
    
    /**
     * Method gets the movies, 20 movies per page.
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     */
    static async apiGetMovies(req, res, next) {

        // sets paging information that will be optionally passed in along with the HTTP
        // request 
        const moviesPerPage = req.query.moviesPerPage ?
            parseInt(req.query.moviesPerPage) : 20; // req.query - the values we're interested in
        const page = req.query.page ? parseInt(req.query.page) : 0;

        // set filters on ratings and title based on what are submitted with the query
        let filters = {}
        if (req.query.rated) {
            filters.rated = req.query.rated;
        } else if (req.query.title) {
            filters.title = req.query.title;
        }

        // make the request to the Movies DAO object using its getMovies method 
        // this will return a single page's worth of movies in a list along with a total number
        // of movies found
        const { moviesList, totalNumMovies } = await MoviesDAO.getMovies({ filters, page, moviesPerPage });

        // takes the information retrieved by the DAO, package it up into the response object
        // put that into the http response object as json
        // this response is what will be sent back to the client who queried the API
        let response = {
            movies: moviesList,
            page: page,
            filters: filters,
            entries_per_page: moviesPerPage,
            total_results: totalNumMovies,
        };
        res.json(response);
    }

    /**
     * Method is the http request to get a movie by the id provided in the json body.
     * 
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     * @returns 
     */
    static async apiGetMovieById(req, res, next) {
        // the requests also passes in an id value - req.params.id
        // it should match a unique id
        // method makes a call to the DAO - communicates with the db
        try {
            let id = req.params.id || {}
            let movie = await MoviesDAO.getMovieById(id);
            if (!movie) {
                // no results for the given 
                // error handling when id not found
                res.status(404).json({ error: "not found"});
                return;
            }
            res.json(movie);
        } catch (e) { // other handling error
            console.log(`API, ${e}`);
            res.status(500).json({ error: e});
        }
    }

    /**
     * Get a list of movie ratings we query the DAO. DAO will query the database for all distinct "rating" values in the movie database,
     * yielding a list of ratings to populate our ratings filter drop-down.
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     */ 
    static async apiGetRatings(req, res, next) {
        try {
            let propertyTypes = await MoviesDAO.getRatings();
            res.json(propertyTypes);
        } catch (e) {
            console.log(`API, ${e}`);
            res.status(500).json({ error: e});
        }
    }
}
