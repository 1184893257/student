package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * ����������������<br>
 * JOptionPane�е�Inputֻ���������ĵ���Ϣ,�����Ǳ��ܵ�,��Ӧ����ʾ����
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
		this.setTitle("����������");

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		c.gridwidth = GridBagConstraints.REMAINDER;
		label = new JLabel("������" + loginName + "�ĵ�¼����:");
		label.setFont(label.getFont().deriveFont(20.0F));
		label.setForeground(Color.red);
		layout.setConstraints(label, c);
		add(label);

		password = new JPasswordField(20);
		c.gridy = GridBagConstraints.RELATIVE;
		layout.setConstraints(password, c);
		add(password);

		JButton button = new JButton("ȷ��");
		button.addActionListener(this);
		this.getRootPane().setDefaultButton(button);
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(button, c);
		add(button);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	/**
	 * ������������Ի���
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param loginName
	 *            �����Ӧ�ĵ�¼��,������ʾ
	 * @return ��õ�����
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
