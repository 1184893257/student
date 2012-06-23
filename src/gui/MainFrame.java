package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import data.*;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * ����ѧ������Ϣ,�൱�����ݿ�Ļ���
	 */
	protected LinkedList<Student> students;
	/**
	 * ��װ���ݿ�����Ķ���
	 */
	protected DB database;
	/**
	 * ����Ҫ��ʾ��ѧ������Ϣ�ı������ģ��
	 */
	protected StudentTableModel tableModel;

	// һ������������صĵ�ѡ��ѡ��ť
	/**
	 * ����������
	 */
	protected JRadioButton nameSort;
	/**
	 * ����?
	 */
	protected JCheckBox dec;

	// ���¸�������ʾ�й�
	/**
	 * �����������,������һ��ѧ��,Ҳ����һ������,������ݰ�ť������
	 */
	protected JTextField filter;
	/**
	 * Ϊtrueʱ��ʾȫ��ѧ��,��������;Ϊfalseʱֻ��ʾƥ��������ѧ��
	 */
	protected boolean showAll;
	/**
	 * Ϊtrueʱ����������,Ϊfalseʱ������ѧ��
	 */
	protected boolean filterByName;
	/**
	 * ����
	 */
	protected String nameOrNo;

	public MainFrame(DB database) {
		super("ѧ����Ϣ����ϵͳ");

		this.database = database;

		// ���ò��ֹ�����
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// ��Ӳ˵���
		addMenu();

		// ���������
		JPanel filter = this.buildFilterPanel();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.weightx = 1.0;
		layout.setConstraints(filter, c);
		add(filter);

		// ���������
		JPanel sort = this.buildSortPanel();
		c.gridy = GridBagConstraints.RELATIVE;
		layout.setConstraints(sort, c);
		add(sort);

		// ��ӱ��
		this.students = database.getAllStudent();
		sort(students);
		this.tableModel = new StudentTableModel(students);
		JScrollPane table = new JScrollPane(new JTable(this.tableModel));
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(table, c);
		add(table);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	/**
	 * ��Ӳ˵�
	 */
	protected void addMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu op = new JMenu("����");
		JMenu help = new JMenu("����");

		JMenuItem admin = new JMenuItem("�����¼�û�");
		JMenuItem exit = new JMenuItem("�˳�");
		JMenuItem author = new JMenuItem("����");

		admin.addActionListener(this);
		exit.addActionListener(this);
		author.addActionListener(this);

		op.add(admin);
		op.add(exit);
		help.add(author);

		bar.add(op);
		bar.add(help);
		this.setJMenuBar(bar);
	}

	/**
	 * ����������ѯ������
	 * 
	 * @return
	 */
	protected JPanel buildFilterPanel() {
		JPanel ans = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		ans.setLayout(layout);

		// �������
		filter = new JTextField("��������ѧ�Ż�����", 20);
		JButton filterByName = new JButton("��ѧ�Ų�ѯ");
		JButton filterByNo = new JButton("��������ѯ");
		JButton showAll = new JButton("��ʾȫ��ѧ��");

		// ����¼�������
		filterByName.addActionListener(this);
		filterByNo.addActionListener(this);
		showAll.addActionListener(this);

		// �������
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(filter, c);
		ans.add(filter);

		c.weightx = 0.0;
		c.gridx = GridBagConstraints.RELATIVE;
		layout.setConstraints(filterByName, c);
		ans.add(filterByName);

		layout.setConstraints(filterByNo, c);
		ans.add(filterByNo);

		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(showAll, c);
		ans.add(showAll);

		return ans;
	}

	/**
	 * ���������
	 * 
	 * @return
	 */
	protected JPanel buildSortPanel() {
		JPanel ans = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		ans.setLayout(layout);

		// �������
		c.fill = GridBagConstraints.HORIZONTAL;
		nameSort = new JRadioButton("����������");
		JRadioButton noSort = new JRadioButton("��ѧ������");
		dec = new JCheckBox("����");

		// ����¼�������
		nameSort.addActionListener(this);
		noSort.addActionListener(this);
		dec.addActionListener(this);
		ButtonGroup group = new ButtonGroup();
		group.add(nameSort);
		group.add(noSort);

		// �������
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(nameSort, c);
		ans.add(nameSort);

		c.gridx = GridBagConstraints.RELATIVE;
		layout.setConstraints(noSort, c);
		ans.add(noSort);

		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(dec, c);
		ans.add(dec);

		return ans;
	}

	/**
	 * ��students��������
	 */
	protected void sort(LinkedList<Student> visibleStudents) {
		Comparator<Student> comparator;
		if (this.nameSort.isSelected())
			comparator = dec.isSelected() ? Comparators.pDec : Comparators.pInc;
		else
			comparator = dec.isSelected() ? Comparators.nDec : Comparators.nInc;
		Collections.sort(visibleStudents, comparator);
	}

	/**
	 * ˢ����ʾ
	 */
	protected void updateDisplay() {
		LinkedList<Student> visibleStudents = new LinkedList<Student>();

		if (showAll)// ��ʾ����ѧ��?
			visibleStudents = students;
		else if (filterByName) {// ��ʾ������nameOrNo��ѧ��
			for (Student stu : students)
				if (nameOrNo.equals(stu.name))
					visibleStudents.add(stu);
		} else {// ��ʾѧ����nameOrNo��ѧ��
			for (Student stu : students)
				if (nameOrNo.equals(stu.name))
					visibleStudents.add(stu);
		}

		this.sort(visibleStudents);
		this.tableModel.showStudents(visibleStudents);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}
