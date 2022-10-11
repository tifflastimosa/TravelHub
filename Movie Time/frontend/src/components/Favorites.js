import { DndProvider } from "react-dnd";
import { HTML5Backend } from 'react-dnd-html5-backend'
import FavoritesRanked from "./FavoritesRanked";



function Favorites( {user} ) {
    return (
        <DndProvider backend={HTML5Backend}>
            <FavoritesRanked user = {user} />
        </DndProvider>
    )
}

export default Favorites;