import {Dropdown} from "react-bootstrap";
import React from "react";

const generateDropdownItem = (activityType, text, action) => {
    return (
        <Dropdown.Item onClick={() => action(activityType)}>
            {text}
        </Dropdown.Item>
    )
}

const ActivitySelector = (props) => {
    const types = ["walking", "running", "kayaking", "cycling", "transport", "cross_country_skiing", "bus"]
    const names = ["Walking", "Running", "Kayaking", "Cycling", "Transport", "Cross-Country Skiing", "Bus"]

    return (
        <Dropdown>
            <Dropdown.Toggle id="dropdown-activity">
                Activity Type
            </Dropdown.Toggle>
            <Dropdown.Menu>
                {types.map((val, idx) => generateDropdownItem(val, names[idx], props.handleClick))}
            </Dropdown.Menu>
        </Dropdown>
    )
}

export default ActivitySelector