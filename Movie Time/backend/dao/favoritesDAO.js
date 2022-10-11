import MoviesDAO from "./moviesDAO.js";

let favoritesCollection;

export default class FavoritesDAO {

    // initializes a connection 
    static async injectDB(conn) {
        if (favoritesCollection) {
            return;
        }
        try {
            favoritesCollection = await conn.db(process.env.MOVIEREVIEWS_NS)
                            .collection('favorites');
        } catch (e) {
            console.error(`Unable to connect in FavoritesDAO: ${e}`);
        }
    }

    // this updates a single document
    // upsert: true settuing ensures that if an entry does not exist for the user it is created, otherwise is updated 
    static async updateFavorites(userId, favorites) {
        try {
            const updateResponse = await favoritesCollection.updateOne(
                { _id: userId }, 
                { $set: {favorites: favorites} },
                { upsert: true }
            )
            return updateResponse
        } catch(e) {
            console.error(`Unable to update favorites: ${e}`);
            return { error: e };
        }
    }

    static async getFavorites(id) {
        let cursor;
        try {
            // this carries out a find operation on the database with the user's ID as the key
            // there will only ever be one value returned, which will represent the list of favorites
            // thus, take the 0 indexed element of the returned array
            cursor = await favoritesCollection.find({
                _id: id
            });
            const favorites = await cursor.toArray();
            return favorites[0];
        } catch(e) {
            console.error(`Something went wrong in getFavorites: ${e}`);
            throw e;
        }
    } 

    static async getFavoriteMovieIds(id) {
        let cursor;
        let favoriteMovies = [];
        let favoriteMoviesAdjusted = [];

        try {
            // this carries out a find operation on the database with the user's ID as the key
            // there will only ever be one value returned, which will represent the list of favorites
            // thus, take the 0 indexed element of the returned array
            cursor = await favoritesCollection.find({
                _id: id
            });
            const favorites = await cursor.toArray();
            console.log("favorites[0] - favorites");
            favoriteMovies = favorites[0].favorites;
            console.log(favoriteMovies);

            return favoriteMovies;
        } catch(e) {
            console.error(`Something went wrong in getFavorites: ${e}`);
            throw e;
        }
    }
    

}