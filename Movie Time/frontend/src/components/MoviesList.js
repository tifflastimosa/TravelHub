import React, { useState, useEffect, useCallback } from "react";
import MovieDataService from "../services/movies"
import { Link } from "react-router-dom";
import { Form, Button, Col, Row, Container, Card } from "react-bootstrap"
import { BsStar, BsStarFill } from "react-icons/bs"

import "../MoviesList.css";

// big component with a lot of functionality 
// displayes the full list of movies broken into pages of up to 20 movies per page
// will enable riltering on title and rating
const MoviesList = ({

    user, 
    favorites, 
    addFavorite,
    deleteFavorite}) => {
    // useState hooks - React hooks new approach to handling state and effects in react
    // functional components

    // useState to set state values
    const [movies, setMovies] = useState([]);
    const [searchTitle, setSearchTitle] = useState("");
    const [searchRating, setSearchRating] = useState("");
    const [ratings, setRatings] = useState(["All Ratings"]);
    const [currentPage, setCurrentPage] = useState(0);
    const [entriesPerPage, setEntriesPerPage] = useState(0);
    const [currentSearchMode, setCurrentSearchMode] = useState("");

    const retrieveRatings = useCallback(() => {
        MovieDataService.getRatings()
        .then(response => {
            setRatings(["All Ratings"].concat(response.data))
        })
        .catch(e => {
            console.log(e);
        });
    }, []);

    const retrieveMovies = useCallback(() => {
        setCurrentSearchMode("");
        MovieDataService.getAll(currentPage)
            .then(response => {
                setMovies(response.data.movies);
                setCurrentPage(response.data.page);
                setEntriesPerPage(response.data.entries_per_page);
            })
            .catch(e => {
                console.log(e);
            });
    }, [currentPage]);

    const find = useCallback((query, by) => {
        MovieDataService.find(query, by, currentPage)
            .then(response => {
                setMovies(response.data.movies);
            })
            .catch(e => {
                console.log(e);
            });
    }, [currentPage]); 

    const findByTitle = useCallback(() =>{
        setCurrentSearchMode("findByTitle");
        find(searchTitle, "title");
    }, [find, searchTitle]);

    const findByRating = useCallback(() => {
        setCurrentSearchMode("findByRating");
        if (searchRating === "All Ratings") {
            retrieveMovies();
        } else {
            find(searchRating, "rated");
        }
    }, [find, searchRating, retrieveMovies]);

    const retrieveNextPage = useCallback(() => {
        if (currentSearchMode === "findByTitle") {
            findByTitle();
        } else if (currentSearchMode === "findByRating") {
            findByRating();
        } else {
            retrieveMovies();
        }
    }, [currentSearchMode, findByTitle, findByRating, retrieveMovies]);

    useEffect(() => {
        retrieveRatings();
    }, [retrieveRatings]);

    useEffect(() => {
        setCurrentPage(0);
    }, [currentSearchMode]);

    useEffect(() => {
        retrieveNextPage();
    }, [currentPage, retrieveNextPage]);

    const onChangeSearchTitle = e => {
        const searchTitle = e.target.value;
        setSearchTitle(searchTitle);
    }

    const onChangeSearchRating = e => {
        const searchRating = e.target.value;
        setSearchRating(searchRating);
    }

    return (
        <div className="App">
            <Container className="main-container">
                {/* this is creating a search bar */}
                <Form> 
                    <Row>
                        <Col>
                        <Form.Group className="mb-3">
                            <Form.Control
                                type="text"
                                placeholder="Search by title"
                                value={searchTitle}
                                onChange={onChangeSearchTitle}
                            />
                        </Form.Group>
                        {/* creates the search button - telling the button to use the findByTitle function on click */}
                        <Button
                            variant="primary"
                            type="button"
                            onClick={findByTitle}
                        > Search
                        </Button>
                        </Col>
                        <Col>
                            <Form.Group className="mb-3">
                                <Form.Control
                                    as="select"
                                    onChange={onChangeSearchRating}
                                >
                                    { ratings.map((rating, i) => {
                                        return (
                                            <option value={rating} key={i}>
                                                {rating}
                                            </option>
                                    )})}
                                </Form.Control>
                            </Form.Group>
                            <Button
                                variant="primary"
                                type="button"
                                onClick={findByRating}
                            >
                                Search
                            </Button>
                        </Col>
                    </Row>
                </Form>
                < Row className="movieRow" >
                    { movies.map((movie) => {
                        return (
                            <Col key={movie._id}>
                                <Card className="moviesListCard">
                                    { user && ( 
                                        favorites.includes(movie._id) ?
                                    <BsStarFill id="star" className="star starFill" onClick={() => {
                                        deleteFavorite(movie._id);
                                    }} />
                                    :
                                    <BsStar id="star" className="star starEmpty" onClick={() => {
                                        addFavorite(movie._id);
                                    }} />
                                    )}
                                    <Card.Img
                                        className="smallPoster"
                                        src={movie.poster+"/100px180"} 
                                        onError={({ target }) => {
                                            target.onerror = null;
                                            target.src="/images/NoPosterAvailable-crop.jpeg";
                                        }}
                                    />

                                        <Card.Body>
                                            <Card.Title> {movie.title} </Card.Title>
                                            <Card.Text>
                                                Rating: {movie.rated}
                                            </Card.Text>
                                            <Card.Text>
                                                {movie.plot}
                                            </Card.Text>
                                            <Link to={"/movies/"+movie._id}>
                                                View Reviews
                                            </Link>
                                        </Card.Body>
                                </Card>
                            </Col>
                        )
                    })}
                </Row>
                <br />
                Showing page: { currentPage + 1 }.
                <Button 
                    variant="link"
                    onClick={() => { setCurrentPage(currentPage + 1)}}
                    >
                        Get next { entriesPerPage } results
                    </Button>
            </Container>
        </div>
    )
}

export default MoviesList;