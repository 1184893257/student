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
	 * 封装数据库操作的对象
	 */
	protected DB database;
	/**
	 * 登录时使用的登录名
	 */
	protected String loginName;
	/**
	 * 删除按钮
	 */
	protected JButton removeButton;
	/**
	 * 修改按钮
	 */
	protected JButton modifyButton;
	/**
	 * 显示所有管理员名字的列表
	 */
	protected JList<String> list;
	/**
	 * 登录名列表的数据模型
	 */
	protected DefaultListModel<String> listmodel;

	public AdminFrame(DB database, String loginName) {
		super("管理员管理窗体");
		this.database = database;
		this.loginName = loginName;

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// 放置3个按钮
		JButton button = new JButton("添加管理员");
		layout.setConstraints(button, c);
		add(button);

		c.gridx = GridBagConstraints.RELATIVE;
		removeButton = new JButton("删除管理员");
		removeButton.setEnabled(false); // 列表中有某行被选中才有效
		layout.setConstraints(removeButton, c);
		add(removeButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		modifyButton = new JButton("修改密码");
		modifyButton.setEnabled(false); // 列表中有某行被选中才有效
		layout.setConstraints(modifyButton, c);
		add(modifyButton);

		// 注册3个按钮的事件监听器
		button.setActionCommand("add");
		removeButton.setActionCommand("remove");
		modifyButton.setActionCommand("modify");
		button.addActionListener(this);
		removeButton.addActionListener(this);
		modifyButton.addActionListener(this);

		// 生成登录名列表
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
	 * 在某些操作过后数据库管理员列表可能发生了变化,刷新之
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
			JOptionPane.showMessageDialog(this, "密码错误,不具有管理员的权限", "操作被拒绝",
					JOptionPane.WARNING_MESSAGE);
			dispose();
			return;
		}
		String cmd = e.getActionCommand();
		if (cmd.equals("add")) {
			String name = JOptionPane.showInputDialog(this, "请输入要添加的登录名",
					"添加管理员", JOptionPane.QUESTION_MESSAGE);
			String password = PasswordDialog.inputPassword(this, name);
			if (database.addAdmin(name, password))
				this.updateList();
			else
				JOptionPane.showMessageDialog(this, "可能是同名管理员已存在", "添加管理员失败",
						JOptionPane.WARNING_MESSAGE);
		} else if (cmd.equals("remove")) {
			String name = list.getSelectedValue();
			database.removeAdmin(name);
			this.updateList();
		} else if (cmd.equals("modify")) {
			String name = list.getSelectedValue();
			String password = PasswordDialog.inputPassword(this, name + "新");
			database.removeAdmin(name);
			database.addAdmin(name, password);
		} else
			JOptionPane.showMessageDialog(this, "未知的动作:" + cmd, "程序出错",
					JOptionPane.ERROR_MESSAGE);
	}

}
