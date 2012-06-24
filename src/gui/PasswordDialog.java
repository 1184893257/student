package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 此类用于输入密码<br>
 * JOptionPane中的Input只能输入明文的信息,密码是保密的,不应该显示出来
 * 
 * @author lqy
 * 
 */
public class PasswordDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	protected JLabel label;
	protected JPasswordField password;

	protected PasswordDialog(Frame owner, String loginName) {
		super(owner);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		this.setTitle("请输入密码");

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		c.gridwidth = GridBagConstraints.REMAINDER;
		label = new JLabel("请输入" + loginName + "的登录密码:");
		label.setFont(label.getFont().deriveFont(20.0F));
		label.setForeground(Color.red);
		layout.setConstraints(label, c);
		add(label);

		password = new JPasswordField(20);
		c.gridy = GridBagConstraints.RELATIVE;
		layout.setConstraints(password, c);
		add(password);

		JButton button = new JButton("确定");
		button.addActionListener(this);
		this.getRootPane().setDefaultButton(button);
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(button, c);
		add(button);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	/**
	 * 弹出密码输入对话框
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param loginName
	 *            密码对应的登录名,用于提示
	 * @return 获得的密码
	 */
	public static String inputPassword(Frame owner, String loginName) {
		PasswordDialog dialog = new PasswordDialog(owner, loginName);
		dialog.setLocation(owner.getLocation());
		dialog.setVisible(true);
		return String.copyValueOf(dialog.password.getPassword());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
