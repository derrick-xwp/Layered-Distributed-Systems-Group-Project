/**
 * Author:  Benjamin Kelly
 * Created: 11-05-2020
 */

/*
!!!FOR THE APPLICATION TO WORK PLEASE EXECUTE THIS .SQL FILE ON THE CONNECTION: jdbc:derby://localhost:1527/sample [app on APP]
 */

/*Create the database tables required for the application*/
CREATE TABLE jobs(provider varchar(50),jobid varchar(50), title  varchar(50), keywords varchar(50),description varchar(500),payment int,status varchar(50),offers  varchar(50));
CREATE TABLE UserInfo(username varchar(50),freelancerid int, skills varchar(50), message varchar(500),account int);
CREATE TABLE users(username varchar(50),password varchar(50), role int,firstname  varchar(50),lastname  varchar(50),address varchar(50));
CREATE TABLE LOGINFO(LOGINFO varchar(50));


INSERT INTO jobs VALUES('eoin','1','software engineer','programmer','In this role, as a software engineer, you will contribute to the design, development, documenting, testing, and enhancement of highly available, distributed, scalable and secure compute systems and platforms with an emphasis on computer vision',35,'open','0');
INSERT INTO jobs VALUES('eoin','2','Training and Communication Specialist','coach','As Training and Communication Specialist you will Manage, design and facilitate business process training to all teams across the organisation as part of wider organisational change, including system and workflow components.',25,'open','0');
INSERT INTO jobs VALUES('eoin','3','driver','bus','CPC License is essential,Adhere to all quality controls and procedures when carrying out tasks,Ensure all Items are picked, inspected and shipped according to customer requirements ',25,'open','0');
INSERT INTO jobs VALUES('eoin','4','cook','HSE cook','There is currently one permanent and whole-time vacancy available in Midlands Regional Hospital Portlaoise.',25,'open','0');
INSERT INTO jobs VALUES('eoin','5','teacher','English teacher','Depending on qualifications, we offer an open end or a 1 year contract in accordance with the Service regulations for the locally recruited teachers in the European Schools',15,'open','0');

INSERT INTO userInfo VALUES('trump',1,'programming','hey,i am very good at programming, i have three years working experience as a bug writer in FBI',12);
INSERT INTO userInfo VALUES('smith',2,'swimming','I am good at swimming, i can swimm in shannon river without rest for 24 hours',22);

INSERT INTO users VALUES('admin','p1',1,'john','smith','limerick');
INSERT INTO users VALUES('eoin','p3',3,'eoin','batch','dublin');
INSERT INTO users VALUES('trump','p2',2,'trump','donald','cork');
INSERT INTO users VALUES('smith','p2',2,'smith','chees','galway');