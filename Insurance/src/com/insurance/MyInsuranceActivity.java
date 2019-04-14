package com.insurance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyInsuranceActivity extends Activity{	
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
	private GridView gridview;
	private TextView f_title;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private List<String> list;
	String instype,userid;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_insurance);
		//back to my
		Button back=(Button)findViewById(R.id.btn_back);                     
		back.setOnClickListener(new OnClickListener(){                               
                                                                              
			@Override
			public void onClick(View arg0) {                                               
				Intent intent=new Intent(MyInsuranceActivity.this,MyActivity.class);	
				startActivity(intent);
				finish();
			}                                                                            
		});  
		//all my insurances
		SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);		
		String indetail=userinsurancesp.getString("insurances", "");
		if(!indetail.equals("")) {
		String[] insids=indetail.split("/");		
		for(int i=0;i<insids.length;i++) {
			Map<String,Object> listItem = new HashMap<String,Object>();			
			String[] insdetail=userinsurancesp.getString(insids[i], null).split("/");
			listItem.put("insuranceimg","pic");
			listItem.put("insurance",insdetail[0]);
			listItem.put("insuranceid",insids[i]);
			listItems.add(listItem);
		}
		}
		gridview=(GridView) findViewById(R.id.gv_insurance);
		SimpleAdapter myadapter=new SimpleAdapter(MyInsuranceActivity.this, listItems,
				R.layout.insurance_item,new String[]{"insuranceimg","insurance"},new int[]{R.id.img_insurance_item,R.id.tv_insurance_item});
		gridview.setAdapter(myadapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	
                AlertDialog.Builder builder= new AlertDialog.Builder(MyInsuranceActivity.this);
                View dialogView = LayoutInflater.from(MyInsuranceActivity.this).inflate(R.layout.insurance_detail,null);
                String insid=listItems.get(arg2).get("insuranceid").toString();
                builder.setTitle(insid).setView(dialogView);
                TextView type=(TextView) dialogView.findViewById(R.id.et_insurance_detail_type);
                TextView id=(TextView) dialogView.findViewById(R.id.et_insurance_detail_number);
                TextView sdate=(TextView) dialogView.findViewById(R.id.et_insurance_detail_start);
                TextView edate=(TextView) dialogView.findViewById(R.id.et_insurance_detail_end);
                SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);
                String[] insdetail=userinsurancesp.getString(insid, null).split("/");
                type.setText(insdetail[0]);
                id.setText(insid);
                sdate.setText(insdetail[1]);
                edate.setText(insdetail[2]);
                Button back=(Button) dialogView.findViewById(R.id.back);
                back.setOnClickListener(new OnClickListener(){                               
                    
        			@Override
        			public void onClick(View arg0) {                                               
        				                             
        			}                                                                            
        		});  
                Dialog  dialog=builder.create();
        		dialog.show();
        		dialog.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT,800);
            }
        });
		//customerid
		SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
		userid=usersp.getString("useraccount", null);
		//intype spinner
				spinner=(Spinner) findViewById(R.id.spinner_insurance);
				spinner.setOnItemSelectedListener ( new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {				
						instype = spinner.getSelectedItem().toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						instype=spinner.getSelectedItem().toString();
						
					}});
		
		//startdate select
				final Button startdatebtn=(Button)findViewById(R.id.btn_insurancestartdate);
				startdatebtn.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						//need test
						final Calendar calendar = Calendar.getInstance();
						 DatePickerDialog dialog = new DatePickerDialog(MyInsuranceActivity.this,
					                new DatePickerDialog.OnDateSetListener() {
					                    @Override
					                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					                    	calendar.set(Calendar.YEAR, year);
					                        calendar.set(Calendar.MONTH, month);
					                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					                        startdatebtn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
					                    }
					                },
					                calendar.get(Calendar.YEAR),
					                calendar.get(Calendar.MONTH),
					                calendar.get(Calendar.DAY_OF_MONTH));
						 DatePicker datePicker = dialog.getDatePicker();
						 Date taday = Calendar.getInstance().getTime();
						 try {
						     datePicker.setMinDate(taday.getTime());
						 } catch (Exception e) {

						 }
						
					        dialog.show();				
						
				}});
		//end date
				final Button enddatebtn=(Button)findViewById(R.id.btn_insuranceenddate);
				enddatebtn.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						//need test
						final Calendar calendar = Calendar.getInstance();
						 DatePickerDialog dialog = new DatePickerDialog(MyInsuranceActivity.this,
					                new DatePickerDialog.OnDateSetListener() {
					                    @Override
					                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					                    	calendar.set(Calendar.YEAR, year);
					                        calendar.set(Calendar.MONTH, month);
					                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					                        enddatebtn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
					                    }
					                },
					                calendar.get(Calendar.YEAR),
					                calendar.get(Calendar.MONTH),
					                calendar.get(Calendar.DAY_OF_MONTH));
						 Calendar ca = Calendar.getInstance();
						 ca.add( Calendar. DATE, +5);
						 DatePicker datePicker = dialog.getDatePicker();
						 Date taday = ca.getTime();
						 try {
							 datePicker.setMinDate(taday.getTime());
						 } catch (Exception e) {

						 }
						
					        dialog.show();				
						
				}});
		Button addinsurance=(Button)findViewById(R.id.btn_addinsurance);                     
		addinsurance.setOnClickListener(new OnClickListener(){                               
                                                                              
			@Override
			public void onClick(View arg0) {                                               
				jsonHelper lg=new jsonHelper();                                             
				lg.addInsurance(MyInsuranceActivity.this,startdatebtn.getText().toString(),enddatebtn.getText().toString(),instype,userid);                             
			}                                                                            
		});                                                                               
			
	}
}
