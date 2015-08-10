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
		if(result.contains("您的密码不正确，请您重新输入！")){
			panel.getError().setText("你的密码不正确");
		}else if(result.contains("你输入的证件号不存在，请您重新输入！")){
			panel.getError().setText("您输入的证件号不存在");
		}else if(result.equals("请检查是否联网")){
			panel.getError().setText("请检查是否联网");
		}else{
			for(int page=1;page<5;page++){
				if(!getData(quaryPath, "totalrows=21&page="+page+"&pageSize=20")){
					break;
				}
			}
			//检验
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
			if(data.equals("请检查网络")){
				panel.getError().setText("请检查网络");
				return false;
			}else{
				Document document=DocumentHelper.parseText(data);
				return parseDocument(document);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			panel.getError().setText("发生未知错误");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private Boolean parseDocument(Document document) {
		//取得文档根元素下的所有子元素，即是<table>标签中的<tr>标签集合
		List<Element> elements = document.getRootElement().elements();
		//如果为空，没有数据，返回
		if(elements.size()==0)	return false;
		//从1开始取，排除<th>行
		for(int i=1;i<elements.size();i++){
			//取得<tr>标签的所有子元素，即<td>
			List<Element> eles = elements.get(i).elements();
			//第3列存的是课程名
			String subject=eles.get(2).getText();
			//第7列存的是成绩
			String temp = eles.get(6).getText().trim();
			//当没有成绩时，设为-1分，后面按分数排序需要
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
