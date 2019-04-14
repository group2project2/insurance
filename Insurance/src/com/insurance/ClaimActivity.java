package com.insurance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimActivity extends Activity{
	String selectedinsuranceid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_my_claim);
		
		//back
				Button back=(Button)findViewById(R.id.btn_back);
				back.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						Intent intent=new Intent(ClaimActivity.this,MyActivity.class);	
						startActivity(intent);
						finish();
					}
				});
				
		//insuranceselect spinner
		final Spinner insid= (Spinner) findViewById(R.id.spinner_claiminsuranceid); 
		SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);
		String insuranceid=userinsurancesp.getString("insurances", "");
		List<String> insuranceidlist= new ArrayList<String>();
		if(!insuranceid.equals("")) {
		String[] insids=insuranceid.split("/");
		
		for(int i=0;i<insids.length;i++) {
			String[] insdetail=userinsurancesp.getString(insids[i], null).split("/");
			insuranceidlist.add(insids[i]+">"+insdetail[0]);
			

		}
		}
		ArrayAdapter<String> myadaptor=new ArrayAdapter<String>(ClaimActivity.this,
				R.layout.activity_spinneritem, insuranceidlist);
		insid.setAdapter(myadaptor);
		insid.setOnItemSelectedListener ( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {	
				String[] iid=insid.getSelectedItem().toString().split(">");
				selectedinsuranceid = iid[0];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				String[] iid=insid.getSelectedItem().toString().split(">");
				selectedinsuranceid = iid[0];
				
			}});
		//reason
		final EditText reason=(EditText)findViewById(R.id.et_insurance_detail_start); 
		
		//submit claim
		Button submit=(Button)findViewById(R.id.btn_claim);
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				jsonHelper lg=new jsonHelper();     
				if(!reason.getText().toString().equals("")) {
				lg.claim(ClaimActivity.this,selectedinsuranceid,reason.getText().toString());
				}else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ClaimActivity.this);
					builder.setTitle("Warning").setMessage("Reason cannot be empty.")
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							//dismiss();
						}
					}).show();
				}
			}
		});
	}

}
