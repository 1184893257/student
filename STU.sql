/*����ѧ����Ϣ�������ݿ�
*/
CREATE DATABASE STU
GO

USE STU
GO

/*����ѧ����͹���Ա��
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

INSERT INTO Student VALUES('03091799','����','��','1991-02-01','����֣��','18700000003','liuchen@126.com')
INSERT INTO Student VALUES('03091800','����','��','1991-06-03','���ϳ�ɳ','18700000000','liyong@163.com')
INSERT INTO Student VALUES('03091801','����','Ů','1991-08-09','��������','18700000001','liuchen@gmail.com')
INSERT INTO Student VALUES('03091802','��Ʒ��','��','1991-02-28','��������','18700000004','hepinjun@sina.com')
