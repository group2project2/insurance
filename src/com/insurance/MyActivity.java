package com.insurance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_personal);	
		TextView username=(TextView)findViewById(R.id.tv_username);
		SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
		username.setText(usersp.getString("username", ""));
		
	}
	public void onClick(View v){
		switch(v.getId()){
		case R.id.btn_my_claim:			
			Intent intent=new Intent(MyActivity.this,ClaimActivity.class);
			startActivity(intent);
			finish();		
			break;
		case R.id.btn_my_insurance:
			Intent intent2=new Intent(MyActivity.this,MyInsuranceActivity.class);
			startActivity(intent2);
			finish();
			break;
        case R.id.btn_my_info:
			
			break;
        case R.id.btn_my_help:
			
			break;
        case R.id.btn_my_logout:
        	AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
			builder.setTitle("Warning").setMessage("Log out now?")
			.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
					 SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);
					 SharedPreferences.Editor userspeditor = usersp.edit(); 
					 SharedPreferences.Editor userinsurancespeditor = userinsurancesp.edit(); 				  
					 userspeditor.clear();
					 userspeditor.commit();
					 userinsurancespeditor.clear();
					 userinsurancespeditor.commit();
					 Intent intent=new Intent(MyActivity.this,MainActivity.class);	
						startActivity(intent);
						finish();
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//dismiss();
				}
			}).show();
        	 
			break;
		}
	}
}


