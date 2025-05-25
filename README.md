# Voting Application

A simple Java-based Voting Application that connects to a MySQL database and allows user registration and vote tracking.

## 📁 Project Structure


## ⚙️ Features

- Register a new voter with name, age, and vote status
- Prevent duplicate voter registrations by ID
- Display all registered voters
- Connects to MySQL database using JDBC

## 🛠️ Technologies Used

- Java (JDK 8+)
- JDBC for database connectivity
- MySQL (as the backend database)

## 🧰 Prerequisites

- Java Development Kit (JDK) installed
- MySQL server running
- A database and table created with schema similar to:

```sql
CREATE DATABASE votingdb;

USE votingdb;

CREATE TABLE voters (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    hasVoted BOOLEAN
);
String url = "jdbc:mysql://localhost:3306/votingdb";
String user = "root";
String password = "yourpassword";
javac *.java
java VotingApp
