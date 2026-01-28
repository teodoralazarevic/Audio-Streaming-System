# Audio Streaming Information System
---

## Project Description

This project implements an **Audio Streaming Information System** that manages users, audio recordings, categories, subscription packages, and listening history. The system is designed as a distributed architecture consisting of a **client application**, a **central server**, and **two subsystems**, all communicating via JMS. Users can upload audio recordings, subscribe to packages, and listen to audio, while the system records all relevant metadata and enforces subscription rules.

---

## Data Model

The system maintains the following entities:

- **User**: name, email, year of birth, gender, location.  
- **Location**: name.  
- **Audio Recording**: title, duration, owner, upload date and time; can belong to multiple categories.  
- **Category**: name.  
- **Package**: monthly subscription, current price.  
- **Subscription**: user, package, start date and time, paid price (one subscription per user per package at a time).  
- **Listening**: user, audio recording, start date and time, start second, duration listened.

---

## System Architecture

### Client Application
- Java SE program with either console or GUI interface.
- Accepts user requests, converts them to **REST requests**, and sends them to the central server.
- Displays server responses to the user.

### Central Server
- Acts as a bridge between client and subsystems; does **not store data** itself.
- Handles REST requests and forwards them to subsystems via **JMS**.
- Endpoint functionalities include:
  1. Create location
  2. Create user
  3. Update user email
  4. Update user location
  5. Create category
  6. Create audio recording
  7. Assign category to audio
  8. Create package
  9. Update package monthly price
  10. Create subscription
  11. Record listening event
  12. Delete audio (by owner)
  13. Get all locations
  14. Get all users
  15. Get all categories
  16. Get all audio recordings
  17. Get categories for a recording
  18. Get all packages
  19. Get all subscriptions for a user
  20. Get all listenings for a recording

- Requests 1, 2, 3, 4, 13, 14 go to **Subsystem 1**, and the rest go to **Subsystem 2**.

### Subsystem 1
- Stores **users and locations** and their relationships.
- Communicates exclusively through **JMS**.

### Subsystem 2
- Stores **categories, audio recordings, packages, subscriptions, listenings**, and their relationships.
- Communicates exclusively through **JMS**.

---

## Technologies

- **Programming Language:** Java SE  
- **Database:** MySQL  
- **Communication:** REST API, JMS 
