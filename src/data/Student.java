package data;

import java.sql.Date;

public class Student {
	/**
	 * ѧ��
	 */
	public String no;
	/**
	 * ����
	 */
	public String name;
	/**
	 * ����
	 */
	public int age;
	/**
	 * �Ա�:"��"\"Ů"
	 */
	public String sex;
	/**
	 * ��������
	 */
	public java.sql.Date birthday;
	/**
	 * ��ַ
	 */
	public String address;
	/**
	 * �绰
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
