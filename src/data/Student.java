package data;

import java.sql.Date;

public class Student {
	/**
	 * ѧ��
	 */
	protected String no;
	/**
	 * ����
	 */
	protected String name;
	/**
	 * ����
	 */
	protected int age;
	/**
	 * �Ա�:"��"\"Ů"
	 */
	protected String sex;
	/**
	 * ��������
	 */
	protected java.sql.Date birthday;
	/**
	 * ��ַ
	 */
	protected String address;
	/**
	 * E-mail
	 */
	protected String email;
	
	public Student(String no, String name, int age, String sex, Date birthday,
			String address, String email) {
		this.no = no;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
	}
	
}
