package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

	//存储cookie
	private static List<String> cookies = new ArrayList<String>();
	
	//存储sesskey，提交题目时会用到
	public static String sesskey;

	public static void sendPost(String urlPath, String params, boolean first) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//第一次不自动处理重定向，第二次自动处理。不必要。
			conn.setInstanceFollowRedirects(!first);
			//conn.setRequestMethod("POST");
			if (!first) {
				//不是第一次post请求，带cookie提交
				conn.setRequestProperty("Cookie", cookies.get(1) + ";" + cookies.get(3));
//				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//				conn.setRequestProperty("Referer", "http://dccsat.cduestc.cn/question/question.php?returnurl=%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall&qtype=multichoice");
//				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//				conn.setRequestProperty("Origin", "http://dccsat.cduestc.cn");
//				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
//				conn.setRequestProperty("Cache-Control", "max-age=0");
//				conn.setRequestProperty("Connection", "keep-alive");
			}
//			PrintWriter writer=new PrintWriter(conn.getOutputStream());
//			writer.write(params);
//			writer.flush();
//			writer.close();
			//必须要指定编码，否则提交失败
			conn.getOutputStream().write(params.getBytes("utf-8"));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			//连接
			conn.connect();
			if (first){
				//如果是第一次，保存cookie
				String key = null;
				for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
					if (key.equalsIgnoreCase("set-cookie")) {
						String tmp = conn.getHeaderField(i);
						cookies.add(tmp.substring(0, tmp.indexOf(';')));
					}
				}
			}
			conn.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String sendGet(String urlPath, String params,boolean first) {
		try {
			URL url = new URL(urlPath + (params != null ? "?" + params : ""));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//带cookie提交
			conn.setRequestProperty("Cookie", cookies.get(1) + ";" + cookies.get(3));
			// conn.setRequestProperty("Referer",
			// "http://dccsat.cduestc.cn/login/index.php");
			//指定来自的地址，不必要。
			if(!first) conn.setRequestProperty("Referer", "http://dccsat.cduestc.cn/question/question.php?returnurl=%2Fmod%2Fquiz%2Fview.php%3Fmod%3Dquiz%26action%3Dviewall&qtype=multichoice");
			//必须指定编码，否则乱码
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				if(first){
					//从get请求响应中获取sesskey
					if(line.contains("http://dccsat.cduestc.cn/login/logout.php?sesskey=")){
						line=line.substring(line.indexOf('生'),line.length());
						line=line.substring(line.indexOf('?'), line.indexOf('退'));
						line=line.substring(line.indexOf('=')+1, line.length()-2);
						sesskey=line;
					}
				}
			}
			reader.close();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "连接出错";
		}

	}
}
