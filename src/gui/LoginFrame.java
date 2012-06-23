package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import data.DB;

/**
 * �����ǵ�¼�����ʵ��
 * 
 * @author lqy
 * 
 */
public class LoginFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * ��װ���ݿ�����Ķ���
	 */
	protected DB database;
	/**
	 * ��¼�������
	 */
	protected JTextField nameField;
	/**
	 * ���������
	 */
	protected JPasswordField passwordField;

	public LoginFrame(DB database) {
		super("���¼");
		this.database = database;

		// ���ò��ֹ�����
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		nameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		// ��¼����:JLabel+JTextField
		JLabel label = new JLabel("��¼��:");
		layout.setConstraints(label, c);
		add(label);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = GridBagConstraints.RELATIVE;
		layout.setConstraints(nameField, c);
		add(nameField);

		// ��¼������:JLabel+JPasswordField
		label = new JLabel("����:");
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridwidth = 1;
		layout.setConstraints(label, c);
		add(label);
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(passwordField, c);
		add(passwordField);

		// ��¼��ť
		JButton button = new JButton("��¼");
		button.addActionListener(this);
		this.getRootPane().setDefaultButton(button);
		layout.setConstraints(button, c);
		add(button);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setCenter();
	}

	/**
	 * ���������������Ļ����
	 */
	protected void setCenter() {
		Dimension screen = this.getToolkit().getScreenSize();
		Dimension login = this.getSize();
		this.setLocation((screen.width - login.width) / 2,
				(screen.height - login.height) / 2);
	}

	public static void main(String[] args) {
		DB database = new DB();
		if (database.init()) {// ����ɹ����������ݿ���򿪵�¼����
			LoginFrame frame = new LoginFrame(database);
			frame.setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��", "���������",
					JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// String name = this.nameField.getText();
		// String password =
		// String.copyValueOf(this.passwordField.getPassword());
		// if (this.database.login(name, password)) {// ��¼�ɹ�
		// MainFrame frame = new MainFrame();
		// frame.setLocation(this.getLocation());
		// this.dispose();
		// frame.setVisible(true);
		// } else
		// JOptionPane.showMessageDialog(null, "�û������������", "��¼ʧ��",
		// JOptionPane.WARNING_MESSAGE);
		MainFrame frame = new MainFrame(database);
		frame.setLocation(this.getLocation());
		this.dispose();
		frame.setVisible(true);
	}

}
