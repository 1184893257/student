package gui;

import javax.swing.*;

public class Author extends JFrame {
	private static final long serialVersionUID = 1L;

	public Author() {
		super("我们");

		int cols = 6;

		String names = "<html>";
		names += "<p align=\"center\">学生信息管理系统(030914班)<p>";
		names += "<table align=\"center\">";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "需求分析"
				+ "</td></tr>";
		names += "<tr>" + "<td align=\"center\" colspan=\"" + cols / 3 + "\">"
				+ "杨延中" + "</td>" + "<td align=\"center\" colspan=\"" + cols
				/ 3 + "\">" + "周旭" + "</td>"
				+ "<td align=\"center\" colspan=\"" + cols / 3 + "\">" + "金方圆"
				+ "</td>" + "</tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "总体设计"
				+ "</td></tr>";
		names += "<tr>" + "<td align=\"center\" colspan=\"" + cols / 2 + "\">"
				+ "杨凯强" + "</td>" + "<td align=\"center\" colspan=\"" + cols
				/ 2 + "\">" + "陈天泳" + "</td>" + "</tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">"
				+ "详细设计与编码" + "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "刘乔羽"
				+ "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "测试"
				+ "</td></tr>";
		names += "<tr><td align=\"center\" colspan=\"" + cols + "\">" + "全体成员"
				+ "</td></tr>";
		names += "</table></html>";

		JLabel label = new JLabel(names);
		add(label, "Center");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
