package data;

import java.util.*;

import javax.swing.table.AbstractTableModel;

/**
 * 学生信息表格的数据模型
 * 
 * @author lqy
 * 
 */
public class StudentTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 列名
	 */
	protected String[] columnNames = { "学号", "姓名", "年龄", "性别", "生日", "地址",
			"电话", "E-mail" };
	/**
	 * 表格数据
	 */
	protected Object[][] data;

	public StudentTableModel(LinkedList<Student> students) {
		this.showStudents(students);
	}

	/**
	 * 将students中的所有学生显示出来
	 * 
	 * @param students
	 *            包含要显示的所有的学生
	 */
	public void showStudents(LinkedList<Student> students) {
		final int size = students.size();
		data = new Object[size][];

		int i;
		Iterator<Student> it = students.iterator();
		Student t;
		for (i = 0; it.hasNext(); ++i) {
			t = it.next();
			data[i] = new Object[] { t.no, t.name, t.age, t.sex, t.birthday,
					t.address, t.tel, t.email };
		}

		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return data[0][columnIndex].getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;// 不允许直接修改
	}

}
