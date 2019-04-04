package com.insurance;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	String name="",password="",age="",mobile="",address="",nationalID="",sex="",bankID="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);	
		name="abc";		
		password="123456";
		age="21";
		mobile="12345678901";
		address="simple";
		nationalID="1234567890";
		sex="female";
		bankID="123456789";
		Button register=(Button)findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {	
				try {					

					 JSONObject json = new JSONObject();					 
					 json.put("name", URLEncoder.encode(name, "UTF-8"));
					 json.put("password", URLEncoder.encode(password, "UTF-8"));//把数据put进json对象中
					 json.put("age", age);
					 json.put("mobile", mobile);
					 json.put("address", address);
					 json.put("nationalID", nationalID);
					 json.put("sex", sex);
					 json.put("bankID", bankID);
					 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
					 if(jsarr.length()>0) {
						 JSONObject js=jsarr.getJSONObject(0);
						 String statu=js.getString("statu");						 
						 if(statu.equals("successfully")) {
							 SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
							 SharedPreferences.Editor userspeditor = usersp.edit(); 
							 userspeditor.putString("useraccount",jsarr.getJSONObject(0).getString("id"));  
							 userspeditor.putString("username",name);  
							 userspeditor.putString("userpw",password);  
							 userspeditor.commit();	
							 Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);	
						     startActivity(intent);
						     finish();
						}
					 }else {
						 Looper.prepare();
						 Toast.makeText(RegisterActivity.this,"unsuccessful",Toast.LENGTH_SHORT).show();
						 Looper.loop();
					 }
				} catch (Exception e) {
					e.printStackTrace();


				}
				
				}}).start();;
			}});
		
	}

}
