import "./App.css";
import { useState, useEffect, React, useCallback, useRef } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Nav, Navbar } from "react-bootstrap";
import { Link, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import Login from "./components/Login";
import Logout from "./components/Logout";

import MoviesList from "./components/MoviesList";
import Movie from "./components/Movie";
import AddReview from "./components/AddReview";
import FavoritesDataService from "./services/favorites"
import Favorites from "./components/Favorites";


const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

function App() {

  const [user, setUser ] = useState(null);
  const [favorites, setFavorites] = useState([]); // hook initialized on an empty array
  const [faveState, setFaveState] = useState(false);
  
  // reference: https://stackoverflow.com/questions/53253940/make-react-useeffect-hook-not-run-on-initial-render
  // const firstUpdate = useRef(true);
  // uses array destructuring syntax to add a movie ID number to the list
  const addFavorite = (movieId) => {
    setFaveState(true);
    console.log("add favorite: " + movieId);
    setFavorites([...favorites, movieId])
  }

  // create a new array w/o the id to be deleted
  const deleteFavorite = (movieId) => {
    console.log("delete favorite: " + movieId);
    setFavorites(favorites.filter(f => f !== movieId));
  }

  const getFavorites = useCallback(() => {
      console.log("getFavorites: " + user.googleId);
      FavoritesDataService.getFavorites(user.googleId)
        .then(response => {
          console.log("getFavorites - response: ", response);
          setFavorites(response.data.favorites)
        })
        .catch(e => {
          console.log(e);
        });
    }, [user]);
 
  const updateFavorite = useCallback(() => {
        // get the json type
        // need the user id and the movie id
        let favorite = {
            _id: user.googleId,
            favorites: favorites
        }
        // console.log("favorite: " + favorite)
        console.log("favorites");
        console.log(favorites);
        console.log("end favorites");
        FavoritesDataService.updateFavorite(favorite)
          .then(response => {
            console.log("updateFavorite - response: " + response);
          })
          .catch(e => {
            console.log(e);
          });
      // }
    
  }, [favorites, user]);

  useEffect(() => {
    let loginData = JSON.parse(localStorage.getItem("login"));
    if (loginData) {
      let loginExp = loginData.exp;
      let now = Date.now()/1000;
      if (now < loginExp) {
        // Not expired
        setUser(loginData);
      } else {
        // Expired
        localStorage.setItem("login", null);
      }
    }
  }, []);

  useEffect(() => {
    if (user) {
      console.log("use effect: " + user.googleId);
      getFavorites();
    }
  }, [getFavorites, user]);

  useEffect(() => {
    if (user && faveState) {
      updateFavorite();
      setFaveState(false);
    }
    
  }, [user, favorites, faveState, setFaveState, updateFavorite]); // add the dependency - updateFavorite

  return (
    <GoogleOAuthProvider clientId={clientId}>
    <div className="App">
  
      <Navbar bg="primary" expand="lg" sticky="top" variant="dark">
        <Container className="container-fluid">
        <Navbar.Brand className="brand" href="/">
          <img src="/images/movies-logo.png" alt="movies logo" className="moviesLogo"/>
          MOVIE TIME
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav" >
          <Nav className="ml-auto">
            <Nav.Link as={Link} to={"/movies"}>
              Movies
            </Nav.Link>
            {user && (<Nav.Link as={Link} to={"/favorites"}>
              Favorites
            </Nav.Link>)}
          </Nav>
        </Navbar.Collapse>
        { user ? (
              <Logout setUser={setUser} />
              ) : (
              <Login setUser={setUser} />
              )
        }
        </Container>
      </Navbar>

      <Routes>
        <Route exact path={"/"} element={
          <MoviesList 
            user={ user }
            addFavorite={ addFavorite }
            deleteFavorite={ deleteFavorite }
            favorites={ favorites }/>}
          />
        <Route exact path={"/movies"} element={
          <MoviesList 
            user={ user }
            addFavorite={ addFavorite }
            deleteFavorite={ deleteFavorite }
            favorites={ favorites }
            />}
          />
        <Route exact path={"/movies/:id/"} element={
          <Movie user = {user} />}
          />
        <Route path={"/movies/:id/review"} element={
            <AddReview user = { user } /> }
          />
        <Route 
          path={"/favorites"} 
          element={
            user ? 
            <Favorites 
              user = { user }
              favorites = { favorites }
              faveState = { setFaveState }
            />
            :
            <MoviesList/>
          } />
      </Routes>

    </div>
    </GoogleOAuthProvider>
  );
}

export default App;
