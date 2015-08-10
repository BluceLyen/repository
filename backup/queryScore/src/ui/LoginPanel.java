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
		//设置为绝对布局管理
		this.setLayout(null);
		
		//初始化登陆lable
		JLabel lable=new JLabel("登陆");
		lable.setFont(new Font("楷体",Font.BOLD,30));
		lable.setBounds(160, 10, 70, 100);
		this.add(lable);
		
		//初始化用户名lable
		JLabel idLable=new JLabel("用户名：");
		Font font=new Font("楷体",0,20);
		idLable.setFont(font);
		idLable.setBounds(90, 110, 80, 20);
		this.add(idLable);
		
		//初始化用户名输入框
		username=new JTextField();
		username.setBounds(180, 110, 110, 20);
		this.add(username);
		
		//初始化密码lable
		JLabel passLable=new JLabel("密码  ：");
		passLable.setFont(font);
		passLable.setBounds(90, 150, 80, 20);
		this.add(passLable);
		
		//初始化密码输入框
		password=new JPasswordField();
		password.setBounds(180, 150, 110, 20);
		this.add(password);
		
		//初始化登陆按钮
		login=new JButton("登陆");
		login.setBounds(120, 200, 60, 30);
		this.add(login);
		
		//初始化重置按钮
		reset=new JButton("重置");
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
					error.setText("请输入完整信息");
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
