// in web programming, "routing" refers to handling requests based on
// their different URLs and HTTP request methods (i.e. GET, POST, PUT, DELETE)
import express from 'express';
import MoviesController from './movies.controller.js';
import ReviewsController from './reviews.controller.js';
import FavoritesController from './favorites.controller.js';
// import FavoritesController from './favorites.controller.js';

const router = express.Router(); // get access tp express router

// URL when it ends in backslash will get all movies
router.route("/").get(MoviesController.apiGetMovies);
// URL when it ends in /id/{unique_id} to get the movie of the
// given id
router.route("/id/:id").get(MoviesController.apiGetMovieById);
// URL when it ends in ratings to get ratings for movies
router.route("/ratings").get(MoviesController.apiGetRatings);
// URL when it ends in review, it will post (add) the review
router.route("/review").post(ReviewsController.apiPostReview);
// URL to update review
router.route("/review").put(ReviewsController.apiUpdateReview);
// URL to delete review
router.route("/review").delete(ReviewsController.apiDeleteReview);

// exports the router as a module so that it can be imported by
// other server.js

router
    .route("/favorites")
    .put(FavoritesController.apiUpdateFavorites);

router
    .route("/favorites/:userId")
    .get(FavoritesController.apiGetFavorites);

router
    .route("/favorites/movies/:userId")
    .get(FavoritesController.apiGetFavoritesMoviesId);


export default router;

// users won't be creating, editing, or deleting movies - thus
// don't need post, put, or delete methods
// ** may need it for ratings and reviews 