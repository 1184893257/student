package data;

import java.sql.Date;

public class Student {
	/**
	 * 学号
	 */
	public String no;
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 年龄
	 */
	public int age;
	/**
	 * 性别:"男"\"女"
	 */
	public String sex;
	/**
	 * 出生日期
	 */
	public java.sql.Date birthday;
	/**
	 * 地址
	 */
	public String address;
	/**
	 * 电话
	 */
	public String tel;
	/**
	 * E-mail
	 */
	public String email;

	public Student(String no, String name, int age, String sex, Date birthday,
			String address, String tel, String email) {
		this.no = no;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.tel = tel;
		this.email = email;
	}
}
