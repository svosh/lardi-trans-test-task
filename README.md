## This is test-task project for lardi-trans company by Ivanov Konstantyn
## Since 2018-09
##
##
##
## DML & DDL scripts for SQL-type storage creating:

CREATE DATABASE phone_book_base
;
USE phone_book_base
;

CREATE TABLE users(
  id BIGINT NOT NULL AUTO_INCREMENT ,
  login VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL,
  name VARCHAR(50),
  PRIMARY KEY (id)
)
;

CREATE TABLE users_phone_book_rows (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  patronymic varchar(50) NOT NULL,
  email varchar(50),
  address varchar(500),
  mobile_phone_number varchar(50) NOT NULL,
  home_phone_number varchar(50),
  PRIMARY KEY (id),
  CONSTRAINT FK_users_phone_book_rows_users FOREIGN KEY (user_id) REFERENCES users (id)
)
;

## start-key for changing properties path:
-Dlardi.conf=/path/to/file.properties

## Properties file fill pattern:

#general
spring.main.banner-mode=off
server.address=localhost
server.port=

#file/sql/something else
dataStorageType = sql

#file-type storage properties
fileDataStoragePath =

#datasource properties. If we use "file-type" data-storage - this section must be empty
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/phone_book_base
spring.datasource.username=
spring.datasource.password=

#jpa properties
spring.jpa.hibernate.ddl-auto=none