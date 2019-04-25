package com.insurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClaimActivity extends Activity{
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
	String selectedinsuranceid;
	SimpleAdapter myadapter;
	SimpleAdapter spinneradaptor;
	jsonHelper lg=new jsonHelper();
	String claimid="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_my_claim);
		SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
		final String userid=usersp.getString("useraccount", null);
		//send request
		new Thread(new Runnable() {
			@Override
			public void run() {	
		lg.getAllClaim(ClaimActivity.this,"getcustomerclaim",userid);
	}}).start();
		ListView allclaims=(ListView)findViewById(R.id.lv_claim);
		SharedPreferences userclaimsp=ClaimActivity.this.getSharedPreferences("currentuserclaims",0);
		if(!userclaimsp.getString("claims", "").equals("")) {
			String[] claims=userclaimsp.getString("claims", "").split("\\/");
			for(int i=1;i<claims.length;i++) {
				Map<String,Object> listItem = new HashMap<String,Object>();		
				String iteminsid=userclaimsp.getString(claims[i], "");
				String[] insdetail=iteminsid.split("\\/");
				listItem.put("claimid",insdetail[0]);
				listItem.put("forinsid",claims[i]);
				listItems.add(listItem);
			}
		}
		myadapter=new SimpleAdapter(ClaimActivity.this, listItems,
				R.layout.insurance_item,new String[]{"claimid","forinsid"},new int[]{R.id.claim_id,R.id.claim_forins});
		allclaims.setAdapter(myadapter);
		allclaims.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {            	         	
                AlertDialog.Builder builder= new AlertDialog.Builder(ClaimActivity.this);
                View dialogView = LayoutInflater.from(ClaimActivity.this).inflate(R.layout.claim_detail,null);
                claimid =listItems.get(arg2).get("claimid").toString();
                builder.setTitle(claimid).setView(dialogView);
                TextView id=(TextView) dialogView.findViewById(R.id.et_claim_detail_id);
                TextView forinsid=(TextView) dialogView.findViewById(R.id.et_claim_detail_forins);
                TextView statu=(TextView) dialogView.findViewById(R.id.et_claim_detail_statu);
                TextView staff=(TextView) dialogView.findViewById(R.id.et_claim_detail_staff);
                TextView date=(TextView) dialogView.findViewById(R.id.et_claim_detail_date);
                TextView problem=(TextView) dialogView.findViewById(R.id.et_claim_detail_problem);
                TextView solution=(TextView) dialogView.findViewById(R.id.et_claim_detail_solution);
                //send request
                new Thread(new Runnable() {
					@Override
					public void run() {	
                lg.getClaimDetail(ClaimActivity.this, claimid);
					}}).start();
                SharedPreferences userclaimsp= getApplicationContext().getSharedPreferences("currentclaimdetail",0);
                id.setText(claimid);
                forinsid.setText(userclaimsp.getString("forinsid", ""));
                statu.setText(userclaimsp.getString("statu", ""));
                staff.setText(userclaimsp.getString("staff", ""));
                date.setText(userclaimsp.getString("date", ""));
                problem.setText(userclaimsp.getString("problem", ""));
                solution.setText(userclaimsp.getString("solution", ""));
                
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
		List<Map<String,Object>> insuranceidlist=new ArrayList<Map<String,Object>>();
		if(!insuranceid.equals("")) {
		String[] insids=insuranceid.split(" ");
		
		for(int i=1;i<insids.length;i++) {
			Map<String,Object> listItem = new HashMap<String,Object>();
			String[] insdetail=userinsurancesp.getString(insids[i], "").split("\\|");
			listItem.put("insinfo",insids[i]+">"+insdetail[0]);
			insuranceidlist.add(listItem);

		}
		}
		
		spinneradaptor=new SimpleAdapter(ClaimActivity.this, insuranceidlist,
				R.layout.activity_spinneritem,new String[]{"insinfo"},new int[]{R.id.insid});
		insid.setAdapter(spinneradaptor);
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
				Toast.makeText(ClaimActivity.this,selectedinsuranceid,Toast.LENGTH_SHORT).show();
				
			}});
		//reason
		final EditText reason=(EditText)findViewById(R.id.et_insurance_detail_start); 
		
		//submit claim
		Button submit=(Button)findViewById(R.id.btn_claim);
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				final jsonHelper lg=new jsonHelper();     
				if(!reason.getText().toString().equals("")) {
					new Thread(new Runnable() {
						@Override
						public void run() {	
				lg.claim(ClaimActivity.this,selectedinsuranceid,reason.getText().toString());
				
				
						}}).start();
					SharedPreferences temporaryinfo =getApplicationContext().getSharedPreferences("tempinfo",0);
					String addstatu=temporaryinfo.getString("addinsid","");
					if(!addstatu.equals("")) {
			        	Map<String,Object> listItem = new HashMap<String,Object>();	
			        	listItem.put("insurance",addstatu);
						listItem.put("insuranceid",selectedinsuranceid);
						listItems.add(listItem);	
						myadapter.notifyDataSetChanged();
			        }
					
					
				}else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ClaimActivity.this);
					builder.setTitle("Warning").setMessage("Problem cannot be empty.")
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					}).show();
				}
			}
		});
	}

}
