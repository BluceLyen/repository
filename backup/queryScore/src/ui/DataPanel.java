package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * ��ʾ���ݵ����
 * @author xwjdsh
 */
public class DataPanel extends JPanel {
	
	
	
	private List<Map.Entry<String, Integer>> scores;
	
	private MainFrame frame;
	
	private JPanel data;
 	
	public DataPanel(MainFrame frame,Map<String, Integer> scores) {
		this.frame=frame;
		this.scores=new ArrayList<Map.Entry<String,Integer>>(scores.entrySet());
		sort(true);
		this.setLayout(new BorderLayout());
		this.data=init_data();
		this.add("Center",data);
		this.add("South",init_button());
	}
	
	private JPanel init_data(){
		JPanel dataPanel=new JPanel();
		dataPanel.setLayout(new GridLayout(this.scores.size()+1,2));
		dataPanel.add(getLable("��Ŀ", Color.red));
		dataPanel.add(getLable("�ɼ�", Color.red));
		for(Map.Entry<String, Integer> entry : this.scores){
			dataPanel.add(getLable(entry.getKey(), Color.black));
			dataPanel.add(getLable(String.valueOf( entry.getValue()<0?"δ��":String.valueOf(entry.getValue())), entry.getValue()>0&&entry.getValue()<60?Color.red:Color.black));

		}
		dataPanel.repaint();
		return dataPanel;
	}
	
	private JPanel init_button() {
		JPanel btnPanel=new JPanel();
		final JComboBox sortSelect=new JComboBox();
		//�������
		sortSelect.addItem("�Ӹߵ���");
		sortSelect.addItem("�ӵ͵���");
		//Ĭ��ѡ�е�һ��
		sortSelect.setSelectedIndex(0);
		sortSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sortSelect.getSelectedIndex()==0){
					sort(true);
				}else{
					sort(false);
				}
				updataCharts();
			}
		});
		btnPanel.add(sortSelect);
		//��ʼ�����ذ�ť
		JButton back=new JButton("����");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.backPanel();
			}
		});
		btnPanel.add(back);
		return btnPanel;
	}
	
	private JLabel getLable(String title,Color color){
		JLabel lable=new JLabel(title,JLabel.CENTER);
		lable.setForeground(color);
		return lable;
	}
	
	private void sort(boolean bl){
		final boolean flag=bl;
		Collections.sort(this.scores,new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
				if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
		            return flag?1:-1;  
		           }else{  
		            return flag?-1:1;  
		           }  
			}
			
		});
	}
	
	private void updataCharts(){
		//���������Ϊ���ɼ�
		this.data.setVisible(false);
		//ɾ����ʾ��Ϣ��Panel
		this.remove(this.data);
		//���¹�����ʾPanel
		this.data=init_data();
		//���
		this.add("Center",this.data);
	}
}
