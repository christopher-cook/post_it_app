# Post_It_API
---
 
### Approach

The approach we took was to build out the User, Post, Comment, Profile layers in tandem with Auth thus allowing us to avoid code refactoring later on. Once basic functionality was met we began integrating the front-end application and handled exception handling via Postman. Unit Testing accounted for the breadth of our project as we tried to hit on every instruction not neccesarily just meet the minimum coverage. Final steps were to test making sure no breaks occurred during fetches and all exceptions were handled.

* ERD
![ERD Diagram](https://i.imgur.com/l2DZYPq.png)
* [Pivitol tracker](https://www.pivotaltracker.com/n/projects/2407483)
  * User Stories/Wireframes
* [Timeline](https://github.com/christopher-cook/post_it_app/wiki/Timeline)
* [Trello]()
  * individual task assignments
---

### Challenges
---
1. User signup needs a user role to obtain an authority level. Since we designed the API to not expose user role to regular user, we decided to use a default role, 'ROLE_USER', for user sign-up. Only Admin can access url to grant any other level of authorities.
2. When integrating front-end app came across a few different CORS related issues.

### Technologies Implemented
---
* Spring framework
* Hibernate (persistence)
* Maven (project object model)
* Dependencies
  * junit
  * jackson-databind
  * spring-webmvc
  * spring-test
  * mockito-core
* Mockito (testing)
 

### Contributors
---
Qiming Chen

Christopher Cook
