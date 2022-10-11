import React, {Component} from "react";
import DistanceChart from "./DistanceChart";
import ActivitySelector from "./ActivitySelector";
import {getDistanceByActivity} from "../../DataComponents/APIService";

class DistanceWidgetRoot extends Component {
    // constructor, initialize state to empty array
    constructor(props) {
        super(props);
        this.state = {
            labels: [],
            data: []
        }
        this.getDistances = this.getDistances.bind(this)
    }

    getDistances(activity) {
        let labels = []
        let distances = []

        // have our collection of distances, establishes distances
        // mounts it into the document object
        // the html tree
        getDistanceByActivity(activity)
            .then(response => {
                // function - what are we doing with this data
                for (const activity of response.data) {
                    if (activity.distance < 10000) {
                        labels.push(activity.date)
                        distances.push(parseInt(activity.distance))
                    }
                }
                this.setState({
                    labels: labels,
                    data: distances
                })
            })
            .catch(error => console.log(error))
    }

    // fetch data, set state
    componentDidMount() {
        this.getDistances()
    }

    render() {
        return (
            <div className="container">
                <ActivitySelector handleClick={this.getDistances} />
                <DistanceChart labels={this.state.labels} data={this.state.data} />
            </div>
        );
    }
}

export default DistanceWidgetRoot;
