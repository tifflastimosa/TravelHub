// conatins the top-level code
// connects the database and the daos
// sets up the exception handling

// import dependencies
import app from './server.js';
import mongodb from "mongodb";
import dotenv from "dotenv";
import MoviesDAO from './dao/moviesDAO.js';
import ReviewsDAO from './dao/reviewsDAO.js';
import FavoritesDAO from './dao/favoritesDAO.js';

async function main() {

    // sets up our environment variables with reference to the .env file
    // accessed with process.env
    // accessed with process.env
    dotenv.config();

    // create 
    const client = new mongodb.MongoClient(
        process.env.MOVIEREVIEWS_DB_URI
    )

    const port = process.env.PORT || 8000;

    try {
        // connect to mongodb server; connect client object to the database
        await client.connect();
        // pass the client object to the DAO
        await MoviesDAO.injectDB(client);
        await ReviewsDAO.injectDB(client);
        await FavoritesDAO.injectDB(client);
        // set the server to listen at the port
        // listen method is implemented in Express
        app.listen(port, () => {
            console.log('Server is running on port: ' + port)
        })
    } catch (e) {
        console.error(e);
        process.exit(1);
    }

}

// main function contains top level code for backend application
// function is declared as async to use asynchronous await 
main().catch(console.error);