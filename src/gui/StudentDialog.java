package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import data.Student;

/**
 * 学生信息添加,修改对话框
 * 
 * @author lqy
 * 
 */
public class StudentDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 为true表示这次对话被用户取消了,数据都不可靠
	 */
	public boolean canceled;
	/**
	 * 此次对话的结果(新添加的学生的信息,修改后的学生信息)
	 */
	public Student student;
	/**
	 * 正在修改的学生原来的学号
	 */
	public String editingNo;

	// 输入信息的组件
	protected JTextField no;
	protected JTextField name;
	protected JComboBox<String> sex;
	protected JComboBox<Integer> year, month, day;
	protected JTextField address;
	protected JTextField tel;
	protected JTextField email;
	protected JButton button;

	public StudentDialog(Frame owner) {
		super(owner);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);

		// 设置布局管理器
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c1 = new GridBagConstraints();
		GridBagConstraints c2;
		this.setLayout(layout);

		c1.gridx = GridBagConstraints.RELATIVE;
		c1.gridy = GridBagConstraints.RELATIVE;
		c2 = (GridBagConstraints) c1.clone();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1.0;
		c2.gridwidth = GridBagConstraints.REMAINDER;

		// 学号栏
		JLabel label = new JLabel("学号:");
		layout.setConstraints(label, c1);
		add(label);
		no = new JTextField(20);
		layout.setConstraints(no, c2);
		add(no);

		// 姓名栏
		label = new JLabel("姓名:");
		layout.setConstraints(label, c1);
		add(label);
		name = new JTextField(20);
		layout.setConstraints(name, c2);
		add(name);

		// 性别列表
		label = new JLabel("性别:");
		layout.setConstraints(label, c1);
		add(label);
		sex = new JComboBox<String>(new String[] { "男", "女" });
		layout.setConstraints(sex, c2);
		add(sex);

		// 出生日期
		label = new JLabel("出生日期:");
		layout.setConstraints(label, c1);
		add(label);
		buildBirthdayBoxs();// 设置year/month/day组件
		layout.setConstraints(year, c1);
		add(year);
		label = new JLabel("年");
		layout.setConstraints(label, c1);
		add(label);
		layout.setConstraints(month, c1);
		add(month);
		label = new JLabel("月");
		layout.setConstraints(label, c1);
		add(label);
		layout.setConstraints(day, c1);
		add(day);
		label = new JLabel("日");
		layout.setConstraints(label, c2);
		add(label);

		// 地址
		label = new JLabel("地址:");
		layout.setConstraints(label, c1);
		add(label);
		address = new JTextField(20);
		layout.setConstraints(address, c2);
		add(address);

		// 电话
		label = new JLabel("电话:");
		layout.setConstraints(label, c1);
		add(label);
		tel = new JTextField(20);
		layout.setConstraints(tel, c2);
		add(tel);

		// E-mail
		label = new JLabel("E-mail:");
		layout.setConstraints(label, c1);
		add(label);
		email = new JTextField(20);
		layout.setConstraints(email, c2);
		add(email);

		// 确定按钮
		button = new JButton("确定");
		button.addActionListener(this);
		c2.fill = GridBagConstraints.NONE;
		c2.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(button, c2);
		add(button);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				canceled = true;
				dispose();
			}

		});
		pack();
	}

	/**
	 * 用于求某月的天数
	 */
	protected Calendar cal = Calendar.getInstance();

	/**
	 * 求year年month月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	protected int getDaysOfMonth(int year, int month) {
		cal.set(year, month - 1, 1);
		cal.roll(Calendar.DATE, -1);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 设置day的天数
	 * 
	 * @param days
	 */
	public void setDay(int days) {
		DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>) day
				.getModel();
		model.removeAllElements();
		for (int i = 1; i <= days; ++i)
			model.addElement(i);
	}

	/**
	 * 建立日期相关的几个表拉列表框
	 */
	protected void buildBirthdayBoxs() {
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		final int thisYear = today.get(Calendar.YEAR);

		final int startYear = 1980;
		Integer[] birthYears = new Integer[thisYear - startYear + 1];
		int i;
		for (i = startYear; i <= thisYear; ++i)
			birthYears[thisYear - i] = i;
		year = new JComboBox<Integer>(birthYears);
		year.setSelectedIndex(thisYear - 1991);

		Integer[] birthMonths = new Integer[12];
		for (i = 1; i <= 12; ++i)
			birthMonths[i - 1] = i;
		month = new JComboBox<Integer>(birthMonths);

		// day的具体值到选中年的时候确定
		Integer[] birthDays = new Integer[30];
		for (i = 1; i <= 30; i++)
			birthDays[i - 1] = i;
		day = new JComboBox<Integer>(birthDays);
		day.getComponent(0).addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final int days = getDaysOfMonth(
						(Integer) year.getSelectedItem(),
						(Integer) month.getSelectedItem());
				StudentDialog.this.setDay(days);
			}

		});
	}

	/**
	 * 显示添加对话框
	 * 
	 * @return 如果用户点关闭按钮则表示取消这次对话,返回false
	 */
	public boolean showAddDialog() {
		this.setTitle("请输入要添加的学生的信息");
		canceled = false;
		this.getRootPane().setDefaultButton(button);
		this.setVisible(true);
		return !canceled;
	}

	/**
	 * 显示修改学生信息的对话框
	 * 
	 * @param student
	 *            要修改的学生的信息
	 * @return 如果用户点击关闭按钮则返回false表示取消这次修改
	 */
	public boolean showModifyDialog(Student student) {
		this.setTitle("请修改");
		canceled = false;
		this.editingNo = student.no;
		no.setText(student.no);
		name.setText(student.name);
		sex.setSelectedItem(student.sex);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(student.birthday.getTime());
		int year, month, day;
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DATE);
		this.year.setSelectedItem(year);
		this.month.setSelectedItem(month);
		this.setDay(this.getDaysOfMonth(year, month));
		this.day.setSelectedItem(day);

		address.setText(student.address);
		tel.setText(student.tel);
		email.setText(student.email);
		this.getRootPane().setDefaultButton(button);
		this.setVisible(true);
		return !canceled;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		final int thisyear = cal.get(Calendar.YEAR);
		cal.set((Integer) year.getSelectedItem(),
				(Integer) month.getSelectedItem() - 1,
				(Integer) day.getSelectedItem());
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		this.student = new Student(no.getText(), name.getText(), thisyear
				- (Integer) year.getSelectedItem(),
				(String) sex.getSelectedItem(), date, address.getText(),
				tel.getText(), email.getText());
		this.dispose();
	}
}
