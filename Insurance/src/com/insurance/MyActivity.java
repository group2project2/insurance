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

public class MyActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		//back **************************
		Button claim=(Button)findViewById(R.id.claim);
		claim.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MyActivity.this,ClaimActivity.class);	
				startActivity(intent);
				finish();
				
			}
		});
		
		//myinsurance ***************************
		Button myinsurance=(Button)findViewById(R.id.myinsurance);
		myinsurance.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.activity_myinsurance);
				Button add=(Button)findViewById(R.id.add);
				add.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						new Thread(new Runnable() {
							@Override
							public void run() {	
						try {					

							 JSONObject json = new JSONObject();					 
							 
							 JSONArray jsarr=HttpConnectServer.httpconnection(2,json);
							 if(jsarr.length()>0) {
								 JSONObject js=jsarr.getJSONObject(0);
								 String statu=js.getString("statu");						 
								
							 }else {
								 Looper.prepare();
								 Toast.makeText(MyActivity.this,"unsuccessful",Toast.LENGTH_SHORT).show();
								 Looper.loop();
							 }
						} catch (Exception e) {
							e.printStackTrace();


						}
						
						}}).start();;
						
					}
				});
			}
		});
		
		//myclaim ************************
		Button myclaim=(Button)findViewById(R.id.myclaim);
		myclaim.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
			}
		});
	}

}
