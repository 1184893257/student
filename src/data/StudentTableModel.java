package data;

import java.util.*;

import javax.swing.table.AbstractTableModel;

/**
 * ѧ����Ϣ��������ģ��
 * 
 * @author lqy
 * 
 */
public class StudentTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	protected String[] columnNames = { "ѧ��", "����", "����", "�Ա�", "����", "��ַ",
			"�绰", "E-mail" };
	/**
	 * �������
	 */
	protected Object[][] data;

	public StudentTableModel(LinkedList<Student> students) {
		this.showStudents(students);
	}

	/**
	 * ��students�е�����ѧ����ʾ����
	 * 
	 * @param students
	 *            ����Ҫ��ʾ�����е�ѧ��
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
		return false;// ������ֱ���޸�
	}

}
