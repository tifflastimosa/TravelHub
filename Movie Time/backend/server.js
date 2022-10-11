// server code resides here
// where the Express framework will be put to use
import express from 'express';
import cors from 'cors';
import movies from './api/movies.route.js';

// create our express application 
const app = express();

// .use() method on Express app adds various functionality in the form
// of "middleware"
// "middleware" used handles cross-origin resource sharing (CORS) requests
// and lets us work with JSON in Express
app.use(cors());
app.use(express.json());

// sets up the base URL for our API
// all requets coming on URLs with this prefix will be sent to
// movies.route.js module for routing based on the rest of hte URL
app.use("/api/v1/movies", movies); // at this endpoint, will send requests and receive responses
app.use('*', (req, res) => { // any other endpoing, provide a 404 response error
    // all other urls will recieve a 404 "not found" error response
    res.status(404).json({error: "nout found"});
})

export default app;