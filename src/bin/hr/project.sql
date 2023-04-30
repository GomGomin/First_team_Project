
/* Drop Triggers */

DROP TRIGGER TRI_T_DEPT_deptno;
DROP TRIGGER TRI_T_notice_noticeNo;
DROP TRIGGER TRI_T_POSITION_PositionNo;
DROP TRIGGER TRI_T_WORK_workno;



/* Drop Tables */

DROP TABLE T_SALARY CASCADE CONSTRAINTS;
DROP TABLE T_WORK CASCADE CONSTRAINTS;
DROP TABLE T_EMPLOYEE CASCADE CONSTRAINTS;
DROP TABLE T_DEPT CASCADE CONSTRAINTS;
DROP TABLE T_notice CASCADE CONSTRAINTS;
DROP TABLE T_POSITION CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE SEQ_T_DEPT_deptno;
DROP SEQUENCE SEQ_T_notice_noticeNo;
DROP SEQUENCE SEQ_T_POSITION_PositionNo;
DROP SEQUENCE SEQ_T_WORK_workno;




/* Create Sequences */

CREATE SEQUENCE SEQ_T_DEPT_deptno INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_notice_noticeNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_POSITION_PositionNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_WORK_workno INCREMENT BY 1 START WITH 1;



/* Create Tables */

CREATE TABLE T_DEPT
(
	deptno number NOT NULL,
	department varchar2(50),
	PRIMARY KEY (deptno)
);


CREATE TABLE T_EMPLOYEE
(
	id varchar2(20) NOT NULL,
	deptno number NOT NULL,
	PositionNo number NOT NULL,
	pw varchar2(20) NOT NULL,
	name varchar2(30) NOT NULL,
	email varchar2(50),
	birthday date,
	gender varchar2(1 char),
	joindate date DEFAULT SYSDATE NOT NULL,
	resigndate date,
	resign varchar2(1 char) NOT NULL,
	salary number NOT NULL,
	accountnumber varchar2(100) NOT NULL,
	bankname varchar2(100) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE T_notice
(
	noticeNo number NOT NULL,
	noticetitle varchar2(100),
	content varchar2(5000),
	insertdate date DEFAULT SYSDATE NOT NULL,
	noticewriter varchar2(20),
	PRIMARY KEY (noticeNo)
);


CREATE TABLE T_POSITION
(
	PositionNo number NOT NULL,
	position varchar2(50),
	PRIMARY KEY (PositionNo)
);


CREATE TABLE T_SALARY
(
	id varchar2(20) NOT NULL,
	accountnumber varchar2(20),
	bankname varchar2(100),
	payment number,
	paymentdate date DEFAULT SYSDATE NOT NULL,
	insertid varchar2(20) NOT NULL
);


CREATE TABLE T_WORK
(
	workno number NOT NULL,
	id varchar2(20) NOT NULL,
	deptno number NOT NULL,
	PositionNo number NOT NULL,
	startworkdate date DEFAULT SYSDATE,
	endworkdate date,
	PRIMARY KEY (workno)
);



/* Create Foreign Keys */

ALTER TABLE T_EMPLOYEE
	ADD FOREIGN KEY (deptno)
	REFERENCES T_DEPT (deptno)
;


ALTER TABLE T_WORK
	ADD FOREIGN KEY (deptno)
	REFERENCES T_DEPT (deptno)
;


ALTER TABLE T_SALARY
	ADD FOREIGN KEY (id)
	REFERENCES T_EMPLOYEE (id)
;


ALTER TABLE T_WORK
	ADD FOREIGN KEY (id)
	REFERENCES T_EMPLOYEE (id)
;


ALTER TABLE T_EMPLOYEE
	ADD FOREIGN KEY (PositionNo)
	REFERENCES T_POSITION (PositionNo)
;


ALTER TABLE T_WORK
	ADD FOREIGN KEY (PositionNo)
	REFERENCES T_POSITION (PositionNo)
;



/* Create Triggers */

CREATE OR REPLACE TRIGGER TRI_T_DEPT_deptno BEFORE INSERT ON T_DEPT
FOR EACH ROW
BEGIN
	SELECT SEQ_T_DEPT_deptno.nextval
	INTO :new.deptno
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_T_notice_noticeNo BEFORE INSERT ON T_notice
FOR EACH ROW
BEGIN
	SELECT SEQ_T_notice_noticeNo.nextval
	INTO :new.noticeNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_T_POSITION_PositionNo BEFORE INSERT ON T_POSITION
FOR EACH ROW
BEGIN
	SELECT SEQ_T_POSITION_PositionNo.nextval
	INTO :new.PositionNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_T_WORK_workno BEFORE INSERT ON T_WORK
FOR EACH ROW
BEGIN
	SELECT SEQ_T_WORK_workno.nextval
	INTO :new.workno
	FROM dual;
END;

/




