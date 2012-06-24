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
	 * ����ѧ������Ϣ,�൱�����ݿ�Ļ���
	 */
	protected LinkedList<Student> students;
	/**
	 * ��װ���ݿ�����Ķ���
	 */
	protected DB database;
	/**
	 * ��ʾѧ����Ϣ�ı��
	 */
	protected JTable table;
	/**
	 * ����Ҫ��ʾ��ѧ������Ϣ�ı������ģ��
	 */
	protected StudentTableModel tableModel;
	/**
	 * �����Ҽ��˵�
	 */
	protected JPopupMenu menu;
	/**
	 * ����ѧ����Ϣ�����\�޸ĶԻ���
	 */
	protected StudentDialog dialog;
	/**
	 * ����Ա����
	 */
	protected AdminFrame admin;

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

	public MainFrame(DB database, String loginName) {
		super("ѧ����Ϣ����ϵͳ");

		this.database = database;
		this.dialog = new StudentDialog(this);
		this.admin = new AdminFrame(database, loginName);

		// ���ò��ֹ�����
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// ��Ӳ˵���
		addMenu();

		// ���������
		this.showAll = true;
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

		// �������ѧ��,�������
		this.students = database.getAllStudent();
		// TODO ɾ�����¼��д���
		for (Student e : students)
			System.out.println("INSERT INTO Student VALUES('" + e.no + "',"
					+ "'" + e.name + "'," + "'" + e.sex + "'," + "'"
					+ e.birthday + "'," + "'" + e.address + "'," + "'" + e.tel
					+ "'," + "'" + e.email + "')");
		sort(students);
		this.tableModel = new StudentTableModel(students);
		table = new JTable(this.tableModel);

		// ��ӱ����Ҽ��˵�
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

		// ������delete����ɾ��һ��ѧ��
		// һ��ֻ��ѡһ��
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					remove();
			}

		});

		// ���ø��еĿ��
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(70);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(30);
		colModel.getColumn(3).setPreferredWidth(30);
		colModel.getColumn(4).setPreferredWidth(70);
		colModel.getColumn(5).setPreferredWidth(200);
		colModel.getColumn(6).setPreferredWidth(100);
		colModel.getColumn(7).setPreferredWidth(150);

		// ���ñ��
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(700, 500));
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(tablePane, c);
		add(tablePane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		JButton filterByNo = new JButton("��ѧ�Ų�ѯ");
		JButton filterByName = new JButton("��������ѯ");
		JButton showAll = new JButton("��ʾȫ��ѧ��");

		// ����¼�������
		filterByName.setActionCommand("filterByName");
		filterByNo.setActionCommand("filterByNo");
		showAll.setActionCommand("showAll");
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
		JRadioButton noSort = new JRadioButton("��ѧ������", true);
		dec = new JCheckBox("����");
		ButtonGroup group = new ButtonGroup();
		group.add(nameSort);
		group.add(noSort);

		// ����¼�������
		nameSort.setActionCommand("resort");
		noSort.setActionCommand("resort");
		dec.setActionCommand("resort");
		nameSort.addActionListener(this);
		noSort.addActionListener(this);
		dec.addActionListener(this);

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

	protected JPopupMenu buildTableMenu() {
		JPopupMenu ans = new JPopupMenu();
		String[] show = { "���", "ɾ��", "�޸�" };
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
				if (nameOrNo.equals(stu.no))
					visibleStudents.add(stu);
		}

		this.sort(visibleStudents);
		this.tableModel.showStudents(visibleStudents);
	}

	/**
	 * ���һ��ѧ��
	 */
	protected void add() {
		if (!dialog.showAddDialog())
			return;// �û�ȡ������ζԻ�

		// �����ݿ���в���,����ɹ������ѧ����students��
		if (database.addStudent(dialog.student)) {
			students.add(dialog.student);
			this.updateDisplay();
		} else
			JOptionPane.showMessageDialog(this, "��������ӵ�ѧ����ѧ��������ѧ����ѧ�ų�ͻ��",
					"���ѧ��ʧ��", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * �޸�һ��ѧ������Ϣ
	 */
	protected void modify() {
		// ���ѡ�е�Ҫ�޸ĵ���
		int row = this.table.getSelectedRow();
		if (row < 0)
			return;

		// ���Ҫ�޸ĵ�ѧ������Ϣ
		final String no = (String) tableModel.getValueAt(row, 0);
		Student isToModify = null;
		for (Student e : students)
			if (e.no.equals(no)) {
				isToModify = e;
				break;
			}

		if (!dialog.showModifyDialog(isToModify))
			return;// ����û�ȡ���Ի�,�򷵻�

		// �����ݿ��޸ĳɹ��Ļ��ͽ��ɵ�ѧ����Ϣɾ��,����µ�ѧ����Ϣ
		if (database.modifyStudent(dialog.editingNo, dialog.student)) {
			students.remove(isToModify);
			students.add(dialog.student);
			this.updateDisplay();
		} else
			JOptionPane.showMessageDialog(this, "��������ӵ�ѧ����ѧ��������ѧ����ѧ�ų�ͻ��",
					"���ѧ��ʧ��", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * ɾ��ѡ�е�ѧ��
	 */
	protected void remove() {
		// ���ѡ�е�Ҫɾ������
		int row = this.table.getSelectedRow();
		if (row < 0)
			return;

		// ���Ҫɾ����ѧ����ѧ��
		final String no = (String) tableModel.getValueAt(row, 0);

		// �����ݿ��students��ɾ�����ѧ��
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
			else if (action.equals("admin")) {
				admin.setLocation(MainFrame.this.getLocation());
				admin.setVisible(true);
			} else if (action.equals("author"))
				;// TODO ����������Ϣ
			else if (action.equals("add"))
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
						"δ֪�Ķ���:" + action, "�������", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new doActions(e.getActionCommand()));
	}
}
