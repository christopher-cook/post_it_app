# Post_It_API
---
 
This is the backend program written in java providing backend functionality for the frontend of project 1.

### Technologies Implemented
---
* Java EE
* Spring framework
* Hibernate (persistence)
* Maven (project object model)
* PostgreSQL
* Dependencies
  * junit
  * jackson-databind
  * spring-webmvc
  * spring-test
  * mockito-core
* Mockito and PowerMockito (unit testing)

### Approach

The approach we took was to build out the User, Post, Comment, Profile layers in tandem with Auth thus allowing us to avoid code refactoring later on. Once basic functionality was met we began integrating the front-end application and handled exception handling via Postman. Unit Testing accounted for the breadth of our project as we tried to hit on every instruction not neccesarily just meet the minimum coverage. Final steps were to test making sure no breaks occurred during fetches and all exceptions were handled.

##### Database
* ERD for the clarity of database entity relationship
![ERD Diagram](https://i.imgur.com/l2DZYPq.png)

##### Software Architecture Design and Implementation
* CRUD for designing routes
* DRY (don't repeat yourself) and KISS (keep it simple, stupid)
* UML to visualize the system structure

### ERD
![ERD Diagram](https://i.imgur.com/l2DZYPq.png)

### Agile Development
* [Pivitol tracker](https://www.pivotaltracker.com/n/projects/2407483)
  * User Stories/Wireframes
* User Stories to break down requirements and concepts into features
*** we broke down the features and steps into Epics, User Stories, and Tasks to manage the progress
* Continuous Integration & Early Deliverables
*** we kept on testing the functionality with postman and integrating the frontend to ensure the deliverables along the way
  
### Extreme Programming
* Pair Programming

### Time Management
* [Timeline](https://github.com/christopher-cook/post_it_app/wiki/Timeline)
* [Trello]()
  * individual task assignments
  
### Challenges:

1. CORS

-- When integrating front-end app came across a few different CORS related issues.

2. Request Authentication and its unit test for controller

-- To extract the authentication information, we added one argument for the Authentication object in the controller functions.

-- Directly placing the Authentication object in controller function led to duplicate code and a harder setup for the unit test with mockito MVC builder, so we used SecurityContextHandler to get the authentication information in a separate function, which made the code isolated and easier for testing.

-- The above solution 

3. Unit test for static methods and constructors

-- Mockito doesn't support mocking for static methods and constructors, we adopted PowerMockito partially to overcome this case.

### Future improvements

1. User Role

--- The backend has only one role "ROLE_USER". For a better differentiation of user group, we need to implement more methods for UserRole entity.

--- user signup needs a user role to obtain an authority level for security configuration, but we designed not to expose the user role setting to regular user, so we used a default role 'ROLE_USER' for user signing up and only Admin can user /role url to grant any other level of authorities.

2. Higher unit testing coverage


### Contributors
---
Qiming Chen

Christopher Cook
