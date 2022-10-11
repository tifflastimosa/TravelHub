# The Quantified Self Application

A web application that reads a json file , parses the json file, and inserts the parsed data into NoSQL database, MongoDB.  Moreover, this application utilizes RESTful api, which utilizes the parsed data from the collections in the database to make HTTP requests to make visual representations of the data.

## Features

### User Stories:
* As an average person, I want to view my calories burned.
* As a person, who is training to run, I want to track my start time, end time, and distance.
* As a commuter, I want to view the distance traveled.

### Features:
* Import and parse a .json file
* Range of queries supported:
  * Activity Count by Year
  * Calories by Year
  * Filter list activities by Activity
  * Filter places by name of the places
  * Filter places by year
  * Count Places by Place
* Add Activity, Update Activity, Delete Activity from the database to track an Activity
* Add Place, Update Place, Delete Place from the database to track a Place

* After the final presentation, we promised to have a front end that represented the data and to implement a full RESTful api service that would allow the user or client to not only make GET requests, but also use POST, PUT, and DELETE requests.

* Please see the following link below for a screenshot of the features implemented. </br>
[Features Screenshots](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/Features-Screenshots.pdf)

## Assumptions

* Assumed that the .json file inputted into the program will contain the same field and attributes.
* There is a check for json, otherwise the program will continue to run until you stop it or .json is inputted.
* When user input becomes an added feature, the user or client will put in the accurate input such that it will go to the appropirate collection. Our code does not fully handle cases at this time.
* Assuming that the user or client is comfortable with MongoDB Atlas and React.
* Inferred that a summary in the summaries array represented a collection of all activities in segments array for an Entry, which is a single json object in the json array.

## Getting Started

* This project was built in IntelliJ using Spring Boot.

* Download the quantifiedself package onto your local machine.

* When getting started, the back end and part of the front end will be running on port 8080, while the other part of the front end is running on port 3000.  Please note that you may have to utilize the back button in your browser to get back to the webpage landing on part 3000.

### Building Java Program

Please see the pom.xml file for dependencies. </br>

To build the Java program, open the quantifiedself package in IntelliJ, or preferred IDE.  Navigate to the pom.xml and load the dependencies and build the project. </br>

Please note that the database should be loaded with the storyline.json file, thus there should be no need to inject the storyline.json when running the program. </br>

You should be able to make CRUD requests in Postman. </br>

### Front End

To setup the front end, in your terminal, change your directory to quantifiedself.  Once in said package, type the below in:

```
cd app
npm install
```

npm install will install and load the dependencies to run the front end.  From there, once you have the QuantifiedSelfApplciation running, in your terminal, input ```npm start```


## Documentation

* Please see javadocs in our project for additional documentation </br>

* REST api Documentation </br>
[REST Documentation](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/REST-Documentation.pdf)


## Testing
* Testing is satisfactory.  Overall, our ending result showed a vast improvement in our CodeMR report from Sprint 2 to the ending product.  We saw improvement of our CodeMR after refactoring and utilizing features from creational patterns such as Builder Pattern and Factory Pattern.  There is much to improve on, however, we are satisfied with our CodeMR report. For a more detailed report regarding CodeMR, please see the following link. </br>

  * [CodeMR](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/CodeMR-Report.pdf)

* Testing could be improved in Postman. Postman was a new tool we utilized, and to do the testing we had to learn a little bit of javascript to do so.  For a more detailed report regarding Postman testing, please see the following link. </br>
  * [Postman REST Testing](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/Postman-Testing.pdf)</br>
  * [Post REST Testing - json](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/Quantified%20Self%20API%20test.postman_test_run.json)</br>


* It is important to note, testing was satisfactory, but revealed the improvements we need to do to make this web application fully featured.  For unit testing, please see the test folder in the quantifiedself project.  Some of our tests failed, but it is important to make note of these tests so that we can use that to revisit our code and do any necessary debugging. </br>

## System design
![quantifiedself uml diagram](https://github.com/tifflastimosa/Portfolio/blob/main/Quantified%20Self%20Web%20Application/quantified-self-uml.png)</br>

   For this project, we implemented the Model View Controller (MVC) design pattern to develop our web application because of the commonality of use for user interface. The model contained entries which contained segments and summaries. The segment contained move segments and place segments, where a move segment is a list of activities. The summaries is a summary of the activities performed for that entry.  In doing this, we created an activities, entries, and place collection.  </br>

   Next, we implemented the RESTful api.  Our design used a Controller, Service, and Repository structure.  This design pattern allows us to have the business logic in the Service layer.  In the Service layer, this is where we wrote the logic to do our CRUD requests and custom queries.  Moreover, by utilizing this design, this allowed for less coupling between the Controller and the Repository layers. </br>

   We attempted to follow the SOLID principles so there are multiple classes following this design. </br>

## Future of the Project

### Current Known Bugs/Issues
* For some parts of the activities, such as calories and count, it is utilizing the entries collection, and there is no support for adding, updating, or deleting.  For the REST api, activity aggregation queries were inserted in the entries collection, which violates the single responsibility principle in SOLID.
* When there is a GET request for the entire entries collection, a NullPointerException is thrown.
* The data for SummaryEntry needs to be looked into further as calories is not setting and there are some errors with the values not equating as it should in the unit testing.
* In reviewing our REST testing in Postman, this revealed the time it took to get a response.  We would need to review if the response time is sufficient or if we need to look more at our design.  It could also be that we need to increase our threshold for the time it takes to process a request, and determine if that threshold is valid.

### Future of the Project
* Create forms/pages/components that will allow the user or client to add, update, or delete an Activity from the activities collection or Place from the place collection.  This will allow the user or client to interact with the database and the dynamic visualizations of the data.
* Allowing the backend to parse different file formats such as .csv to inject into the database.
* Updating the visualizations, such as the Activity Count webpage. 
* For the backend, adding support for different types of attributes because it only parses what we know is there.
* Making the backend modular to allow for a different database to be used by the client or user.
