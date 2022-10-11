 import mongodb from "mongodb";
import MoviesController from "../api/movies.controller.js";
const ObjectId = mongodb.ObjectId;

let reviews;

export default class ReviewsDAO {

    // injectDB method used in the same way as in moviesDAO
    static async injectDB(conn) {
        if (reviews) {
            return;
        }

        try {
            reviews = await conn.db(process.env.MOVIEREVIEWS_NS).collection('reviews');
        } catch (e) {
            console.error(`Unable to establish connection handle in reviews DA: ${e}`);
        }
    }

    /**
     * Method adds a review to the database.
     * @param {*} movieId The id of the movie to be associated with the review.
     * @param {*} user The user who wrote the id.
     * @param {*} review The review left by the user.
     * @param {*} date The date the review was posted.
     * @returns 
     */
    static async addReview(movieId, user, review, date) {
        try {
            const reviewDoc = {
                name: user.name,
                user_id: user._id,
                date: date,
                review: review,
                movie_id: ObjectId(movieId)
            }
            return await reviews.insertOne(reviewDoc);
        } catch (e) {
            console.error(`Unable to post review: ${e}`);
            return { error: e }
        }
    }

    /**
     * Method updates a review when given the id of the review and the user id.
     * 
     * @param {*} reviewId The id of the review to be changed.
     * @param {*} userId The id of the user.
     * @param {*} name The name of the user.
     * @param {*} review The review to be updated.
     * @param {*} date The edit date of the review.
     * @returns 
     */
    static async updateReview(reviewId, userId, name, review, date) {
        try {
            console.log(`reviewId: ${reviewId}`);
            console.log(`userId: ${userId}`);
            console.log(`name: ${name}`);
            console.log(`review: ${review}`);
            console.log(`date: ${date}`);
            let query = {"_id" : ObjectId(reviewId), 
                        "user_id": userId
                        };
            const updatedReview = await reviews.updateOne(
                query, 
                {
                    // update operations - $set is an operator
                    // update operation - set will set new values in the document we are updating
                    $set: {
                        review : review,
                        date: date
                     }
                }, {
                    $unset: review
                }
            )
            console.log(query);
            console.log(updatedReview);
            console.log(`Found: ${updatedReview.matchedCount} document(s) matched the query criteria `);
            console.log(`Updated: ${updatedReview.modifiedCount}`);
        } catch (e) {
            console.error(`Unable to update review: ${e}`);
            return { error: e }
        }
    }

    /**
     * Method deletes a review when given the id and the user id.
     * 
     * @param {*} reviewId The id of the to delete.
     * @param {*} userId The id of the user to delete the review.
     * @returns Returns an error if not successful. 
     */
    static async deleteReview(reviewId, userId) {
        try {
            console.log(reviewId);
            console.log(userId);
            let query = {"_id" : ObjectId(reviewId), 
                        "user_id": userId
                        };

            const deleteReview = await reviews.deleteOne(
                query,
            )
            console.log(query);
            console.log(deleteReview);

        } catch (e) {
            console.error(`Unable to delete review: ${e}`);
            return { error: e }
         }
    }

}