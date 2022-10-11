import React, {Component} from "react";
import {getCountByPlace} from "../../DataComponents/APIService";
import PlaceChart from "./PlaceChart";

class PlaceWidgetRoot extends Component {
    // constructor, initialize state to empty array
    constructor(props) {
        super(props);
        this.state = {
            labels : [],
            count : []
        }
        this.getPlaceCount = this.getPlaceCount.bind(this)
    }

    // this is where the data is loaded in
    getPlaceCount() {
        let labels = []
        let count = []
        // have our collection of distances, establishes distances
        // mounts it into the document object
        // the html tree
        getCountByPlace()
            .then(response => {
                // function - what are we doing with this data
                console.log(response)
                const keys = Object.keys(response.data)
                keys.forEach((key, index) => {
                    labels.push(key)
                    count.push(response.data[key])
                })
                this.setState({
                    labels: labels,
                    count: count
                })
            })
            .catch(error => console.log(error))
    }

    componentDidMount() {
        this.getPlaceCount()
    }

    render() {
        return (
            <div className="container">
                <PlaceChart labels={this.state.labels} count={this.state.count} />


            </div>
        );
    }
}

export default PlaceWidgetRoot;
