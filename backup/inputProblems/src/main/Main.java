package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import util.HttpUtil;

public class Main {

	/**
	 *	username:chenhongjun
	 *	password:b00000000
	 *	userloginrole:3
	 */
	
	//post data�̶�ֵ
	private static final String base="id=&lessonid=&inpopup=&versioning=&" +
			"movecontext=&cmid=0&courseid=0&returnurl=%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall&" +
			"scrollpos=0&appendqnumstring=0&edit=0&" +
					"mycourses=38&" +
					"questiontext[format]=1&resolvequestions[format]=1&submit_returntothis=�������";
	
	//�ύurl
	private static final String uploadPath="http://dccsat.cduestc.cn/question/question.php";
	
	/**
	 *  returnurl:/mod/quiz/view.php?mod=quiz&action=viewall
	 *	qtype:multichoice
	 */
	static{
		//��¼��ȡ��cookie
		HttpUtil.sendPost("http://dccsat.cduestc.cn/login/index.php", "username=chenhongjun&password=b00000000&userloginrole=3",true);
		//�õ�sesskey
		HttpUtil.sendGet("http://dccsat.cduestc.cn/index.php", null,true);
	}
	public static void main(String[] args) {
		//upload("C:/Users/xwjdsh/Desktop/���/�ϴ���Ŀ/thread.txt");
		//System.out.println(HttpUtil.sendGet("http://dccsat.cduestc.cn/question/question.php","returnurl=%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall&qtype=multichoice"));
		//HttpUtil.sendPost("http://dccsat.cduestc.cn/question/question.php", test(), false);
		//HttpUtil.sendGet("http://dccsat.cduestc.cn/mod/quiz/question_bank.php", null, false);
	}
	
	private static void  upload(String filepath){
		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(filepath)));
			String line=null;
			List<String> postData=new ArrayList<String>();
			boolean flag=false;
			while((line=reader.readLine())!=null){
				line=line.trim();
				if(line.length()>0){
					if(line.startsWith("---")){
						if(postData.size()>0){
							//�ύ��Ŀ
							HttpUtil.sendPost(uploadPath, getPostData(postData), false);
							System.out.println("�ϴ��ɹ���");
							//Thread.sleep(1000);
							//���post data
							postData.clear();
						}
						flag=true;
					}else if(flag) postData.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ת���õ�post data��
	private static String getPostData(List<String> postData) {
		StringBuffer sb=new StringBuffer(base);
		sb.append("&sesskey="+HttpUtil.sesskey);
		sb.append("&knowledgepoint="+postData.get(0));
		sb.append("&choicequestiontype="+postData.get(1));
		sb.append("&difficulty="+postData.get(2));
		sb.append("&questiontext[text]=<p class='MsoNormal'>"+postData.get(3)+"</p>");
		if(postData.size()==6){
			//�����ж���
			sb.append("&qtype=truefalse");
			sb.append("&_qf__qtype_truefalse_edit_form=1");
			sb.append("&correctanswer="+postData.get(4).trim());
			sb.append("&resolvequestions[text]="+postData.get(5));
		}else if(postData.size()==10){
			//����ѡ����
			sb.append("&defaultmark=1&noanswers=4");
			sb.append("&answer[0][format]=1&answer[1][format]=1&answer[2][format]=1&answer[3][format]=1");
			sb.append("&qtype=multichoice");
			sb.append("&_qf__qtype_multichoice_edit_form=1");
			sb.append("&answer[0][text]=<p class='MsoNormal'>"+postData.get(4)+"</p>");
			sb.append("&answer[1][text]=<p class='MsoNormal'>"+postData.get(5)+"</p>");
			sb.append("&answer[2][text]=<p class='MsoNormal'>"+postData.get(6)+"</p>");
			sb.append("&answer[3][text]=<p class='MsoNormal'>"+postData.get(7)+"</p>");
			List<String> answers=spitString(postData.get(8));
			int tmp=answers.size()>1?0:1;
			sb.append("&single="+tmp);
			sb.append("&shuffleanswers="+tmp);
			sb.append("&choicetype="+tmp);
			if(tmp==1){
				sb.append("&choice_answer="+(Integer.parseInt(answers.get(0))-1));
			}else{
				for(String answer : answers){
					int an=Integer.parseInt(answer)-1;
					sb.append("&correct_answer["+an+"]="+an);
				}
			}
			sb.append("&resolvequestions[text]="+postData.get(9));
		}
		return sb.toString();
	}
	
	//�ָ��ַ�������һ�������ո�ָ�
	private static List<String> spitString(String str){
		String[] tmp=str.split("\\s+");
		List<String> list=new ArrayList<String>();
		for(String s : tmp){
			if(s.trim().length()!=0){
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * defaultmark:1
		single:0
		shuffleanswers:0
		choicetype:0
		noanswers:4
		id:
		lessonid:
		qtype:multichoice
		inpopup:
		versioning:
		movecontext:
		cmid:0
		courseid:0
		returnurl:/mod/quiz/view.php?mod=quiz&action=viewall
		scrollpos:0
		appendqnumstring:0
		edit:0
		sesskey:DnJSeNuEyi
		_qf__qtype_multichoice_edit_form:1
	 */
	
	/**
	 * mycourses:38
		knowledgepoint:104
		choicequestiontype:multiplechoice
		difficulty:1
		usepermiss:0
		questiontext[text]:<p class="MsoNormal">
			���й��ڼ̳е�����������ǣ���
		</p>
		questiontext[format]:1
		answer[0][text]:<p class="MsoNormal">
			һ��������������ж������
		</p>
		answer[0][format]:1
		answer[1][text]:<p class="MsoNormal">
			һ�������ͬʱ�̳��Զ������s
		</p>
		answer[1][format]:1
		answer[2][text]:<p class="MsoNormal">
			һ���ǳ��������ڼ̳�ʱ���븲�ǴӸ����м̳еĳ��󷽷�
		</p>
		answer[2][format]:1
		answer[3][text]:<p class="MsoNormal">
			�ӿ��п��Զ���ǳ��󷽷�
		</p>
		answer[3][format]:1
		correct_answer[1]:1
		correct_answer[3]:3
		resolvequestions[text]:<p>
			����������Ա��̳У�����ĸ���û�����ơ�java���ǵ��̳л��ƣ�һ����ֻ����һ��ֱ�Ӹ��ࡣ�ǳ�������̳г�����ʱ������ʵ�ָ����еĳ��󷽷���java�Ľӿ���ֻ�ܶ�����󷽷���
		</p>
		resolvequestions[format]:1
		submitbutton:����
		
		
		/mod/quiz/view.php?mod=quiz&action=viewall
		%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall
	 */
//	public static String test(){
//		String postData="defaultmark=1&single=1&shuffleanswers=1&choicetype=1&" +
//				"noanswers=4&id=&lessonid=&qtype=multichoice&inpopup=&versioning=&"+
//				"movecontext=&cmid=0&courseid=0&returnurl=%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall&"+
//				"scrollpos=0&appendqnumstring=0&edit=0&sesskey="+HttpUtil.sesskey+"&_qf__qtype_multichoice_edit_form=1&"+
//				"mycourses=38&knowledgepoint=111&choicequestiontype=singlechoice&difficulty=1&"+
//				"usepermiss=0&questiontext[text]=<p class='MsoNormal'>����( )����������������jdbc��������</p>&" +
//				"questiontext[format]=1&answer[0][text]=<p class='MsoNormal'>��java.sql.DriverManager��getDriver����</p>&" +
//				"answer[0][format]=1&answer[1][text]=<p class='MsoNormal'>��java.sql.DriverManager��getDrivers����</p>&answer[1][format]=1&" +
//				"answer[2][text]=<p class='MsoNormal'>��java.sql.Driver��connect����</p>&answer[2][format]=1&" +
//				"answer[3][text]=<p class='MsoNormal'>��java.lang.Class��forName()����</p>&answer[3][format]=1&" +
//				"choice_answer=3&resolvequestions[text]=jdbc������������Class.forName()��DriverManager.registerDriver()������&resolvequestions[format]=1&submit_returntothis=�������";
//		return postData;
//	}
	
	
}
