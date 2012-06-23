package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import data.*;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 所有学生的信息,相当于数据库的缓存
	 */
	protected LinkedList<Student> students;
	/**
	 * 封装数据库操作的对象
	 */
	protected DB database;
	/**
	 * 包含要显示的学生的信息的表格数据模型
	 */
	protected StudentTableModel tableModel;

	// 一下是与排序相关的单选可选按钮
	/**
	 * 按姓名排序
	 */
	protected JRadioButton nameSort;
	/**
	 * 逆序?
	 */
	protected JCheckBox dec;

	// 以下跟条件显示有关
	/**
	 * 条件的输入框,可以是一个学号,也可是一个姓名,具体根据按钮来区分
	 */
	protected JTextField filter;
	/**
	 * 为true时显示全部学生,忽略条件;为false时只显示匹配条件的学生
	 */
	protected boolean showAll;
	/**
	 * 为true时条件是姓名,为false时条件是学号
	 */
	protected boolean filterByName;
	/**
	 * 条件
	 */
	protected String nameOrNo;

	public MainFrame(DB database) {
		super("学生信息管理系统");

		this.database = database;

		// 设置布局管理器
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// 添加菜单条
		addMenu();

		// 添加搜索条
		JPanel filter = this.buildFilterPanel();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.weightx = 1.0;
		layout.setConstraints(filter, c);
		add(filter);

		// 添加排序条
		JPanel sort = this.buildSortPanel();
		c.gridy = GridBagConstraints.RELATIVE;
		layout.setConstraints(sort, c);
		add(sort);

		// 添加表格
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
	 * 添加菜单
	 */
	protected void addMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu op = new JMenu("操作");
		JMenu help = new JMenu("帮助");

		JMenuItem admin = new JMenuItem("管理登录用户");
		JMenuItem exit = new JMenuItem("退出");
		JMenuItem author = new JMenuItem("作者");

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
	 * 建立条件查询搜索条
	 * 
	 * @return
	 */
	protected JPanel buildFilterPanel() {
		JPanel ans = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		ans.setLayout(layout);

		// 生成组件
		filter = new JTextField("这里输入学号或姓名", 20);
		JButton filterByName = new JButton("按学号查询");
		JButton filterByNo = new JButton("按姓名查询");
		JButton showAll = new JButton("显示全部学生");

		// 添加事件监听器
		filterByName.addActionListener(this);
		filterByNo.addActionListener(this);
		showAll.addActionListener(this);

		// 放置组件
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
	 * 排序控制条
	 * 
	 * @return
	 */
	protected JPanel buildSortPanel() {
		JPanel ans = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		ans.setLayout(layout);

		// 生成组件
		c.fill = GridBagConstraints.HORIZONTAL;
		nameSort = new JRadioButton("按姓名排序");
		JRadioButton noSort = new JRadioButton("按学号排序");
		dec = new JCheckBox("逆序");

		// 添加事件监听器
		nameSort.addActionListener(this);
		noSort.addActionListener(this);
		dec.addActionListener(this);
		ButtonGroup group = new ButtonGroup();
		group.add(nameSort);
		group.add(noSort);

		// 放置组件
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
	 * 对students进行排序
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
	 * 刷新显示
	 */
	protected void updateDisplay() {
		LinkedList<Student> visibleStudents = new LinkedList<Student>();

		if (showAll)// 显示所有学生?
			visibleStudents = students;
		else if (filterByName) {// 显示名字是nameOrNo的学生
			for (Student stu : students)
				if (nameOrNo.equals(stu.name))
					visibleStudents.add(stu);
		} else {// 显示学号是nameOrNo的学生
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
