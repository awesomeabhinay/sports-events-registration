# Sports Event Registration System
The Sports Event Registration System is a web application that allows users to register for sports events happening on a particular day. It includes both the frontend user interface and a backend service for managing users and event registrations.

# Features
User registration and login.
Viewing a list of all available events.
Registering for events.
Unregistering from events.
Viewing a list of events for which the user has registered.
Event cards display event name, category, timings, and registration buttons.
Error handling and notifications.

## Architecture
The system follows a client-server architecture, with the frontend built using ReactJS and the backend implemented with Java and Spring Boot. Below is a high-level diagram of the system architecture:

System Architecture

## Backend API Endpoints
The backend provides the following API endpoints:

### Create User
POST /user/create

### Login User
POST /user/login

### Get All Events List
GET /events/all

### Get All Registered Events List
GET /events/{username}

### Register Event
POST /event/register

### Unregister Event
DELETE /event/unregister

## Installation and Setup
Clone this repository:

### UI Setup
`git clone https://github.com/awesomeabhinay/sports-events-registration-ui.git`

Navigate to the project directory:

cd sports-event-registration

Install frontend dependencies: `npm install`

Start the React development server: `npm start`

### Backend Setup:
`git clone https://github.com/awesomeabhinay/sports-events-registration-ui.git`

Install Java and Spring Boot dependencies.

Run the Spring Boot application.

Configure database connection in the backend application properties.

Access the web application in your browser:

Frontend: http://localhost:3000
Backend: http://localhost:8080
