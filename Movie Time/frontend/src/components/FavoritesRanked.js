import "../ranked.css";
import { useState, useEffect, React, useCallback, useRef } from "react";
import update from 'immutability-helper';
import { FaveCard } from "./FaveCard";
import FavoritesDataService from "../services/favorites"
import favorites from "../services/favorites";

// "scripts": {
    // "dev": "react-scripts start",
    // "start": "serve -s build",
    // "build": "react-scripts build",
    // "test": "react-scripts test --env=jsdom",
    // "eject": "react-scripts eject",
    // "heroku-postbuild": "npm run build"
//   },

// "start": "react-scripts start",
// "build": "react-scripts build",
// "test": "react-scripts test",
// "eject": "react-scripts eject"

const FavoritesRanked = ( {user} ) => {

    const [favoriteMovies, setFavoriteMovies] = useState([]);
    const [newRankedIds, setRankedIds] = useState([]);
    const firstUpdate = useRef(true);


    const getFavorites = useCallback(() => {
      // axios call to get favorites array which contains movie ids
      if (user) {
        FavoritesDataService.getFavoritesMovies(user.googleId)
          .then(response => {
            // we get the response data - must iterate to get movie objects
            setFavoriteMovies(response.data);
            // console.log("Response Data");
            // console.log(response.data);
            // want to get a movie and set the movies
        })
        .catch(e => {
            console.log(e);
      });
      }
      
    }, [user]);

    useEffect(() => {
      getFavorites();
    }, [getFavorites]);

    const moveCard = useCallback((dragIndex, hoverIndex) => {
        setFavoriteMovies((prevCards) =>
          update(prevCards, {
            $splice: [ 
              [dragIndex, 1],
              [hoverIndex, 0, prevCards[dragIndex]],
            ],
          }),
        )
        
    }, []);

    const renderCard = useCallback((card, index) => {
      
      // console.log(card.title, index);

      return (
        <FaveCard
          key={card._id}
          index={index}
          text={card.title}
          poster={card.poster}
          moveCard={moveCard}
        />
      )
      }, [moveCard])
    
    const updateRankings = useCallback(() => {

      let newRanked = [];
      if (firstUpdate.current) {
        firstUpdate.current = false;
        return;
      }

      if (user) {
        for (var i = 0; i < favoriteMovies.length; i++) {
            newRanked.push(favoriteMovies[i]._id);
        }

        let newFavoriteObject = {
          _id: user.googleId,
          favorites: newRanked
        }
        console.log("new Fav: ",newFavoriteObject);
        FavoritesDataService.updateFavorite(newFavoriteObject);
      }
    }, [favoriteMovies, user])

    useEffect(() => {
      updateRankings();
    }, [updateRankings]);

      console.log("update");
      console.log(favoriteMovies);
      console.log("update end");

    return (
        <div className="ranked">

          <div className="inner-left">Drag your favorites to rank them</div>

          <div className="inner-right">{favoriteMovies.map((card, i) => renderCard(card, i))}</div>
              
        </div>
    )

}

export default FavoritesRanked;