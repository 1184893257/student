package gui;

import javax.swing.*;

public class Author extends JFrame {
	private static final long serialVersionUID = 1L;

	public Author() {
		super("����");

		int cols = 6;

		String names = "<html>";
		names += "<p align=\"center\">ѧ����Ϣ����ϵͳ(030914��)<p>";
		names += "<table align=\"center\">";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "�������"
				+ "</td></tr>";
		names += "<tr>" + "<td align=\"center\" colspan=\"" + cols / 3 + "\">"
				+ "������" + "</td>" + "<td align=\"center\" colspan=\"" + cols
				/ 3 + "\">" + "����" + "</td>"
				+ "<td align=\"center\" colspan=\"" + cols / 3 + "\">" + "��Բ"
				+ "</td>" + "</tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "�������"
				+ "</td></tr>";
		names += "<tr>" + "<td align=\"center\" colspan=\"" + cols / 2 + "\">"
				+ "�ǿ" + "</td>" + "<td align=\"center\" colspan=\"" + cols
				/ 2 + "\">" + "������" + "</td>" + "</tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">"
				+ "��ϸ��������" + "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "������"
				+ "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "����"
				+ "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "ȫ���Ա"
				+ "</td></tr>";
		names += "</table></html>";

		JLabel label = new JLabel(names);
		add(label, "Center");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
