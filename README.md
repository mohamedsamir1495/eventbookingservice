# Event Booking

[[_TOC_]]

---

:scroll: **START**

## Introduction

In today's fast-paced world, the convenience of booking systems has become an essential aspect of daily life. From booking tickets for a concert or reserving a spot at a conference, these systems are used widely by individuals and businesses alike.

---

## Task Description

The system will allow users to create, find and reserve tickets for events, view and manage their reservations and to be notified before the event kickoff.

A **user** has:
- name (limited to 100 characters);
- email (valid email format);
- password (minimum 8 characters).

An **event** has:
- name (limited to 100 characters);
- date (in a valid date format);
- available attendees count (positive integer limited to 1000);
- event description (limited to 500 characters).
- category (Concert, Conference, Game)

Develop a set of REST service APIs based on the swagger file provided - [swagger file](event-booking-swagger.yml), that allows users to:

- Create an account;
- User authentication to log into the system;
- Create events;
- Search and reserve tickets for events;
- Send notification to users before event starts.

> Feel free to make assumptions for the design approach. 

## Requirements

While implementing your solution **please take care of the following requirements:**

### Functional requirements

- The REST API methods should be implemented based on the specification provided in the linked swagger file;
- Add 2 new methods, one to **view** your booked events and one to **cancel** your reservation _**(both should be authorized)**_;
- Introduce a periodic task to send notifications for upcoming events to users and create history/audit event log for this.
- No need for UI;

### Non-functional requirements

- The project MUST be buildable and runnable;
- The project MUST have Unit tests;
- The project MUST have a README file with build/run/test instructions (use a DB that can be run locally, e.g. in-memory, via container);
- Any data required by the application to run (e.g. reference tables, dummy data) MUST be preloaded in the database;
- Input/output data MUST be in JSON format;
- Use a framework of your choice, but popular, up-to-date, and long-term support versions are recommended.

---

:scroll: **END**

## Solution Guide
- The project is built using java 21, Spring boot version 3.2.4 and Gradle, so you need to have those to be able to run or debug
- You can run this service using two database options
  * H2 : Use the h2 profile and you can test and play with the application , and check h2 console on url : `localhost:8080/h2`
  * mysql : You will need to have a running container instance of mysql using this command `docker run -d -p 3306:3306 --name bookingdb -e MYSQL_DATABASE=bookingdb -e MYSQL_ROOT_PASSWORD=1234 mysql:latest`

- You can test this service using swagger on url : `localhost:8080/docs`
- You can deploy this service using `gradle jibDockerBuild` command to build a docker image then running docker compose from `./docker-compose` directory using `docker compose up -d` 
- if you have any questions you can reach me out on my email `mohamedsamir1495@gmail.com`

---
