/*建立学生信息管理数据库
*/
CREATE DATABASE STU
GO

USE STU
GO

/*创建学生表和管理员表
*/
CREATE TABLE Student
(
	No		CHAR(10) PRIMARY KEY,
	Name		CHAR(20),
	Sex		CHAR(2),
	Birthday	DATE,
	Address		CHAR(100),
	Tel		CHAR(20),
	Email		CHAR(30)
)

CREATE TABLE Admin
(
	Name		CHAR(20) PRIMARY KEY,
	Password	CHAR(20)
)
GO

INSERT INTO Admin VALUES('admin','admin')

INSERT INTO Student VALUES('03091799','刘晨','男','1991-02-01','河南郑州','18700000003','liuchen@126.com')
INSERT INTO Student VALUES('03091800','李勇','男','1991-06-03','湖南长沙','18700000000','liyong@163.com')
INSERT INTO Student VALUES('03091801','刘晨','女','1991-08-09','陕西西安','18700000001','liuchen@gmail.com')
INSERT INTO Student VALUES('03091802','何品君','男','1991-02-28','江西赣州','18700000004','hepinjun@sina.com')
