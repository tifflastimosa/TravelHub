import React from "react";
import '../styling/LandingPage.css'
import Carousel from 'react-bootstrap/Carousel'

const Home = () => {

    
    return (
        <div className="App">
             <header>
                <div class="hero-image">
                <div className="title">
                        <br></br>
                        <h1>Welcome to Travel Hub</h1>
                </div>
                </div>
            </header>
            <body>
              <div className="about-text">
                <h6>The platform makes traveling easier by providing a selection of activities to explore and coordinate with a group!</h6>
              </div>
            <Carousel>
              <Carousel.Item>
                <img
                  className="d-block w-100"
                  src={process.env.PUBLIC_URL + 'images/carousel/charles-postiaux-TnUG2pWraPE-unsplash.jpg'}
                  alt="First slide"
                />
                <Carousel.Caption>
                  <h3>Explore New Places</h3>
                  <p>Japan</p>
                </Carousel.Caption>
              </Carousel.Item>
              <Carousel.Item>
                <img
                  className="d-block w-100"
                  src={process.env.PUBLIC_URL + 'images/carousel/joss-woodhead-3wFRlwS91yk-unsplash.jpg'}
                  alt="Second slide"
                />

                <Carousel.Caption>
                  <h3>Create Amazing Memories</h3>
                  <p>Austria</p>
                </Carousel.Caption>
              </Carousel.Item>
              <Carousel.Item>
                <img
                  className="d-block w-100"
                  src={process.env.PUBLIC_URL + '/images/carousel/michael-discenza-5omwAMDxmkU-unsplash.jpg'}
                  alt="Third slide"
                />

                <Carousel.Caption>
                  <h3>Dive into the Culture</h3>
                  <p>
                    New York
                  </p>
                </Carousel.Caption>
              </Carousel.Item>
            </Carousel>
          </body>
        </div>
    )
}

export default Home;