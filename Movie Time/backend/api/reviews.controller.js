import ReviewsDAO from "../dao/reviewsDAO.js";

export default class ReviewsController {

    /**
     * Method posts or adds a review to a movie.
     * 
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     */
    static async apiPostReview(req, res, next) {
        // method that will handle the POST request
        // method links the routing code from movies.route.js with the data retrieval code
        try {
            const movieId = req.body.movie_id;
            const review = req.body.review;
            const userInfo = {
                name: req.body.name,
                _id: req.body.user_id
            }

            const date = new Date();

            const reviewResponse = await ReviewsDAO.addReview(
                movieId,
                userInfo,
                review,
                date
            );

            var { error } = reviewResponse;
            console.log(error);
            if (error) {
                res.status(500).json({ error: "Unable to post review." });
            } else {
                res.json({ status: "success"});
            }
        } catch (e) {
            res.status(500).json({ error: e.message });
        }

    }

    /**
     * Method puts or updates a review in the database when given an id.
     * 
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     */
    static async apiUpdateReview(req, res, next) {
        try {
            const reviewId = req.body.review_id;
            const review = req.body.review;
            const userId = req.body.user_id;
            const name = req.body.name;

            const date = new Date();

            const reviewResponse = await ReviewsDAO.updateReview(
                reviewId,
                userId,
                name,
                review,
                date
            );
            res.json({ status: "success"});
        } catch (e) {
            res.status(500).json({ error: e.message });
        }
    }

    /**
     * Method deletes a review from the database.
     * 
     * @param {*} req HTTP request object 
     * @param {*} res The response object that they will help to construct
     * @param {*} next Refers to a callback function that can be called - not used here
     */
    static async apiDeleteReview(req, res, next) {
        try {
            const reviewId = req.body.review_id;
            const userId = req.body.user_id;

            const deleteResponse = await ReviewsDAO.deleteReview(
                reviewId,
                userId
            );
            res.json({ status: "success"});
        } catch (e) {
            res.status(500).json({ error: e.message });
        }
    }

}