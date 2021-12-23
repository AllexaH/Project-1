# Project 1: Reimbursements

## Project Description

In this application, a manager or employee can log by entering ther username and password. Depending on their role, they will be sent to the Manager homepage or the Employee homepage. On the Employee homepage, the user is able to submit an application for a reimbursement. They will be able to see a list of all of the reimbursements they have made as well as all of the information and whether or not a manager has approved or denied their request. On the Manager homepage, it displays a list of all the reimbursement request made by the employees. There are also buttons in each of these requests that allows the manager to approve or deny the request. In both of these homepages there is a logout button at the top of the screen.

## Technologies Used

* PostgreSQL - version 42.3.1
* Logback - version 1.2.6
* Javalin - version 4.1.1
* Jackson-Databind - version 2.13.0
* JUnit - version 5.8.1
* Mockito - version 4.0.0
* Tika - version 1.18
* Selenium - version 4.0.0
* Cucumber - version 7.0.0

## Features

* Login
* Logout
* Add new reimbursement
* Display all reimbursements of the logged in employee
* Display all reimbursement if logged in user is a manager
* Manager can approve or Deny reimbursements

To-do list:
* Manager will be able to filter through the reimbursement based on the status
* There will be a sign up feature for new employees and managers

## Getting Started
   
Open Git Bash
> ` git clone git@github.com:AllexaH/Project-1.git `


## Usage

* Open program such as Eclipse or SpringToolSuite4
* Import project based on the location the cloned project is in.
* Locate "Application.java" in src/main/java/com.revature.app
* Run project
* Open Browser
* Use the following link "http://localhost:8080"
