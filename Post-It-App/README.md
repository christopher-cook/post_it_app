# S1-Project-1

Contributor: Benjamin Karasik, Qiming Chen

This project aims to build a reddit-like front-end application. This app is expected to serve as a forum for users to signup/login their accounts, create and manage posts, comments, and profiles, and this brings up the user stories, features list, and furthermore a sketch of UI based on the features.

Thus, the application structure is defined to be a multi-page application, in which multiple webpages are integrated to construct this app, and each html / css / javascript scripts are modularized to respond for only one page. 

# User Stories and Features 
### User Stories

[Pivotal Tracker link](https://www.pivotaltracker.com/n/projects/2400279)

Briefly, a user wants to:
- Sign up / Log in (account management)
- Create / View / Delete post (post)
- Create / View / Delete comment (comment)
- Create / View / Update profile (profile)

### Function List

###### Home page

- login text area for login function / signup link directing to signup form
- display all posts from server. Users can view and comment on the posts.

###### Post page

- login text area for login function / signup link directing to signup form
- ** if user is not logged in, the page will show error message and hide other contents
- two buttons serve as a switcher to view own posts, or create a new post
- view all posts mode: list all posts sorted by post id, each post has an click listener connected to single view mode
- view one post mode: show one post, comments if any, a comment input area, submit button, and a delete post button
- create a post mode: enter title, post content, and submit

###### Profile page

- login text area for login function / signup link directing to signup form
- ** if user is not logged in, the page will show error message and hide other contents
- If profile is empty and not accessible from the server, the form will be editable
- After entering info, create profile with API call
- If profile is created, display in the page, email and address boxes are grey and read-only, mobile is white and editable for updating profile
- After entering info, update profile with API call

### Wireframe

Therefore, we defined the UI as

##### home page

![alt text][home_page]

##### post page

- post page - view all posts owned by user

![alt text][post_view_all]

- post page - view a single post

![alt text][post_view]

- post page - create a post

![alt text][post_create]

##### profile page

![alt text][profile_page]


# Tech Stack, Methodologies and Principles

### Tech Stack

- HTML for markdown

- CSS for styling and layout

- Javascript for Listners for event handling, DOM manipulation, Communication with backend API, Ajax fetch

### Methodology

Agile Development and Extreme Programming

- fast iteration, early deliverables, pair programming, continuous integration

### Principles

- KISS (keep it simple, stupid)

- DRY (don't repeat yourself) (modularity)

### Application Structure
![alt text][app_structure]

### Hosted app

[OPEN THE APP](https://qimingchen.github.io/S1-Project-1.io/src/html/index.html)
[to enable loading javascript in chrome and avoid mix content warning, please temporarily enable an unsafe script on your browser]

# Timeline:

### requirement analysis and design

- break down features and write user stories (2019/09/24 Tue)

### code

- define application structure (2019/09/24 Tue)

- define HTML markdown (2019/09/24 Tue ~ 2019/09/25 Wed)

- implement consumer for api request and response (2019/09/24 Tue ~ 2019/09/27 Fri)

- implement javascript for each page (2019/09/24 Tue ~ 2019/09/27 Fri)

- css styling (2019/09/26 Thr)

- code cleaning up & documentation (2019/09/28 Sat ~ 2019/09/29 Sun)

### test

- test features (2019/09/29 Sun)

### deploy

- deploy on github host

- presentation (2019/09/30 Mon)

# TODO

- test code

# Test Cases

According to user stories, we can demo in this order:

- Sign up / Log in (account management)

- Create / View / Delete post (post)

- Create / View / Delete comment (comment)

- Create / View / Update profile (profile)



# Check List (Removed at the end of project)

~~Have placeholders for user login and sign-up (i.e., create basic HTML forms).~~

~~Have a user directed to a home page that contains all posts once they sign in.~~

~~Allow a user to create and delete their own posts, as well as view and comment on others’ posts.~~

~~Add front-end Ajax calls to consume the given APIs.~~

~~Use JavaScript for DOM manipulation.~~

~~Include separate HTML, CSS, and JavaScript files.~~

Stick with the KISS (keep it simple, stupid) and DRY (don't repeat yourself) principles.

User stories, documented in Pivotal Tracker, to show the work breakdown and project deliverables.

The front-end application you built.

A Git repository hosted on GitHub with a link to your hosted app.

~~Around 50 (or more) commits on GitHub, dating back to the beginning of the project. (Commit early, commit often. Tell a story with your commits. Each message should give a clear idea of what you changed.)~~

A README.md file with:

... Explanations of the technologies used.

... A couple of paragraphs about the general approach you took.

... ~~Descriptions of any unsolved problems or major hurdles you had to overcome.~~

... ~~A link to your planning documentation for how you broke down this project with deliverables and timelines.~~

Installation instructions for any dependencies.

~~A link to your user stories — who your users are, what they want, and why.~~

~~A link to your wireframes — sketches of major views or interfaces in your application.~~


[home_page]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/home_page.png "home page 1"
[post_view]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/post_view_a_post.png "post page - view a post"
[post_view_all]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/post_view_posts.png "post page - view all posts"
[post_create]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/post_create_a_post.png "post page - create a post"
[post_view]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/post_view_a_post.png "post page - view a post"
[profile_page]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/profile_page.png "profile page"
[app_structure]: https://github.com/BenjaminKarasik28/S1-Project-1/blob/qc/image/app_structure.png "app structure"
