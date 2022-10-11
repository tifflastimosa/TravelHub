// DAO is how the controller methods are getting data 
import mongodb from "mongodb";
const ObjectId = mongodb.ObjectId;

let movies;

export default class MoviesDAO {

    /**
     * Method retrieves the movies collection from the database.
     * 
     * @param {*} conn 
     * @returns The movie collection from the database.
     */
    static async injectDB(conn) {
        if (movies) {
            return;
        }
        try {
            movies = await conn.db(process.env.MOVIEREVIEWS_NS)
                            .collection('movies');
        } catch (e) {
            console.error(`Unable to connect in MoviesDAO: ${e}`);
        }
    }

    /**
     * Method gets all the movies in the database.
     * 
     * @param {*} param0 
     * @returns Returns the entire collection of movies.
     */
    static async getMovies({
        // pass in default parameters
        filters = null,
        page = 0,
        moviesPerPage = 20,
    } = {}) { // empty object is default parameter in case arg is undefined
        let query;
        // construct the query based on whther title or rated filter values exist
        if (filters) {
            if ("title" in filters) {
                query = { $text: { $search: filters['title']}};
            } else if ("rated" in filters) {
                query = { "rated": { $eq: filters['rated']}}
            }
        }

        let cursor; // the acutal query using MongoDB cursor object
        // it is capable of iterating over a database and returning incremental results
        // this will enable us to return a page's worth of movies (default 20) at a time
        try {
            cursor = await movies.find(query)
                                 .limit(moviesPerPage)
                                 .skip(moviesPerPage * page);
            const moviesList = await cursor.toArray();
            const totalNumMovies = await movies.countDocuments(query);
            return {moviesList, totalNumMovies};
        } catch(e) {
            console.error(`Unable to issue find command, ${e}`);
            return { moviesList: [], totalNumMovies: 0 };
        }
    }

    /**
     * Method gets the ratings of movies.
     * 
     * @returns The ratings of movies. 
     */
    static async getRatings() {
        let ratings = [];
        try {
            ratings = await movies.distinct("rated");
            return ratings;
        } catch(e) {
            console.error(`Unable to get ratings, ${e}`);
            return ratings;
        }
    }

    /**
     * Accessing multiple documents using aggregation in MongoDB. Methods gets a movie
     * by the id.
     * 
     * @param {*} id Unique id of the movie.
     * @returns json data of the movie in the database.
     */
    static async getMovieById(id) {
        try {
            // returns the movie by the aggregate
            return await movies.aggregate([
                {
                    $match: {
                        _id: new ObjectId(id),
                    }
                },
                { // returns all the review for that movie - this is left outer
                    $lookup: {
                        from: 'reviews',
                        localField: '_id',
                        foreignField: 'movie_id',
                        as: 'reviews',
                    }
                }
            ]).next();
        } catch(e) {
            console.error(`Something went wrong in getMovieById: ${e}`);
            throw e;
        }
    }
}