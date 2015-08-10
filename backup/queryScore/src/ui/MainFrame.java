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
		
		//���ô��ڱ���
		this.setTitle("һ����ѯ�ɼ�");
		//���ô�С
		this.setSize(400, 600);
		//ʹ���ھ�����ʾ
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = screen.width - this.getWidth() >> 1;
		int h = (screen.height - this.getHeight() >> 1) - 16;
		this.setLocation(w, h);
		//����Ĭ�ϵĹرշ�ʽ
		this.setDefaultCloseOperation(3);
		//����Ϊ���ɸı��С
		this.setResizable(false);
		init_loginPanel();
		
		//������ʾ
		this.setVisible(true);
	}
	
	private void init_loginPanel(){
		loginPanel=new LoginPanel();
		//����߼����
		QuaryLogic quary=new QuaryLogic(this, loginPanel);
		loginPanel.setQuary(quary);
		//����panel
		this.getContentPane().add("Center",loginPanel);
	}
	
	public void changepanel(Map<String, Integer> data){
		//���������Ϊ���ɼ�
		this.loginPanel.setVisible(false);
		//ɾ����ʾ��Ϣ��Panel
		this.remove(this.loginPanel);
		//���¹�����ʾPanel
		dataPanel=new DataPanel(this,data);
		//���
		this.add(dataPanel);
	}
	
	

	public void backPanel() {
		this.dataPanel.setVisible(false);
		this.remove(this.dataPanel);
		init_loginPanel();
	}
}
