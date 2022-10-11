# Travel Hub Backend
Collaborators: Lena Duong, Ian Gerbec, and Tiffany Lastimosa

## Iteration 3

### Goals for Iteration 3
- Implement the core functionality of our proposed application, which was to create trips and 
add activities to a calendar.

### Future of the Project
- Revisit the database decision as we might have used Firestore instead of MongoDB to store our 
data and use Firestore for realtime updates and inviting other users when a trip is completed.
- Ensure that data is validated, most likely on the front end using form validation.
- Improve scalability.

## Lena Duong
- Revisited the schema for activity and proposed a revision to add activity to the Trip model.
- Opted to not use Itinerary data collection in the backend and change the data schema.
- Most of my work was done on the front end for iteration 3.

## Ian Gerbec
- Functionality for the Locations backend was completed in iterations 1 and 2
- However, small adjustments were made to [Location.java](http://Location.java) in order for the 
images for locations stored in the database to be accessible.

## Tiffany Lastimosa
- Implemented adding activity to the Trip Controller and Trip Service Class.
![Trips](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/TL-Add-Activity.jpg?raw=true)


## Iteration 2

### Goals for Iteration 2
- Group began on the CRUD operations last week, and completed it this week for this iteration.
- Goal was to complete the CRUD operations and start implementing the UI on the frontend.

## Lena Duong
- Wrote GET/POST/PUT/DELETE methods for Itinerary
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/LD-Iteration2-Postman.png?raw=true)

## Ian Gerbec
- CRUD operations for Locations was completed in iteration 1.
- Only changes to the Locations backend was some simple additions to ensure the frontend
and backend could communicate correctly, such as the implementation of CrossOrigins in the
  Locations controller.
  
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-CrossOrigin-Locations-Controller.jpg?raw=true)

## Tiffany Lastimosa
- CRUD operations for Trips completed in Iteration 1. My focus was on the front end. Please
see README.md on the front end.
- Below is a screenshot of a current POST to show an example of the CRUD operation.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/TL-Iter2-Postman.jpg?raw=true)

## Iteration 1
- For the backend, our group used Spring Boot as it generates all the boilerplate code to implement
the backend. Moreover, most of us are familiar with Java and wanted to learn another way to
implement the backend.
- Spring Boot also provides annotations for the developer to serialize and deserialize data. 

### Goals for Iteration 1
- Our goal for this iteration was to set up the backend in the cloud and deploy the backend
to heroku so that we can make our api calls from the frontend using axios.

## Lena Duong
- Implemented model, controller, and services for the itinerary data.
- Tested api connection through Postman and MongoDB for itinerary.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/MongoDBItinerary.png?raw=true)
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/PostmanItinerary.png?raw=true)

## Ian Gerbec
- For this iteration, I worked on the Locations collection in the travel_hub_db. This included creating
the backend for this collection to allow for api calls to the locations collection. This was
tested using Insomnia, and is able to get all locations in the database, as well as a singular location.
  
### Location Data Model
- Created a location model class 
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-LocationDataModel.png?raw=true)
  
### Activity Data Model
- Created an activity model class 
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-ActivityDataModel.png?raw=true)

### Location Repository
- Much like the trip repository below, this extends from MongoRepository, allowing us to use built-in
CRUD operations
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-LocationRepository.png?raw=true)
  
### Location Service
- Created a LocationService, which contains the business logic. 
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-LocationService.png?raw=true)

### Location Controller
- LocationController connects with the client and passes http requests to the service layer in order
to access the database for data.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/main/screenshots/IG-LocationController.png?raw=true)

## Tiffany Lastimosa
- I worked on the Trip collection in the travel_hub_db, creating the backend to make api calls to
the trip collection. I also tested the api using Postman and assisted with creating the database in
MongoDB Atlas and deploying the backend to heroku.

### Trip Data Model
- Created a Trip model class, which is used to deserialize and serialize data in the database.
- Allows us to create the custom field in the json.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-TripDataModel.jpg?raw=true)

### Trip Repository
- TripRepository is an interface implemented that extends from MongoRepository. This gives us access
to use built-in CRUD operations and also create custom queries.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-TripRepository.jpg?raw=true)

### Trip Service Layer
- TripServiceImpl is a class that contains the business logic. This layer exists between 
the controller and the repository layer.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-TripServiceImpl.jpg?raw=true)

### Trip Controller
- TripController is the class that connects with the client. It receives the http requests that
is then passed onto the TripServiceImpl class to access the database. 
- TripController allows us to use a variety of http status for a better understanding where
an error may lie when making a request.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-TripController.jpg?raw=true)

### MongoDB Trips Collection
- Created the Trips collection in the travel_hub_db.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-MongoDBTrips.jpg?raw=true)

### Postman Trips api Testing
- Tested api with Postman.
![backend](https://github.ccs.neu.edu/NEU-CS5610-SU22/team-emerald-backend-travel-hub/blob/tl-branch/screenshots/TL-TripPostman.jpg?raw=true)

