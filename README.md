Banking Management System

OverView
A simple banking management system implemented in Java, providing basic features for user registration, login, and account management.

Features

- User registration with username, password, and bank name
- User login with authentication
- Deposit, withdraw, and transfer money between accounts
- View all account details
- Logout functionality

Technologies Used

- Java 8
- Swing for GUI
- HashMap for data storage

How to Run

1. Clone the repository to your local machine.
2. Compile the Java code using javac.
3. Run the program using java.
4. Follow the on-screen instructions to register, login, and manage your account.

Classes and Objects
The system consists of two main classes:
1. BankAccount: This class represents a bank account with attributes such as username, password, bank name, and balance. It has a constructor to initialize these attributes and a toString method to return a string representation of the account.
2. BankingManagementSystem: This class contains the main method and all the functionality for the banking system. It has methods for creating the login frame, registration frame, and banking frame.

Methods
Here's a brief description of each method:
1. loadUsersFromFile: Loads user data from a file named "users.txt" into a HashMap called users.
2. saveUsersToFile: Saves the user data from the users HashMap to the "users.txt" file.
3. createLoginFrame: Creates the login frame with fields for username and password, and buttons for login and registration.
4. createRegisterFrame: Creates the registration frame with fields for username, password, and bank name, and a button for registration.
5. createBankingFrame: Creates the banking frame with buttons for deposit, withdrawal, transfer, viewing account details, and logout.

Event Handling
The system uses event handling to respond to user interactions such as button clicks. The event handlers are defined as lambda expressions and are attached to the corresponding buttons.

Data Storage
The system stores user data in a HashMap called users, where each key is a username and the corresponding value is a BankAccount object. The data is persisted to a file named "users.txt" using the saveUsersToFile method.

Security
The system stores passwords in plain text, which is not secure. In a real-world application, passwords should be hashed and stored securely.

Contributors
M.Vimalkumar885
