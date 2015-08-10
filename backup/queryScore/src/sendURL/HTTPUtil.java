package sendURL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtil {
	
	/**
	 * 保存请求的cookie
	 */
	private static String cookie;

	/**
	 * 发送post请求
	 * @param urlPath 请求的url
	 * @param params  postData
	 */
	public static String sendPost(String urlPath, String params) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(params);
			out.flush();
			out.close();
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer response=new StringBuffer();
			String line;
			while((line=reader.readLine())!=null){
				response.append(line);
				System.out.println(line);
			}
			//从响应头中取得cookie
			cookie = conn.getHeaderField("Set-Cookie");
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "连接出错";
		}
	}
	
	/**
	 * 发送get请求
	 * 应该将其写为可复用的，我这里为了简单
	 * @param urlPath 请求的url
	 * @param params  queryString
	 */
	public static String sendGet(String urlPath,String params){
		try{
			URL url=new URL(urlPath+(params!=null?"?"+params:""));
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			//带cookie访问
			conn.setRequestProperty("Cookie", cookie);
			conn.setDoOutput(true);
			conn.connect();
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			Boolean flag=false;
			Integer order=0;
			StringBuffer result=new StringBuffer();
			while((line=reader.readLine())!=null){
				line=line.trim().toLowerCase();
					if(line.contains("<table")&&order++==6||flag){
						flag=true;
						if(line.toLowerCase().startsWith("</table"))	flag=false;
						if(!line.startsWith("<thead")){
							result.append(line.contains("&bnsp")?line.replace("&nbsp;", ""):line+(flag?"\n":""));
						}
					}
			}
			reader.close();
			conn.disconnect();
			return result.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "连接出错";
		}
		
	}
}
