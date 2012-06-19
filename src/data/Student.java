package data;

import java.sql.Date;

public class Student {
	/**
	 * 学号
	 */
	protected String no;
	/**
	 * 姓名
	 */
	protected String name;
	/**
	 * 年龄
	 */
	protected int age;
	/**
	 * 性别:"男"\"女"
	 */
	protected String sex;
	/**
	 * 出生日期
	 */
	protected java.sql.Date birthday;
	/**
	 * 地址
	 */
	protected String address;
	/**
	 * 电话
	 */
	protected String tel;
	/**
	 * E-mail
	 */
	protected String email;

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
