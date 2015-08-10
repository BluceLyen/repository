package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JFrame;
import logic.QuaryLogic;

public class MainFrame extends JFrame{
	
	private LoginPanel loginPanel;
	
	private DataPanel dataPanel;
	
	public MainFrame(){
		
		//设置窗口标题
		this.setTitle("一键查询成绩");
		//设置大小
		this.setSize(400, 600);
		//使窗口居中显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = screen.width - this.getWidth() >> 1;
		int h = (screen.height - this.getHeight() >> 1) - 16;
		this.setLocation(w, h);
		//设置默认的关闭方式
		this.setDefaultCloseOperation(3);
		//设置为不可改变大小
		this.setResizable(false);
		init_loginPanel();
		
		//设置显示
		this.setVisible(true);
	}
	
	private void init_loginPanel(){
		loginPanel=new LoginPanel();
		//添加逻辑组件
		QuaryLogic quary=new QuaryLogic(this, loginPanel);
		loginPanel.setQuary(quary);
		//设置panel
		this.getContentPane().add("Center",loginPanel);
	}
	
	public void changepanel(Map<String, Integer> data){
		//将组件设置为不可见
		this.loginPanel.setVisible(false);
		//删除显示信息的Panel
		this.remove(this.loginPanel);
		//重新构造显示Panel
		dataPanel=new DataPanel(this,data);
		//添加
		this.add(dataPanel);
	}
	
	

	public void backPanel() {
		this.dataPanel.setVisible(false);
		this.remove(this.dataPanel);
		init_loginPanel();
	}
}
