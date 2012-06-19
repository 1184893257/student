package data;

import java.sql.*;
import java.util.*;

public class DB {
	protected Connection conn;
	protected Statement stat;

	/**
	 * �������ݿ�,���ʧ�ܷ���false
	 * 
	 * @return
	 */
	public boolean init() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1433;databaseName=STU", "lqy",
					"");
			stat = conn.createStatement();
			return true;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * ��DB��������֮ǰ�ر����ݿ�����
	 */
	@Override
	protected void finalize() throws Throwable {
		stat.close();
		conn.close();
		super.finalize();
	}

	/**
	 * ��¼
	 * 
	 * @param name
	 *            ��¼��
	 * @param password
	 *            ��¼����
	 * @return ��¼�ɹ�����true���򷵻�false
	 */
	public boolean login(String name, String password) {
		String select = "SELECT * FROM ADMIN WHERE NAME=?";
		PreparedStatement ps = null;
		boolean correct = false;
		try {
			ps = conn.prepareStatement(select);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next() && rs.getString("PASSWORD").equals(password))
				correct = true;
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return correct;
	}

	/**
	 * ��ӹ���Ա
	 * 
	 * @param name
	 *            ��¼��
	 * @param password
	 *            ����
	 * @return ��ӳɹ�����true,���򷵻�false
	 */
	public boolean addAdmin(String name, String password) {
		String insert = "INSERT INTO ADMIN VALUES(?,?)";
		int n = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, name);
			ps.setString(2, password);
			n = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n == 1;
	}

	/**
	 * ɾ������Ա
	 * 
	 * @param name
	 *            ��¼��
	 */
	public void removeAdmin(String name) {
		String delete = "DELETE FROM ADMIN WHERE NAME=?";
		try {
			PreparedStatement ps = conn.prepareStatement(delete);
			ps.setString(1, name);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ѧ��
	 * 
	 * @param stu
	 *            ��װ��ѧ����Ϣ�Ķ���
	 * @return ��ӳɹ�����true,���򷵻�false
	 */
	public boolean addStudent(Student stu) {
		String insert = "INSERT INTO STUDENT VALUES(?,?,?,?,?,?,?)";
		int n = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, stu.no);
			ps.setString(2, stu.name);
			ps.setString(3, stu.sex);
			ps.setDate(4, stu.birthday);
			ps.setString(5, stu.address);
			ps.setString(6, stu.tel);
			ps.setString(7, stu.email);
			n = ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n == 1;
	}

	/**
	 * ɾ��ѧ��
	 * 
	 * @param no
	 *            Ҫɾ����ѧ����ѧ��
	 */
	public void delStudent(String no) {
		String delete = "DELETE FROM STUDENT WHERE NO=?";
		try {
			PreparedStatement ps = conn.prepareStatement(delete);
			ps.setString(1, no);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �޸�ѧ����Ϣ
	 * 
	 * @param originNo
	 *            ԭ����ѧ����ѧ��
	 * @param now
	 *            �޸ĺ�ѧ������Ϣ
	 * @return �޸ĳɹ�����true,���򷵻�false
	 */
	public boolean modifyStudent(String originNo, Student now) {
		String select = "SELECT * FROM STUDENT WHERE NO=?";
		try {
			PreparedStatement ps = conn
					.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, originNo);
			ResultSet rs = ps.executeQuery();

			rs.first();
			rs.updateString("NO", now.no);
			rs.updateString("NAME", now.name);
			rs.updateString("SEX", now.sex);
			rs.updateDate("BIRTHDAY", now.birthday);
			rs.updateString("ADDRESS", now.address);
			rs.updateString("TEL", now.tel);
			rs.updateString("EMAIL", now.email);
			rs.updateRow();

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * �������ѧ������Ϣ
	 * 
	 * @return ��������ѧ����Ϣ������
	 */
	public LinkedList<Student> getAllStudent() {
		LinkedList<Student> ans = new LinkedList<Student>();
		String select = "SELECT * FROM STUDENT";
		try {
			ResultSet rs = stat.executeQuery(select);

			Student t;

			// ��ȡ��ǰ���
			Calendar now = Calendar.getInstance();
			now.setTime(new java.util.Date());
			final int year = now.get(Calendar.YEAR);

			// birthday�ǳ�������,year��ȥ������ݵõ�����
			Calendar birthday = (Calendar) now.clone();
			while (rs.next()) {
				birthday.setTimeInMillis(rs.getDate("BIRTHDAY").getTime());
				t = new Student(rs.getString("NO"), rs.getString("NAME"),
						birthday.get(Calendar.YEAR) - year,
						rs.getString("Sex"), rs.getDate("BIRTHDAY"),
						rs.getString("ADDRESS"), rs.getString("TEL"),
						rs.getString("EMAIL"));
				ans.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}
}
