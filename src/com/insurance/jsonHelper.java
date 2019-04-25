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

public class jsonHelper{

	public void register(Context context,String name,String password,String mobile,String address,String nationalID,String sex) {
		try {					

			 JSONObject json = new JSONObject();					 
			 json.put("name", name);
			 json.put("password", password);
			 json.put("mobile", mobile);
			 json.put("address", address);
			 json.put("nationalID", nationalID);
			 json.put("sex", sex);
			 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
			 if(jsarr.length()==1) {
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
				 
			 }else {
				 SharedPreferences usersp= context.getSharedPreferences("currentuser",0);
				 SharedPreferences.Editor userspeditor = usersp.edit(); 
				 userspeditor.putString("useraccount",jsarr.getJSONObject(1).getString("insid"));  
				 userspeditor.putString("username",name);  
				 userspeditor.putString("userpw",password);  
				 userspeditor.commit();	
				 Intent intent=new Intent(context,MyActivity.class);
				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 context.startActivity(intent);
			 }
		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	
	public void log(Context context,String aswhat,String name,String password) {
		
		try {					

			 JSONObject json = new JSONObject();
			 json.put("aswhat", aswhat);
			 json.put("name", name);
			 json.put("password",password);
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
				 String id=jsarr.getJSONObject(1).getString("userid");
				 userspeditor.putString("useraccount",id);  
				 userspeditor.putString("username",name);  
				 userspeditor.putString("userpw",password);  
				 if(aswhat.equals("customer")) {
					 userspeditor.putString("mobile", jsarr.getJSONObject(1).getString("mobile"));
					 userspeditor.putString("address", jsarr.getJSONObject(1).getString("address"));
					 userspeditor.putString("nationalID", jsarr.getJSONObject(1).getString("nationalID"));
					 userspeditor.putString("sex", jsarr.getJSONObject(1).getString("sex"));
					 }else {
						 userspeditor.putString("mobile", jsarr.getJSONObject(1).getString("mobile"));
					 }
				 userspeditor.commit();	
				 JSONObject ajs;
				 String ins="";
				 if(jsarr.length()>2) {
				 for(int i=2;i<jsarr.length();i++){					 
					 ajs=jsarr.getJSONObject(i);	
					 ins+=(" "+ajs.getString("insuranceid"));
					 userinsurancespeditor.putString(ajs.getString("insuranceid"),
								 ajs.getString("type")+"|"+ajs.getString("startdate")+"|"+ajs.getString("enddate"));				 
					 
                 } 
				 userinsurancespeditor.putString("insurances",ins);
				 userinsurancespeditor.commit();
				 }
				 Intent intent;
				 if(aswhat.equals("customer")) {
				 intent=new Intent(context,MyActivity.class);
				 }else {
					 intent=new Intent(context,StaffMainActivity.class);
				 }
				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 context.startActivity(intent);	
				 
				 
				 
				 
				 
			 }

		} catch (Exception e) {
			e.printStackTrace();


		}
		
			
	}
	//SUBMIT CLAIM**********************
	public void claim(Context context,String insuranceid,String problem) {
		try {					
  
			 JSONObject json = new JSONObject();
			 json.put("action", "submitclaim");
			 json.put("insuranceid", insuranceid);
			 json.put("problem", problem);
			 JSONArray jsarr=HttpConnectServer.httpconnection(4,json);
			 if(jsarr.length()<2) {
				
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
			 }else {
				 SharedPreferences userclaimsp=context.getSharedPreferences("currentuserclaims",0);
				 SharedPreferences.Editor userclaimeditor = userclaimsp.edit(); 	
				 JSONObject ajs;			 
				 ajs=jsarr.getJSONObject(1);
				 String last=userclaimsp.getString("claims", "");
				 String ins="/"+ajs.getString("claimid");				 
				 userclaimeditor.putString("claims", last+ins);
				 userclaimeditor.commit();	
				 SharedPreferences temporaryinfo =context.getSharedPreferences("tempinfo",0);
				 SharedPreferences.Editor tempeditor = temporaryinfo.edit(); 	
				 tempeditor.putString("addclaimid", ajs.getString("claimid"));
				 tempeditor.commit();
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(1).getString("claimid"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
				 
			 }

		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	//setlement of claim
			public void actionclaim(Context context,String action,String solution,String staffid,String claimid) {
				try {					

					 JSONObject json = new JSONObject();
					 json.put("action", action);
					 json.put("solution", solution);
					 json.put("staffid", staffid);
					 json.put("claimid", claimid);
					 JSONArray jsarr=HttpConnectServer.httpconnection(4,json);
					 Looper.prepare();
					 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
					 Looper.loop();

				} catch (Exception e) {
					e.printStackTrace();


				}
			}
	//ADD NEW INSURANCE***************************
		public void addInsurance(Context context,String startdate,String enddate,String instype,String customerID) {
			
			try {					

				 JSONObject json = new JSONObject();
				 json.put("startdate", startdate);
				 json.put("enddate", enddate);
				 json.put("instype", instype);
				 json.put("customerID", customerID);
				 JSONArray jsarr=HttpConnectServer.httpconnection(3,json);
				 if(jsarr.length()==1) {
					 Looper.prepare();
					 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
					 Looper.loop();
				 }else {
					 SharedPreferences userinsurancesp=context.getSharedPreferences("currentuserinsurance",0);
					 SharedPreferences.Editor userinsurancespeditor = userinsurancesp.edit(); 	
					 JSONObject ajs;
					 ajs=jsarr.getJSONObject(1);	
					 String ins="/"+ajs.getString("insuranceid");
					 userinsurancespeditor.putString(ajs.getString("insuranceid"),
							 instype+"/"+startdate+"/"+enddate);
					 userinsurancespeditor.putString("insurances",userinsurancesp.getString("insuranceid", "").toString()+ins);
					 userinsurancespeditor.commit();
					 SharedPreferences temporaryinfo =context.getSharedPreferences("tempinfo",0);
					 SharedPreferences.Editor tempeditor = temporaryinfo.edit(); 	
					 tempeditor.putString("addinsid", ajs.getString("insuranceid"));
					 tempeditor.commit();
				 }

			} catch (Exception e) {
				e.printStackTrace();


			}
			
		}
	//get user allclaims
	public void getAllClaim(Context context,String action,String userid) {
		try {					

			 JSONObject json = new JSONObject();
			 json.put("action", action);
			 json.put("userid", userid);
			 JSONArray jsarr=HttpConnectServer.httpconnection(5,json);
			 
			 if(jsarr.length()==1) {
					
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
			 }else {
				 JSONObject ajs;
			 SharedPreferences userclaimsp=context.getSharedPreferences("currentuserclaims",0);
			 SharedPreferences.Editor userclaimeditor = userclaimsp.edit(); 
			 String cs="";
			 for(int i=1;i<jsarr.length();i++) {
				 ajs=jsarr.getJSONObject(i);
				 cs+="/"+ajs.getString("claimid");
				 userclaimeditor.putString(ajs.getString("claimid"), ajs.getString("forinstype"));
			 }
			 userclaimeditor.putString("claims", cs);
				 userclaimeditor.commit();	
			 }
		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	
	//get claim detail
	public void getClaimDetail(Context context,String claimid) {
		try {					

			 JSONObject json = new JSONObject();
			 json.put("action", "claimdetail");
			 json.put("claimid", claimid);
			 JSONArray jsarr=HttpConnectServer.httpconnection(5,json);
			 JSONObject ajs=jsarr.getJSONObject(0);
				 SharedPreferences claimdetail=context.getSharedPreferences("currentclaimdetail",0);
				 SharedPreferences.Editor claimdetaileditor = claimdetail.edit(); 	
				 claimdetaileditor.putString("calimid", claimid);
				 claimdetaileditor.putString("forinsid", ajs.getString("forinsid"));
				 claimdetaileditor.putString("forinstype", ajs.getString("forinstype"));
				 claimdetaileditor.putString("statu", ajs.getString("statu"));
				 claimdetaileditor.putString("staff", ajs.getString("staff"));
				 claimdetaileditor.putString("date", ajs.getString("date"));
				 claimdetaileditor.putString("problem", ajs.getString("problem"));
				 claimdetaileditor.putString("solution", ajs.getString("solution"));
				 claimdetaileditor.commit();	

		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	//staff get claim
	public void getStaffAllClaims(Context context) {
		try {					

			 JSONObject json = new JSONObject();
			 json.put("action", "staffallclaim");
			 JSONArray jsarr=HttpConnectServer.httpconnection(5,json);
			 if(jsarr.length()>1) {
				 SharedPreferences staffallclaim=context.getSharedPreferences("staffallclaims",0);
				 SharedPreferences.Editor staffclaimeditor = staffallclaim.edit(); 
				 JSONObject ajs;
				 String ins="";
				 for(int i=1;i<jsarr.length();i++) {
					 ajs=jsarr.getJSONObject(i);
					 staffclaimeditor.putString(ajs.getString("claimid"),  ajs.getString("forinstype"));
					  ins="/"+ajs.getString("claimid");
				 }
				 staffclaimeditor.putString("allclaims", ins);
				 staffclaimeditor.commit();
			 }

		} catch (Exception e) {
			e.printStackTrace();


		}
	}
	//staff get workclaim
		public void getStaffWorkClaims(Context context,String staffid) {
			try {					

				 JSONObject json = new JSONObject();
				 json.put("action", "staffworkclaim");
				 json.put("staffid", staffid);
				 JSONArray jsarr=HttpConnectServer.httpconnection(5,json);
				 if(jsarr.length()>1) {
					 SharedPreferences staffallclaim=context.getSharedPreferences("staffworkclaims",0);
					 SharedPreferences.Editor staffclaimeditor = staffallclaim.edit(); 
					 JSONObject ajs;
					 String ins="";
					 for(int i=1;i<jsarr.length();i++) {
						 ajs=jsarr.getJSONObject(i);
						 staffclaimeditor.putString(ajs.getString("claimid"),  ajs.getString("forinstype"));
						  ins="/"+ajs.getString("claimid");
					 }
					 staffclaimeditor.putString("allclaims", ins);
					 staffclaimeditor.commit();
				 }

			} catch (Exception e) {
				e.printStackTrace();


			}
		}
		
		//get personal info
		public void getPersonalInfo(Context context,String id,String as) {
			try {					

				 JSONObject json = new JSONObject();
				 json.put("action", "getpersonalinfo");
				 json.put("as", as);
				 json.put("id", id);
				 JSONArray jsarr=HttpConnectServer.httpconnection(5,json);
				 if(jsarr.length()>1) {
					 SharedPreferences usersp= context.getSharedPreferences("currentuser",0);
					 SharedPreferences.Editor userspeditor = usersp.edit(); 
					 if(as.equals("customer")) {
					 userspeditor.putString("mobile", jsarr.getJSONObject(1).getString("mobile"));
					 userspeditor.putString("address", jsarr.getJSONObject(1).getString("address"));
					 userspeditor.putString("nationalID", jsarr.getJSONObject(1).getString("nationalID"));
					 userspeditor.putString("sex", jsarr.getJSONObject(1).getString("sex"));
					 userspeditor.putString("bankcardID", jsarr.getJSONObject(1).getString("bankcardID"));
					 }else {
						 userspeditor.putString("mobile", jsarr.getJSONObject(1).getString("mobile"));
					 }
					 userspeditor.commit();
				 }else {
					 Looper.prepare();
					 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
					 Looper.loop();
				 }

			} catch (Exception e) {
				e.printStackTrace();


			}
		}
		//change personalinfo
		public void changePersonalInfo(Context context,String action,String name,String password,String mobile,String address,String nationalID,String sex) {
			try {					

				 JSONObject json = new JSONObject();	
				 json.put("action", action);
				 json.put("name", name);
				 json.put("password", password);
				 json.put("mobile", mobile);
				 json.put("address", address);
				 json.put("nationalID", nationalID);
				 json.put("sex", sex);
				 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
				 Looper.prepare();
				 Toast.makeText(context,jsarr.getJSONObject(0).getString("statu"),Toast.LENGTH_SHORT).show();
				 Looper.loop();
			} catch (Exception e) {
				e.printStackTrace();


			}
		}
		
}
