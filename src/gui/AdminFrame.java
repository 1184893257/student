package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.DB;

public class AdminFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * ��װ���ݿ�����Ķ���
	 */
	protected DB database;
	/**
	 * ��¼ʱʹ�õĵ�¼��
	 */
	protected String loginName;
	/**
	 * ɾ����ť
	 */
	protected JButton removeButton;
	/**
	 * �޸İ�ť
	 */
	protected JButton modifyButton;
	/**
	 * ��ʾ���й���Ա���ֵ��б�
	 */
	protected JList<String> list;
	/**
	 * ��¼���б������ģ��
	 */
	protected DefaultListModel<String> listmodel;

	public AdminFrame(DB database, String loginName) {
		super("����Ա������");
		this.database = database;
		this.loginName = loginName;

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// ����3����ť
		JButton button = new JButton("��ӹ���Ա");
		layout.setConstraints(button, c);
		add(button);

		c.gridx = GridBagConstraints.RELATIVE;
		removeButton = new JButton("ɾ������Ա");
		removeButton.setEnabled(false); // �б�����ĳ�б�ѡ�в���Ч
		layout.setConstraints(removeButton, c);
		add(removeButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		modifyButton = new JButton("�޸�����");
		modifyButton.setEnabled(false); // �б�����ĳ�б�ѡ�в���Ч
		layout.setConstraints(modifyButton, c);
		add(modifyButton);

		// ע��3����ť���¼�������
		button.setActionCommand("add");
		removeButton.setActionCommand("remove");
		modifyButton.setActionCommand("modify");
		button.addActionListener(this);
		removeButton.addActionListener(this);
		modifyButton.addActionListener(this);

		// ���ɵ�¼���б�
		listmodel = new DefaultListModel<String>();
		list = new JList<String>(listmodel);
		this.updateList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (list.getSelectedIndex() < 0) {
							removeButton.setEnabled(false);
							modifyButton.setEnabled(false);
						} else {
							removeButton.setEnabled(true);
							modifyButton.setEnabled(true);
						}
					}
				});

		JScrollPane listPane = new JScrollPane(list);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(listPane, c);
		add(listPane);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	/**
	 * ��ĳЩ�����������ݿ����Ա�б���ܷ����˱仯,ˢ��֮
	 */
	protected void updateList() {
		listmodel.removeAllElements();
		Vector<String> admins = database.getAllAdmin();
		for (String e : admins)
			listmodel.addElement(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!database.login(loginName,
				PasswordDialog.inputPassword(this, loginName))) {
			JOptionPane.showMessageDialog(this, "�������,�����й���Ա��Ȩ��", "�������ܾ�",
					JOptionPane.WARNING_MESSAGE);
			dispose();
			return;
		}
		String cmd = e.getActionCommand();
		if (cmd.equals("add")) {
			String name = JOptionPane.showInputDialog(this, "������Ҫ��ӵĵ�¼��",
					"��ӹ���Ա", JOptionPane.QUESTION_MESSAGE);
			String password = PasswordDialog.inputPassword(this, name);
			if (database.addAdmin(name, password))
				this.updateList();
			else
				JOptionPane.showMessageDialog(this, "������ͬ������Ա�Ѵ���", "��ӹ���Աʧ��",
						JOptionPane.WARNING_MESSAGE);
		} else if (cmd.equals("remove")) {
			String name = list.getSelectedValue();
			database.removeAdmin(name);
			this.updateList();
		} else if (cmd.equals("modify")) {
			String name = list.getSelectedValue();
			String password = PasswordDialog.inputPassword(this, name + "��");
			database.removeAdmin(name);
			database.addAdmin(name, password);
		} else
			JOptionPane.showMessageDialog(this, "δ֪�Ķ���:" + cmd, "�������",
					JOptionPane.ERROR_MESSAGE);
	}

}
