package gui;

import javax.swing.*;

import data.DB;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected DB database;

	public LoginFrame(DB database) {
		super("��¼");

		this.database = database;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	public static void main(String[] args) {
		DB database = new DB();
		if (database.init()) {// ����ɹ����������ݿ���򿪵�¼����
			LoginFrame frame = new LoginFrame(database);
			frame.setVisible(true);
		}
		JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��", "���������",
				JOptionPane.ERROR_MESSAGE);
	}

}
