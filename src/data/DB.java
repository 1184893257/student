package data;

import java.sql.*;
import java.util.*;

public class DB {
	protected Connection conn;
	protected Statement stat;

	/**
	 * 连接数据库,如果失败返回false
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
	 * 在DB对象销毁之前关闭数据库连接
	 */
	@Override
	protected void finalize() throws Throwable {
		stat.close();
		conn.close();
		super.finalize();
	}

	/**
	 * 登录
	 * 
	 * @param name
	 *            登录名
	 * @param password
	 *            登录密码
	 * @return 登录成功返回true否则返回false
	 */
	public boolean login(String name, String password) {
		String select = "SELECT * FROM ADMIN WHERE NAME=?";
		PreparedStatement ps = null;
		boolean correct = false;
		try {
			ps = conn.prepareStatement(select);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next() && rs.getString("PASSWORD").trim().equals(password))
				correct = true;
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return correct;
	}

	/**
	 * 获得所有管理员的名字
	 * 
	 * @return
	 */
	public Vector<String> getAllAdmin() {
		Vector<String> ans = new Vector<String>();
		String select = "SELECT * FROM ADMIN";
		try {
			ResultSet rs = stat.executeQuery(select);
			while (rs.next())
				ans.add(rs.getString("NAME").trim());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * 添加管理员
	 * 
	 * @param name
	 *            登录名
	 * @param password
	 *            密码
	 * @return 添加成功返回true,否则返回false
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
	 * 删除管理员
	 * 
	 * @param name
	 *            登录名
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
	 * 添加学生
	 * 
	 * @param stu
	 *            封装了学生信息的对象
	 * @return 添加成功返回true,否则返回false
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
	 * 删除学生
	 * 
	 * @param no
	 *            要删除的学生的学号
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
	 * 修改学生信息
	 * 
	 * @param originNo
	 *            原来的学生的学号
	 * @param now
	 *            修改后学生的信息
	 * @return 修改成功返回true,否则返回false
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
	 * 获得所有学生的信息
	 * 
	 * @return 包含所有学生信息的链表
	 */
	public LinkedList<Student> getAllStudent() {
		LinkedList<Student> ans = new LinkedList<Student>();
		String select = "SELECT * FROM STUDENT";
		try {
			ResultSet rs = stat.executeQuery(select);

			Student t;

			// 获取当前年份
			Calendar now = Calendar.getInstance();
			now.setTime(new java.util.Date());
			final int year = now.get(Calendar.YEAR);

			// birthday是出生日期,year减去它的年份得到年龄
			Calendar birthday = (Calendar) now.clone();
			while (rs.next()) {
				birthday.setTimeInMillis(rs.getDate("BIRTHDAY").getTime());
				t = new Student(rs.getString("NO").trim(), rs.getString("NAME")
						.trim(), year - birthday.get(Calendar.YEAR), rs
						.getString("Sex").trim(), rs.getDate("BIRTHDAY"), rs
						.getString("ADDRESS").trim(), rs.getString("TEL")
						.trim(), rs.getString("EMAIL").trim());
				ans.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}
}
