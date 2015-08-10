package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.QuaryLogic;

public class LoginPanel extends JPanel{
	
	private JButton login;
	private JButton reset;
	
	private JTextField username;
	private JPasswordField password;
	
	private JLabel error;
	
	private QuaryLogic quary;
	
	public LoginPanel() {
		//����Ϊ���Բ��ֹ���
		this.setLayout(null);
		
		//��ʼ����½lable
		JLabel lable=new JLabel("��½");
		lable.setFont(new Font("����",Font.BOLD,30));
		lable.setBounds(160, 10, 70, 100);
		this.add(lable);
		
		//��ʼ���û���lable
		JLabel idLable=new JLabel("�û�����");
		Font font=new Font("����",0,20);
		idLable.setFont(font);
		idLable.setBounds(90, 110, 80, 20);
		this.add(idLable);
		
		//��ʼ���û��������
		username=new JTextField();
		username.setBounds(180, 110, 110, 20);
		this.add(username);
		
		//��ʼ������lable
		JLabel passLable=new JLabel("����  ��");
		passLable.setFont(font);
		passLable.setBounds(90, 150, 80, 20);
		this.add(passLable);
		
		//��ʼ�����������
		password=new JPasswordField();
		password.setBounds(180, 150, 110, 20);
		this.add(password);
		
		//��ʼ����½��ť
		login=new JButton("��½");
		login.setBounds(120, 200, 60, 30);
		this.add(login);
		
		//��ʼ�����ð�ť
		reset=new JButton("����");
		reset.setBounds(220, 200, 60, 30);
		this.add(reset);
		
		error=new JLabel("");
		error.setForeground(Color.red);
		error.setBounds(120, 250, 250, 20);
		error.setFont(font);
		this.add(error);
		addListener();
	}
	public void addListener(){
		this.login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id=username.getText();
				String pass=new String(password.getPassword());
				if(id.trim().length()==0||pass.trim().length()==0){
					error.setText("������������Ϣ");
				}else{
					quary.login(id,pass);
				}
			}
		});
		this.reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				password.setText("");
				error.setText("");
				username.requestFocus();
			}
		});
	}
	
	public void setQuary(QuaryLogic quary) {
		this.quary = quary;
	}
	public JLabel getError() {
		return error;
	}
}
