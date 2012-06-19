package gui;

import javax.swing.*;

import data.DB;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected DB database;

	public LoginFrame(DB database) {
		super("登录");

		this.database = database;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	public static void main(String[] args) {
		DB database = new DB();
		if (database.init()) {// 如果成功连接上数据库则打开登录窗体
			LoginFrame frame = new LoginFrame(database);
			frame.setVisible(true);
		}
		JOptionPane.showMessageDialog(null, "数据库连接失败", "程序出错了",
				JOptionPane.ERROR_MESSAGE);
	}

}
