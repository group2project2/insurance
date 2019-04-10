package com.insurance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ClaimActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_my_claim);
		
		//back
				/*Button back=(Button)findViewById(R.id.back);
				back.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						Intent intent=new Intent(ClaimActivity.this,MyActivity.class);	
						startActivity(intent);
						finish();
					}
				});*/
				
		//insuranceselect spinner
		/*Spinner insid= (Spinner) findViewById(R.id.insuranceid); 
		SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);					
		String[] insids=userinsurancesp.getString("insurances", null).split("/");
		List<String> insuranceidlist= new ArrayList<String>();
		for(int i=0;i<insids.length;i++) {
			String[] insdetail=userinsurancesp.getString(insids[i], null).split("/");
			insuranceidlist.add(insids[i]+">"+insdetail[0]);
			

		}
		ArrayAdapter<String> myadaptor=new ArrayAdapter<String>(ClaimActivity.this,
				R.layout.activity_spinneritem, insuranceidlist);
		insid.setAdapter(myadaptor);
		*/
		//submit claim
		/*Button submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {	
				try {		
					



					 JSONObject json = new JSONObject();					 
					 
					 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
					 if(jsarr.length()>0) {
						
						
					 }else {
						 Looper.prepare();
						 Toast.makeText(ClaimActivity.this,"unsuccessful",Toast.LENGTH_SHORT).show();
						 Looper.loop();
					 }
				} catch (Exception e) {
					e.printStackTrace();


				}
				
				}}).start();;
			}
		});*/
	}

}
