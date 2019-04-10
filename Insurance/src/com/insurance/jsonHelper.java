package com.insurance;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class jsonHelper extends Activity{

	public void register(Context context,String name,String password,String mobile,String address,String nationalID,String sex,String bankID) {
		try {					

			 JSONObject json = new JSONObject();					 
			 json.put("name", URLEncoder.encode(name, "UTF-8"));
			 json.put("password", URLEncoder.encode(password, "UTF-8"));
			 json.put("mobile", mobile);
			 json.put("address", address);
			 json.put("nationalID", nationalID);
			 json.put("sex", sex);
			 json.put("bankID", bankID);
			 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
			 if(jsarr.length()>1) {
				 JSONObject js=jsarr.getJSONObject(0);
				 String statu=js.getString("statu");
					 SharedPreferences usersp= context.getSharedPreferences("currentuser",0);
					 SharedPreferences.Editor userspeditor = usersp.edit(); 
					 userspeditor.putString("useraccount",jsarr.getJSONObject(0).getString("id"));  
					 userspeditor.putString("username",name);  
					 userspeditor.putString("userpw",password);  
					 userspeditor.commit();	
					 Intent intent=new Intent(context,MyActivity.class);	
					 context.startActivity(intent);
				     finish();
				
			 }else {
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
			 }
		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	
	public void log(Context context,String aswhat,String name,String password) {
		
		try {					

			 JSONObject json = new JSONObject();
			 json.put("aswhat", aswhat);
			 json.put("id", name);
			 json.put("password",password);//把数据put进json对象中
			 JSONArray jsarr=HttpConnectServer.httpconnection(1,json);
			 if(jsarr.length()==1) {
				
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
			 }else {
				 SharedPreferences usersp= context.getSharedPreferences("currentuser",0);
				 SharedPreferences userinsurancesp=context.getSharedPreferences("currentuserinsurance",0);
				 SharedPreferences.Editor userspeditor = usersp.edit(); 
				 SharedPreferences.Editor userinsurancespeditor = userinsurancesp.edit(); 				  
				 String id=jsarr.getJSONObject(1).getString("username");
				 userspeditor.putString("useraccount",id);  
				 userspeditor.putString("username",name);  
				 userspeditor.putString("userpw",password);  
				 userspeditor.commit();	
				 JSONObject ajs;
				 String ins="";
				 if(jsarr.length()>2) {
				 for(int i=2;i<jsarr.length();i++){					 
					 ajs=jsarr.getJSONObject(i);	
					 ins+=("/"+ajs.getString("insuranceid"));
					 userinsurancespeditor.putString(ajs.getString("insuranceid"),
								 ajs.getString("type")+"/"+ajs.getString("startdate")+"/"+ajs.getString("enddate"));				 
					 
                 } 
				 userinsurancespeditor.putString("insurances",ins);
				 userinsurancespeditor.commit();
				 }
				 Intent intent=new Intent(context,MyActivity.class);
				 context.startActivity(intent);				 
				 finish();
				 
			 }

		} catch (Exception e) {
			e.printStackTrace();


		}
		
			
	}
	public void claim(Context context,String insuranceid,String problem,String password) {
		
	}
	
}
