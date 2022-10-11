import FavoritesDAO from "../dao/favoritesDAO.js";
import MoviesDAO from "../dao/moviesDAO.js";

export default class FavoritesController {

    static async apiUpdateFavorites(req, res, next) {
        try {
            const FavoritesResponse = await FavoritesDAO.updateFavorites(
                req.body._id,
                req.body.favorites
            )

            var { error } = FavoritesResponse
            if (error) {
                res.status(500).json({ error });
            }

            // if (FavoritesResponse.modifiedCount === 0) {
            //     throw new Error ("Unable to update favorites.")
            // }
            // res.json({ status: "success "});
            res.json({ status: "success "});
        } catch (e) {
            res.status(500).json({ error: e.message })
        }
    }

    static async apiGetFavorites(req, res, next) {
        try {
            let id = req.params.userId;
            let favorites = await FavoritesDAO.getFavorites(id);
            if (!favorites) {
                console.log("api favorites id: " + id);
                res.status(404).json({ error: "not found id" });
                return;
            }
            res.json(favorites);
        } catch(e) {
            console.log(`API, ${e}`);
            res.status(500).json({ error: e });
        }
    }

    static async apiGetFavoritesMoviesId(req, res, next) {
        try {
            let id = req.params.userId;
            let favoriteMovies = await FavoritesDAO.getFavoriteMovieIds(id);
            let favoriteMoviesAdjusted = [];
            for (var i = 0; i < favoriteMovies.length; i++) {
                // console.log(favoriteMovies[i]);
                let movie = await MoviesDAO.getMovieById(favoriteMovies[i]);
                let movieAdjusted = {
                    _id: movie._id,
                    title: movie.title,
                    poster: movie.poster
                }
                favoriteMoviesAdjusted.push(movieAdjusted);
            }
    
            console.log(favoriteMoviesAdjusted);
            
            if (!favoriteMovies) {
                console.log("api favorites id: " + id);
                res.status(404).json({ error: "not found id" });
                return;
            }
            res.json(favoriteMoviesAdjusted);
        } catch(e) {
            console.log(`API, ${e}`);
            res.status(500).json({ error: e });
        }
    }

}