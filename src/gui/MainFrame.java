package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

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
	 * 显示学生信息的表格
	 */
	protected JTable table;
	/**
	 * 包含要显示的学生的信息的表格数据模型
	 */
	protected StudentTableModel tableModel;
	/**
	 * 表格的右键菜单
	 */
	protected JPopupMenu menu;
	/**
	 * 单个学生信息的添加\修改对话框
	 */
	protected StudentDialog dialog;
	/**
	 * 管理员窗体
	 */
	protected AdminFrame admin;
	/**
	 * 作者信息的窗体
	 */
	protected Author author;

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
	
	/**
	 * 将本窗体放置在屏幕中央
	 */
	protected void setCenter() {
		Dimension screen = this.getToolkit().getScreenSize();
		Dimension login = this.getSize();
		this.setLocation((screen.width - login.width) / 2,
				(screen.height - login.height) / 2);
	}

	public MainFrame(DB database, String loginName) {
		super("学生信息管理系统");

		this.database = database;
		this.dialog = new StudentDialog(this);
		this.admin = new AdminFrame(database, loginName);
		this.author = new Author();

		// 设置布局管理器
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// 添加菜单条
		addMenu();

		// 添加搜索条
		this.showAll = true;
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

		// 获得所有学生,建立表格
		this.students = database.getAllStudent();
		sort(students);
		this.tableModel = new StudentTableModel(students);
		table = new JTable(this.tableModel);

		// 添加表格的右键菜单
		menu = this.buildTableMenu();
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});

		// 表格监听delete按键删除一个学生
		// 一次只能选一行
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					remove();
			}

		});

		// 设置各列的宽度
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(70);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(30);
		colModel.getColumn(3).setPreferredWidth(30);
		colModel.getColumn(4).setPreferredWidth(70);
		colModel.getColumn(5).setPreferredWidth(200);
		colModel.getColumn(6).setPreferredWidth(100);
		colModel.getColumn(7).setPreferredWidth(150);

		// 放置表格
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(700, 500));
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(tablePane, c);
		add(tablePane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setCenter();
		Point loc = this.getLocation();
		dialog.setLocation(loc);
		admin.setLocation(loc);
		author.setLocation(loc);
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

		admin.setActionCommand("admin");
		exit.setActionCommand("exit");
		author.setActionCommand("author");
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
		JButton filterByNo = new JButton("按学号查询");
		JButton filterByName = new JButton("按姓名查询");
		JButton showAll = new JButton("显示全部学生");

		// 添加事件监听器
		filterByName.setActionCommand("filterByName");
		filterByNo.setActionCommand("filterByNo");
		showAll.setActionCommand("showAll");
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
		JRadioButton noSort = new JRadioButton("按学号排序", true);
		dec = new JCheckBox("逆序");
		ButtonGroup group = new ButtonGroup();
		group.add(nameSort);
		group.add(noSort);

		// 添加事件监听器
		nameSort.setActionCommand("resort");
		noSort.setActionCommand("resort");
		dec.setActionCommand("resort");
		nameSort.addActionListener(this);
		noSort.addActionListener(this);
		dec.addActionListener(this);

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

	protected JPopupMenu buildTableMenu() {
		JPopupMenu ans = new JPopupMenu();
		String[] show = { "添加", "删除", "修改" };
		String[] action = { "add", "remove", "modify" };
		int i;
		for (i = 0; i < show.length; ++i) {
			JMenuItem item = new JMenuItem(show[i]);
			item.setActionCommand(action[i]);
			item.addActionListener(this);
			ans.add(item);
		}
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
				if (nameOrNo.equals(stu.no))
					visibleStudents.add(stu);
		}

		this.sort(visibleStudents);
		this.tableModel.showStudents(visibleStudents);
	}

	/**
	 * 添加一个学生
	 */
	protected void add() {
		if (!dialog.showAddDialog())
			return;// 用户取消了这次对话

		// 对数据库进行操作,如果成功则添加学生到students中
		if (database.addStudent(dialog.student)) {
			students.add(dialog.student);
			this.updateDisplay();
		} else
			JOptionPane.showMessageDialog(this, "可能是添加的学生的学号与已有学生的学号冲突了",
					"添加学生失败", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * 修改一个学生的信息
	 */
	protected void modify() {
		// 获得选中的要修改的行
		int row = this.table.getSelectedRow();
		if (row < 0)
			return;

		// 获得要修改的学生的信息
		final String no = (String) tableModel.getValueAt(row, 0);
		Student isToModify = null;
		for (Student e : students)
			if (e.no.equals(no)) {
				isToModify = e;
				break;
			}

		if (!dialog.showModifyDialog(isToModify))
			return;// 如果用户取消对话,则返回

		// 对数据库修改成功的话就将旧的学生信息删除,添加新的学生信息
		if (database.modifyStudent(dialog.editingNo, dialog.student)) {
			students.remove(isToModify);
			students.add(dialog.student);
			this.updateDisplay();
		} else
			JOptionPane.showMessageDialog(this, "可能是添加的学生的学号与已有学生的学号冲突了",
					"添加学生失败", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * 删除选中的学生
	 */
	protected void remove() {
		// 获得选中的要删除的行
		int row = this.table.getSelectedRow();
		if (row < 0)
			return;

		// 获得要删除的学生的学号
		final String no = (String) tableModel.getValueAt(row, 0);

		// 在数据库和students中删除这个学生
		database.delStudent(no);
		Iterator<Student> it = students.iterator();
		while (it.hasNext())
			if (it.next().no.equals(no)) {
				it.remove();
				break;
			}
		this.updateDisplay();
	}

	protected class doActions implements Runnable {
		protected String action;

		public doActions(String action) {
			this.action = action;
		}

		@Override
		public void run() {
			if (action.equals("exit"))
				System.exit(0);
			else if (action.equals("resort"))
				MainFrame.this.updateDisplay();
			else if (action.equals("admin"))
				admin.setVisible(true);
			else if (action.equals("author")) {
				author.setVisible(true);
			} else if (action.equals("add"))
				add();
			else if (action.equals("remove"))
				remove();
			else if (action.equals("modify"))
				modify();
			else if (action.equals("filterByName")) {
				showAll = false;
				MainFrame.this.filterByName = true;
				nameOrNo = MainFrame.this.filter.getText();
				MainFrame.this.updateDisplay();
			} else if (action.equals("filterByNo")) {
				showAll = false;
				MainFrame.this.filterByName = false;
				nameOrNo = MainFrame.this.filter.getText();
				MainFrame.this.updateDisplay();
			} else if (action.equals("showAll")) {
				showAll = true;
				MainFrame.this.updateDisplay();
			} else
				JOptionPane.showMessageDialog(MainFrame.this,
						"未知的动作:" + action, "程序出错", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new doActions(e.getActionCommand()));
	}
}
