# Console Bookstore

![](/bookshopDB.jpg)


## Description


   This is a console-based application that facilitates the purchasing of books. 
	An employee can add these items to an inventory and manage offers for those those books while a customer can view the available books and make offers.
	

## Technical Overview
1. Data is stored in a database.
2. Data Access is performed through the use of JDBC in a data layer consisting of Data Access Objects.
3. Input is received using the java.util.Scanner class.
4. Public service layer methods have at least one JUnit test.
5. Log4j is implemented to log events to a file.


## User Stories
* As a user, I can login.
* As an employee, I can add an item to the shop. 
* As a customer, I can view the available items. 
* As a customer, I can make an offer for an item. 
* As an employee, I can accept or reject a pending offer for an item. 
* As the system, I update an item to an owned state when an offer is accepted. 
* As the system, I reject all other pending offers for an item when an offer is accepted. 
* As a user, I can register for a customer account. 
* As an employee, I can remove an item from the shop. 
* As a customer, I can view the items that I own.
* As a customer, I can view my remaining payments for an item.
* As an employee, I can view all payments.
* As a user, I can make a payment.
* As an employee, I can edit existing items. 


## Languages Used

Java
PostgreSQL 13

## Installation

- Clone repo
- Create a PostgreSQL 13 database and copy-paste the schema found in ```schema.txt```
- Setup the following environment variables:
- ```CONNECTION_USERNAME = <Database username>```
- ```CONNECTION_PASSWORD = <Database password>```
- ```CONNECTION_URL = jdbc:postgresql://localhost:5432/BookShopDB```
- Run the Java files in your terminal with the ```javac``` command
