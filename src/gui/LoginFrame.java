package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import data.DB;

/**
 * 此类是登录窗体的实现
 * 
 * @author lqy
 * 
 */
public class LoginFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 封装数据库操作的对象
	 */
	protected DB database;
	/**
	 * 登录名输入框
	 */
	protected JTextField nameField;
	/**
	 * 密码输入框
	 */
	protected JPasswordField passwordField;

	public LoginFrame(DB database) {
		super("请登录");
		this.database = database;

		// 设置布局管理器
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		nameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		// 登录名行:JLabel+JTextField
		JLabel label = new JLabel("登录名:");
		layout.setConstraints(label, c);
		add(label);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = GridBagConstraints.RELATIVE;
		layout.setConstraints(nameField, c);
		add(nameField);

		// 登录密码行:JLabel+JPasswordField
		label = new JLabel("密码:");
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridwidth = 1;
		layout.setConstraints(label, c);
		add(label);
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(passwordField, c);
		add(passwordField);

		// 登录按钮
		JButton button = new JButton("登录");
		button.addActionListener(this);
		this.getRootPane().setDefaultButton(button);
		layout.setConstraints(button, c);
		add(button);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setCenter();
	}

	/**
	 * 将本窗体放置在屏幕中央
	 */
	protected void setCenter() {
		Dimension screen = this.getToolkit().getScreenSize();
		Dimension login = this.getSize();
		this.setLocation((screen.width - login.width) / 2,
				(screen.height - login.height) / 2);
	}

	public static void main(String[] args) {
		DB database = new DB();
		if (database.init()) {// 如果成功连接上数据库则打开登录窗体
			LoginFrame frame = new LoginFrame(database);
			frame.setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "数据库连接失败", "程序出错了",
					JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = this.nameField.getText();
		String password = String.copyValueOf(this.passwordField.getPassword());
		if (this.database.login(name, password)) {// 登录成功
			MainFrame frame = new MainFrame(database, name);
			this.dispose();
			frame.setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "用户名或密码错误", "登录失败",
					JOptionPane.WARNING_MESSAGE);
	}

}
