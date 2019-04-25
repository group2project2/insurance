package com.insurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class StaffMyActivity extends Activity{
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
	SimpleAdapter myadapter;
	jsonHelper lg=new jsonHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_my);	
		//btn all claims
		Button acallclaim=(Button)findViewById(R.id.btn_allcalims);
		acallclaim.setOnClickListener(new OnClickListener(){                               
            
			@Override
			public void onClick(View arg0) { 
				Intent intent=new Intent(StaffMyActivity.this,StaffMainActivity.class);	
				startActivity(intent);
				finish();
				                             
			}                                                                            
		});			
		//check and edit info
		Button info=(Button)findViewById(R.id.btn_staff_info);
		info.setOnClickListener(new OnClickListener(){                               
            
			@Override
			public void onClick(View arg0) { 
				AlertDialog.Builder builder= new AlertDialog.Builder(StaffMyActivity.this);
                View dialogView = LayoutInflater.from(StaffMyActivity.this).inflate(R.layout.staff_personalinfo,null);
                final TextView etid=(TextView)dialogView.findViewById(R.id.et_staffid);
                final EditText etname=(EditText)dialogView.findViewById(R.id.et_staffname);
                final EditText etmobile=(EditText)dialogView.findViewById(R.id.et_staffmobile);
                SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
                etid.setText(usersp.getString("useraccount", ""));
                etname.setText(usersp.getString("username", ""));
                etmobile.setText(usersp.getString("mobile", ""));                              
               builder.setPositiveButton("Edit",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						//check edittext
						if(etid.getText().toString().equals("")||etname.getText().toString().equals("")||etmobile.getText().toString().equals("")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(StaffMyActivity.this);
							builder.setTitle("Warning").setMessage("infomation cannot be empty.")
							.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									//dismiss();
								}
							}).show();
						}else {
							//confirmation to change
							AlertDialog.Builder builder = new AlertDialog.Builder(StaffMyActivity.this);
							builder.setTitle("Confirmation").setMessage("Confirm to chane?")
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									new Thread(new Runnable() {
										@Override
										public void run() {	
											SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
											lg.changePersonalInfo(StaffMyActivity.this,"changestaffinfo",etname.getText().toString(),usersp.getString("userpw", ""),etmobile.getText().toString(),"","","");
										}}).start();
								}
							})
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									
								}
							}).show();
							
							
						}
					}
				});
                builder.setNegativeButton("Back",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
                Dialog  dialog=builder.create();
        		dialog.show();
        		dialog.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT,800);
				                             
			}                                                                            
		});	
		//button logout
				Button logout=(Button)findViewById(R.id.btn_staff_logout);
				logout.setOnClickListener(new OnClickListener(){                               
		            
					@Override
					public void onClick(View arg0) { 
						AlertDialog.Builder builder = new AlertDialog.Builder(StaffMyActivity.this);
						builder.setTitle("Warning").setMessage("Log out now?")
						.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								//change*************************************
								SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
								 SharedPreferences allclaimsp=getApplicationContext().getSharedPreferences("staffallclaims",0);
								 SharedPreferences userclaimsp=getApplicationContext().getSharedPreferences("staffworkclaims",0);
								 SharedPreferences claimdetailsp= getApplicationContext().getSharedPreferences("currentclaimdetail",0);
								 SharedPreferences.Editor userspeditor = usersp.edit(); 
								 SharedPreferences.Editor allclaimspeditor = allclaimsp.edit(); 
								 SharedPreferences.Editor userclaimspeditor = userclaimsp.edit(); 
								 SharedPreferences.Editor claimdetailspeditor = claimdetailsp.edit(); 
								 userspeditor.clear();
								 userspeditor.commit();
								 allclaimspeditor.clear();
								 allclaimspeditor.commit();
								 userclaimspeditor.clear();
								 userclaimspeditor.commit();
								 claimdetailspeditor.clear();
								 claimdetailspeditor.commit();
								 Intent intent=new Intent(StaffMyActivity.this,MainActivity.class);	
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
			        	 
						
						                             
					}                                                                            
				});
	}
}

