package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sendURL.HTTPUtil;
import ui.LoginPanel;
import ui.MainFrame;

public class QuaryLogic {
	
	private static final String loginPath="http://newjw.cduestc.cn/loginAction.do";
	
	private static final String quaryPath="http://newjw.cduestc.cn/bxqcjcxAction.do";
	
	private final Map<String,Integer> scores=new HashMap<String, Integer>();
	
	private MainFrame frame;
	
	private LoginPanel panel;

	public QuaryLogic(MainFrame frame, LoginPanel panel) {
		this.frame = frame;
		this.panel = panel;
	}

	public void login(String id, String pass) {
		String result = HTTPUtil.sendPost(loginPath, String.format("zjh=%s&mm=%s", id,pass));
		if(result.contains("�������벻��ȷ�������������룡")){
			panel.getError().setText("������벻��ȷ");
		}else if(result.contains("�������֤���Ų����ڣ������������룡")){
			panel.getError().setText("�������֤���Ų�����");
		}else if(result.equals("�����Ƿ�����")){
			panel.getError().setText("�����Ƿ�����");
		}else{
			for(int page=1;page<5;page++){
				if(!getData(quaryPath, "totalrows=21&page="+page+"&pageSize=20")){
					break;
				}
			}
			//����
//			for(Map.Entry<String, Integer> entry : scores.entrySet()){
//				System.out.print(entry.getKey()+"------>");
//				System.out.print(entry.getValue());
//				System.out.println();
//			}
			frame.changepanel(scores);
		}
	}
	
	public Boolean getData(String url,String parms){
		try {
			String data = HTTPUtil.sendGet(quaryPath, parms);
			if(data.equals("��������")){
				panel.getError().setText("��������");
				return false;
			}else{
				Document document=DocumentHelper.parseText(data);
				return parseDocument(document);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			panel.getError().setText("����δ֪����");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private Boolean parseDocument(Document document) {
		//ȡ���ĵ���Ԫ���µ�������Ԫ�أ�����<table>��ǩ�е�<tr>��ǩ����
		List<Element> elements = document.getRootElement().elements();
		//���Ϊ�գ�û�����ݣ�����
		if(elements.size()==0)	return false;
		//��1��ʼȡ���ų�<th>��
		for(int i=1;i<elements.size();i++){
			//ȡ��<tr>��ǩ��������Ԫ�أ���<td>
			List<Element> eles = elements.get(i).elements();
			//��3�д���ǿγ���
			String subject=eles.get(2).getText();
			//��7�д���ǳɼ�
			String temp = eles.get(6).getText().trim();
			//��û�гɼ�ʱ����Ϊ-1�֣����水����������Ҫ
			Integer score=temp.equals("")?-1:Integer.parseInt(temp);
			if(scores.containsKey(subject)){
				Integer tmp=scores.get(subject);
				score=tmp>score?tmp:score;
			}
			scores.put(subject, score);
		}
		return true;
	}
}
