package com.insurance;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class HttpConnectServer {
	static JSONArray rjson;
	public static JSONArray httpconnection(int rl,JSONObject json) {
	HttpURLConnection urlConnection = null;
    int statu=0;
	

	try {
		 URL url ;
		if(rl==1) {
		  url = new URL("http://39.105.30.59:80/androidlogin.php");
		}else if(rl==2){
			url = new URL("http://39.105.30.59:80/androidregister.php");
		}else if(rl==3){
			url = new URL("http://39.105.30.59:80/androidaddinsurance.php");
		}else if(rl==5){
			url = new URL("http://39.105.30.59:80/androidoperation.php");
		}else {
			url = new URL("http://39.105.30.59:80/androidclaim.php");
		}
		 urlConnection = (HttpURLConnection) url.openConnection();
		 urlConnection.setConnectTimeout(80000);
		 urlConnection.setUseCaches(false);
		 urlConnection.setInstanceFollowRedirects(true);
		 urlConnection.setReadTimeout(80000);
		 urlConnection.setDoInput(true);
		 urlConnection.setDoOutput(true);
		 urlConnection.setRequestMethod("POST");
		 urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		 urlConnection.connect(); 
		 
		 DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());			           
            out.writeBytes(json.toString());
            out.flush();
            out.close();
            
	
		 if(urlConnection.getResponseCode()==200){//identify wether connect successfully
			 InputStream in = urlConnection.getInputStream(); 
			 
			 BufferedReader br = new BufferedReader(new InputStreamReader(in));  
			 String str = null;  
			 StringBuffer buffer = new StringBuffer();  
			 while((str = br.readLine())!=null){
				 buffer.append(str);  
			 }
			 in.close(); 
			 br.close();
			 rjson = new JSONArray(buffer.toString()); 
			 
			 


		 }else{
			 statu=4;
			
		 }
		
	} catch (Exception e) {
		e.printStackTrace();


	}finally{
		urlConnection.disconnect();//stop connection,release resource
	}
	return rjson;
	}
}
