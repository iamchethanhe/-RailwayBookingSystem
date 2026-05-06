Railway Ticket Booking System

A modern desktop-based Railway Ticket Booking System developed using Java Swing, JDBC, and MySQL. The application simulates a real-world railway reservation platform where users can search trains, book tickets, and manage reservations, while administrators can manage train records and monitor bookings.

The project focuses on combining:

* Clean UI design
* Database integration
* Real-time booking workflow
* Admin management features
* Structured Java Swing architecture

⸻

Project Overview

The Railway Ticket Booking System is designed to digitize railway reservation operations. Traditionally, railway bookings involve manual processes and long waiting times. This project automates the complete workflow through an interactive desktop application.

The system allows users to:

* Create accounts
* Search available trains
* View train schedules
* Book railway tickets
* View booked tickets

The admin module allows administrators to:

* Add new trains
* Delete trains
* View all passenger bookings
* Manage railway data efficiently

The application uses Java Swing for the graphical interface and MySQL for persistent database storage.

⸻

Key Features

User Module

User Authentication

* Secure user registration and login system
* Validation for empty fields and password matching
* Session handling using logged-in username

Train Search System

* Search trains based on source and destination
* Dynamically fetch train data from MySQL database
* Multiple train timing display

Ticket Booking

* Book tickets in different categories:
    * AC
    * Non-AC
    * Sleeper
* Dynamic price calculation based on:
    * Category
    * Number of tickets
* Booking confirmation system

My Tickets

* View all booked tickets
* Ticket card-based UI layout
* Displays:
    * Train name
    * Route
    * Ticket category
    * Journey date
    * Ticket count
    * Total price

Logout System

* Secure logout navigation
* Redirects user back to login screen

⸻

Admin Module

Add Train

Admin can:

* Add train name
* Source station
* Destination station
* Ticket base price
* Train timing

Delete Train

* Remove trains from database
* Dynamic train list loading
* Train management support

View All Bookings

* Admin can monitor all passenger reservations
* Displays complete booking details
* Useful for railway management and analytics

⸻

User Interface Design

The project uses a modern desktop UI inspired by real-world booking applications.

UI Highlights

* Gradient backgrounds
* Card-based layouts
* Responsive Swing components
* Structured forms using GridBagLayout
* Modern button styling
* Clean blue theme consistency

The UI was carefully designed to improve:

* Readability
* User experience
* Navigation simplicity
* Professional appearance

⸻

Technologies Used

Technology	Purpose
Java	Core Programming Language
Java Swing	GUI Development
JDBC	Database Connectivity
MySQL	Database Management
AWT	Event Handling & Graphics
GridBagLayout / BoxLayout	Advanced Layout Management

⸻

Project Structure

src/
 └── gui/
      ├── LoginPage.java
      ├── RegisterPage.java
      ├── Dashboard.java
      ├── SearchTrainPage.java
      ├── ViewTrainPage.java
      ├── BookingPage.java
      ├── ViewTicketPage.java
      ├── AdminDashboard.java
      ├── AddTrainPage.java
      ├── DeleteTrainPage.java
      ├── AdminViewBookings.java
      └── DBConnection.java

⸻

Database Design

The application uses MySQL as the backend database.

Step 1: Create Database

CREATE DATABASE railway;

⸻

Step 2: Select Database

USE railway;

⸻

Step 3: Create Users Table

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    password VARCHAR(100)
);

⸻

Step 4: Create Trains Table

CREATE TABLE trains(
    id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100),
    source VARCHAR(100),
    destination VARCHAR(100),
    price INT,
    train_time VARCHAR(50)
);

⸻

Step 5: Create Bookings Table

CREATE TABLE bookings(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    train_name VARCHAR(100),
    source VARCHAR(100),
    destination VARCHAR(100),
    category VARCHAR(50),
    timing VARCHAR(50),
    tickets INT,
    total_price INT,
    journey_date VARCHAR(50)
);

⸻

Database Connection Setup

Update your MySQL username and password inside:

DBConnection.java

Example:

return DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/railway",
    "root",
    "your_password"
);

⸻

How to Run the Project

Step 1: Clone Repository

git clone https://github.com/your-username/RailwayBookingSystem.git

⸻

Step 2: Open in IDE

Recommended IDEs:

* IntelliJ IDEA
* Eclipse
* NetBeans
* VS Code

⸻

Step 3: Add MySQL JDBC Driver

Download MySQL Connector/J:

https://dev.mysql.com/downloads/connector/j/

Add the JDBC .jar file to project libraries.

⸻

Step 4: Configure Database

* Start MySQL server
* Create database and tables
* Update DB credentials in DBConnection.java

⸻

Step 5: Run Application

Run:

LoginPage.java

⸻

Admin Credentials

Username: admin
Password: admin123

⸻

Application Screens

The project includes:

* Login Page
* Registration Page
* User Dashboard
* Search Train Page
* Train Listing Page
* Ticket Booking Page
* My Tickets Page
* Admin Dashboard
* Add Train Page
* Delete Train Page
* Booking Management Page

⸻

Concepts Implemented

This project demonstrates:

* Object-Oriented Programming (OOP)
* Event-Driven Programming
* Java Swing GUI Design
* Database CRUD Operations
* JDBC Connectivity
* Layout Management
* Multi-page Navigation
* Real-time Data Handling

⸻

Future Enhancements

The project can be extended with:

* Online Payment Gateway
* QR Code Ticket Verification
* Seat Availability Tracking
* PDF Ticket Generation
* Email Notifications
* Ticket Cancellation
* Seat Selection System
* Live Train Status
* Railway Station Auto Suggestions
* User Profile Management

⸻

Educational Value

This project is highly useful for:

* Java Mini Projects
* DBMS Projects
* Academic Demonstrations
* Swing GUI Practice
* JDBC Learning
* Software Engineering Projects

It provides practical exposure to building real-world desktop applications with database integration.

⸻

Author

Chethan Arya

Passionate about:

* Java Development
* Machine Learning
* Cyber Security
* Software Engineering

⸻

License

This project is developed for educational and academic purposes.

Feel free to use and modify it for learning and project development.