import React from 'react'
import { googleLogout } from '@react-oauth/google';
import { Button } from 'react-bootstrap';

// const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

export default function Logout({ setUser }) {

    const handleLogout = () => {
        googleLogout()
        setUser(null);
        console.log("Logged out successfully.");
    }

    return (
        <div>
            <Button variant="info" onClick={handleLogout}> Logout </Button>
        </div>
    )
}