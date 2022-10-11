import axios from "axios";

const API_BASE_URI = "http://localhost:8080/"

const getDistanceByActivity = (activity = "") => {
    console.log(activity)
    return axios.get(API_BASE_URI + "activities/" + activity)
}

const getCountByPlace = () => {
    return axios.get(API_BASE_URI + "places/count")
}

const getCalories = () => {
    return axios.get(API_BASE_URI + "activity/count")
}

// exporting an object, a POJO - defining key value pair w/o defining the value
// the functions in the curly braces are the keys, and the implementation above are the values
export {getDistanceByActivity, getCountByPlace, getCalories}
