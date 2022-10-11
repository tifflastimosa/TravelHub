import React, { useState, useEffect } from "react";
import MovieDataService from "../services/movies";
import { Link, useParams, useNavigate } from 'react-router-dom';
import { Card, Container, Image, Col, Row, Button } from "react-bootstrap"

import "../Movie.css";

const Movie = ({ user }) => {

    let params = useParams();
    console.log("params:")
    console.log(params)

    const navigate = useNavigate()

    const [movie, setMovie] = useState({
        id: null,
        title: "",
        rated: "", 
        reviews: []
    });

    useEffect(() => {
        const getMovie = id => {
            // TODO:
            // Implement getMovie - gets movie by id
            MovieDataService.getMovieById(params.id)
                .then(response => {
                    setMovie(response.data)
                    console.log(response.data)
                })
                .catch(e => {
                    console.log(e);
                });
        }
        getMovie(params.id)
    }, [params.id]);

    const deleteFunction = (reviewId, index) => {
        // create data object - pass through to movies.js
        let data = {
            review_id: reviewId,
            user_id: user.googleId
        }
        MovieDataService.deleteReview(data)
        .then(response => {
            console.log(response);
            setMovie((prevState) => {
                prevState.reviews.splice(index, 1);
                return({
                    ...prevState
                })
            })
            navigate("/movies/" + params.id);
            })
        .catch(e => {
            console.log("failure")
            console.log(e);
        });
    }


    return (
        <div className={"background"}>
            <Container>
                <Row>
                    <Col>
                    <div className="poster">
                        <Image
                            id='img'
                            className="bigPicture"
                            src={movie.poster+"/100px250"}
                            onError={({ target }) => {
                                target.onerror = null;
                                target.src="/images/NoPosterAvailable-crop.jpeg";
                            }}
                             />
                    </div>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Header as="h5">{movie.title}</Card.Header>
                            <Card.Body>
                                <Card.Text>
                                    {movie.plot}
                                </Card.Text>
                                { user && 
                                    <Link to={"/movies/" + params.id + "/review"}>
                                        Add Review
                                    </Link>}
                            </Card.Body>
                        </Card>
                        <h2>Reviews</h2>
                        <br></br>
                        { movie.reviews.map((review, index) => {
                            return (
                                <div className="d-flex">
                                    <div className="flex-shrink-0 reviewsText">
                                        <h5>{review.name + " reviewed on "}</h5>
                                        <p className="review">{review.review}</p>
                                        { user && user.googleId === review.user_id &&
                                            <Row>
                                                <Col>
                                                <Link to={{
                                                    pathname: "/movies/" + params.id + "/review"
                                                }}
                                                state = {{
                                                    currentReview: review
                                                }}>
                                                    Edit
                                                </Link>
                                                </Col>
                                                <Col>
                                                <Button variant="link" onClick={ () =>
                                                    // review.user_id, review._id
                                                    // TODO: Implement delete behavior
                                                    {
                                                        deleteFunction(review._id, index)
                                                    }
                                                }>
                                                    Delete
                                                    </Button>
                                                    </Col>
                                            </Row>
                                        }
                                    </div>
                                </div>
                            )
                        })}
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default Movie;