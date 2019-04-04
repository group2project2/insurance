package com.insurance;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {
	String name="admin";
	String password="123456";	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() 
				.detectAll().penaltyLog().build()); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//back
		Button back=(Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);	
				startActivity(intent);
				finish();
			}
		});
		MyListener listener = new MyListener(); 
		Button ascustomer=(Button)findViewById(R.id.ascustomer);
		ascustomer.setOnClickListener(listener);
		Button asemployee=(Button)findViewById(R.id.asemployee);
		asemployee.setOnClickListener(listener);
	}
	public void log(String aswhat,String id,String password) {
		
		try {					

			 JSONObject json = new JSONObject();
			 json.put("aswhat", aswhat);
			 json.put("id", URLEncoder.encode(id, "UTF-8"));
			 json.put("password", URLEncoder.encode(password, "UTF-8"));//把数据put进json对象中
			 JSONArray jsarr=HttpConnectServer.httpconnection(1,json);
			 if(jsarr.length()==1) {
				 String statu=jsarr.getJSONObject(0).getString("statu");
				 Looper.prepare();
				 Toast.makeText(LoginActivity.this,statu,Toast.LENGTH_SHORT).show();
				 Looper.loop();
			 }else {
				 SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
				 SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);
				 SharedPreferences.Editor userspeditor = usersp.edit(); 
				 SharedPreferences.Editor userinsurancespeditor = userinsurancesp.edit(); 
				  
				 String name=jsarr.getJSONObject(0).getString("username");
				 userspeditor.putString("useraccount",id);  
				 userspeditor.putString("username",name);  
				 userspeditor.putString("userpw",password);  
				 userspeditor.commit();	
				 JSONObject ajs;
				 String ins="";
				 for(int i=1;i<jsarr.length();i++){					 
					 ajs=jsarr.getJSONObject(i);	
					 ins+=("/"+ajs.getString("insuranceid"));
					 userinsurancespeditor.putString(ajs.getString("insuranceid"),
								 ajs.getString("type")+"/"+ajs.getString("startdate")+"/"+ajs.getString("enddate"));				 
					 
                 } 
				 userinsurancespeditor.putString("insurances",ins);
				 userinsurancespeditor.commit();
				 Intent intent=new Intent(LoginActivity.this,MyActivity.class);	
					startActivity(intent);
					finish();
			 }

		} catch (Exception e) {
			e.printStackTrace();


		}
			
	}
	public class MyListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {			
			switch(v.getId()) {
			 case R.id.ascustomer:
				 new Thread(new Runnable() {
						@Override
						public void run() {	
							String as="customer";				 
				 log(as,name,password);
						}}).start();;
				break;
			 case R.id.asemployee:
				 new Thread(new Runnable() {
						@Override
						public void run() {	
				 String as="employee";
				 log(as,name,password);
						}}).start();;
				 break;
			
			}					
		}
	}

	

		
	
}
