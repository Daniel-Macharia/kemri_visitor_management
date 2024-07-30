

--below is the sql code for the project database
--DROP DATABASE IF EXISTS KEMRI_VISITORS_PROJECT;

CREATE DATABASE KEMRI_VISITORS_PROJECT;

USE KEMRI_VISITORS_PROJECT;


DROP TABLE IF EXISTS SECURITY;

CREATE TABLE SECURITY(
SECURITY_NUMBER VARCHAR(20) NOT NULL,
SECURITY_NAME VARCHAR(100) NOT NULL,
PHONE_NUMBER VARCHAR(10) NOT NULL,
SECURITY_PASSWORD VARCHAR(20) NOT NULL,
PRIMARY KEY(SECURITY_NUMBER)
);


DROP TABLE IF EXISTS VISITOR;

CREATE TABLE VISITOR(
ID_NUMBER VARCHAR(8) NOT NULL,
VISITOR_NAME VARCHAR(100) NOT NULL,
PHONE_NUMBER VARCHAR(10),
PRIMARY KEY(ID_NUMBER)
);


DROP TABLE IF EXISTS DESTINATION;

CREATE TABLE DESTINATION(
DESTINATION_ID VARCHAR(10) NOT NULL,
DESTINATION_NAME VARCHAR(100) NOT NULL,
PRIMARY KEY(DESTINATION_ID)
);

INSERT INTO DESTINATION(DESTINATION_ID, DESTINATION_NAME) VALUES("ICT", "ICT Help Desk and User Support"),
("HRM", "Human Resource Management"),
("DAD", "Data Analysis Department"),
("PJT", "Project"),
("LAB", "Bio-medical Laboratory"),
("SCI", "Science Department"),
("RCH", "Research");


DROP TABLE IF EXISTS VISIT;

CREATE TABLE VISIT(
VISIT_DATE DATE NOT NULL,
VISIT_NUMBER INT NOT NULL,
ID_NUMBER VARCHAR(8) NOT NULL,
SECURITY_NUMBER VARCHAR(20) NOT NULL,
VISITOR_ORIGIN VARCHAR(100) NOT NULL,
VISITOR_DESTINATION_ID VARCHAR(10) NOT NULL,
VISIT_REASON VARCHAR(200) NOT NULL,
VISITOR_TIME_IN VARCHAR(20) NOT NULL,
VISITOR_TIME_OUT VARCHAR(20) NULL,
VEHICLE_NUMBER_PLATE VARCHAR(10),
PRIMARY KEY(VISIT_DATE, VISIT_NUMBER),
FOREIGN KEY (ID_NUMBER) REFERENCES VISITOR(ID_NUMBER),
FOREIGN KEY (VISITOR_DESTINATION_ID) REFERENCES DESTINATION(DESTINATION_ID),
FOREIGN KEY (SECURITY_NUMBER) REFERENCES SECURITY(SECURITY_NUMBER)
);


DROP TABLE IF EXISTS DATE_NUMBER;

CREATE TABLE DATE_NUMBER(
ID INT AUTO_INCREMENT,
DATE_TODAY VARCHAR(12) NOT NULL,
CURRENT_COUNT INT NOT NULL,
IS_STAFF_COUNT VARCHAR(5) NOT NULL,
PRIMARY KEY( ID )
);

INSERT INTO DATE_NUMBER (DATE_TODAY, CURRENT_COUNT, IS_STAFF_COUNT) VALUES ("2024 05 12", 0, "YES"), ("2024 06 02", 0, "NO");


/*ADDITIONAL SQL TABLES*/
DROP TABLE IF EXISTS STATION;
 CREATE TABLE STATION(
STATION_ID VARCHAR(10),
STATION_NAME VARCHAR(100),
PRIMARY KEY (STATION_ID)
);

DROP TABLE IF EXISTS STAFF;

CREATE TABLE STAFF(
STAFF_ID VARCHAR(10) NOT NULL,
STAFF_NAME VARCHAR(100) NOT NULL,
STAFF_PHONE VARCHAR(20) NOT NULL,
STATION_ID VARCHAR(10) NOT NULL,
PRIMARY KEY (STAFF_ID),
FOREIGN KEY CONSTRAINTS(STATION_ID)
REFERENCES STATION(STATION_ID)
);

DROP TABLE IF EXISTS STAFF_VISIT;

CREATE TABLE STAFF_VISIT(
VISIT_DATE varchar(12) NOT NULL,
VISIT_NUMBER INT NOT NULL,
SECURITY_NUMBER VARCHAR(20) NOT NULL,
STAFF_ID VARCHAR(10) NOT NULL,
CHECK_IN_STATION VARCHAR(10) NOT NULL,
STAFF_CHECK_IN_TIME TIMESTAMP,
STAFF_CHECK_OUT_TIME VARCHAR(10),
PRIMARY KEY(VISIT_DATE, VISIT_NUMBER),
FOREIGN KEY (STAFF_ID)
REFERENCES STAFF(STAFF_ID),
FOREIGN KEY (SECURITY_NUMBER)
REFERENCES SECURITY(SECURITY_NUMBER)
);

--end of sql code
