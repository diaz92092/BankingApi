DROP DATABASE IF EXISTS p0;
CREATE DATABASE p0;

USE p0;

DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients(
                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                        firstName VARCHAR(255) NOT NULL,
                        lastName VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        phoneNumber VARCHAR(255),
                        address VARCHAR(255),
                        city VARCHAR(255),
                        state VARCHAR(255),
                        zip VARCHAR(255)
);

INSERT INTO clients
VALUES
(1, "Mike", "West", "mike.swest@gmail.com", "310-356-3201", "5841 Round Meadow Rd .", "Los Angeles", "CA", "90300"),
(2, "John", "Kent", "john.kent@gmail.com", "310-488-2033", "4682 Valley Lane.", "Los Angeles", "CA", "90264"),
(3, "Dan", "Wilson", "dan.wilson@gmail.com", "626-121-0093", "233 Sierra St.", "Del Mar", "CA", "90264"),
(4, "Eve", "Smith", "eve.smith@gmail.com", "310-738-239", "31 North Bedford Cour", "La Jolla", "CA", "90425");

CREATE TABLE accounts(
                         id INTEGER PRIMARY KEY AUTO_INCREMENT,
                         account_type VARCHAR(255) NOT NULL,
                         balance DECIMAL NOT NULL,
                         client_id INTEGER NOT NULL,
                         CONSTRAINT `fk_accounts_clients` FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);


INSERT INTO accounts
VALUES
(1, "Checking", 5000.00, 1),
(2, "Savings", 5556.00, 1),
(3, "Savings", 556.00, 2),
(4, "Savings", 1556.00, 4),
(5, "Savings", 5256.00, 4)
;





SELECT * FROM clients;
SELECT * FROM accounts;


SELECT *
FROM clients
WHERE id = 1;


SELECT * FROM accounts
WHERE client_id = 10;


SELECT * FROM accounts
WHERE client_id = 2
HAVING (balance < 2000 AND balance > 400);



SELECT * FROM accounts
WHERE id=1 AND client_id = 1;


UPDATE accounts
SET account_type="Savings", balance=10000
WHERE id=5 AND client_id =4 ;

