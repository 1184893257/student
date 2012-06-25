package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import data.Student;

/**
 * ѧ����Ϣ���,�޸ĶԻ���
 * 
 * @author lqy
 * 
 */
public class StudentDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Ϊtrue��ʾ��ζԻ����û�ȡ����,���ݶ����ɿ�
	 */
	public boolean canceled;
	/**
	 * �˴ζԻ��Ľ��(����ӵ�ѧ������Ϣ,�޸ĺ��ѧ����Ϣ)
	 */
	public Student student;
	/**
	 * �����޸ĵ�ѧ��ԭ����ѧ��
	 */
	public String editingNo;

	// ������Ϣ�����
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

		// ���ò��ֹ�����
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

		// ѧ����
		JLabel label = new JLabel("ѧ��:");
		layout.setConstraints(label, c1);
		add(label);
		no = new JTextField(20);
		layout.setConstraints(no, c2);
		add(no);

		// ������
		label = new JLabel("����:");
		layout.setConstraints(label, c1);
		add(label);
		name = new JTextField(20);
		layout.setConstraints(name, c2);
		add(name);

		// �Ա��б�
		label = new JLabel("�Ա�:");
		layout.setConstraints(label, c1);
		add(label);
		sex = new JComboBox<String>(new String[] { "��", "Ů" });
		layout.setConstraints(sex, c2);
		add(sex);

		// ��������
		label = new JLabel("��������:");
		layout.setConstraints(label, c1);
		add(label);
		buildBirthdayBoxs();// ����year/month/day���
		layout.setConstraints(year, c1);
		add(year);
		label = new JLabel("��");
		layout.setConstraints(label, c1);
		add(label);
		layout.setConstraints(month, c1);
		add(month);
		label = new JLabel("��");
		layout.setConstraints(label, c1);
		add(label);
		layout.setConstraints(day, c1);
		add(day);
		label = new JLabel("��");
		layout.setConstraints(label, c2);
		add(label);

		// ��ַ
		label = new JLabel("��ַ:");
		layout.setConstraints(label, c1);
		add(label);
		address = new JTextField(20);
		layout.setConstraints(address, c2);
		add(address);

		// �绰
		label = new JLabel("�绰:");
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

		// ȷ����ť
		button = new JButton("ȷ��");
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
	 * ������ĳ�µ�����
	 */
	protected Calendar cal = Calendar.getInstance();

	/**
	 * ��year��month�µ�����
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
	 * ����day������
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
	 * ����������صļ��������б��
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

		// day�ľ���ֵ��ѡ�����ʱ��ȷ��
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
	 * ��ʾ��ӶԻ���
	 * 
	 * @return ����û���رհ�ť���ʾȡ����ζԻ�,����false
	 */
	public boolean showAddDialog() {
		this.setTitle("������Ҫ��ӵ�ѧ������Ϣ");
		canceled = false;
		this.getRootPane().setDefaultButton(button);
		this.setVisible(true);
		return !canceled;
	}

	/**
	 * ��ʾ�޸�ѧ����Ϣ�ĶԻ���
	 * 
	 * @param student
	 *            Ҫ�޸ĵ�ѧ������Ϣ
	 * @return ����û�����رհ�ť�򷵻�false��ʾȡ������޸�
	 */
	public boolean showModifyDialog(Student student) {
		this.setTitle("���޸�");
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
