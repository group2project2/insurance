package com.insurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

public class StaffMainActivity extends Activity{
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
	SimpleAdapter myadapter;
	jsonHelper lg=new jsonHelper();
	String claimid="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staffmain);	
		
		//staff my
				Button btn_staffmy=(Button)findViewById(R.id.btn_staffmy);
				btn_staffmy.setOnClickListener(new OnClickListener(){                               
		            
					@Override
					public void onClick(View arg0) { 
						Intent intent=new Intent(StaffMainActivity.this,StaffMyActivity.class);	
						startActivity(intent);
						finish();
						                             
					}                                                                            
				});
		//all untreated claim
				new Thread(new Runnable() {
					@Override
					public void run() {	
				lg.getStaffAllClaims(StaffMainActivity.this);
	           }}).start();
		ListView allclaims=(ListView)findViewById(R.id.staffallclaimlv);
		SharedPreferences userclaimsp=getApplicationContext().getSharedPreferences("staffallclaims",0);
		if(!userclaimsp.getString("allclaims", "").equals("")) {
			String[] claims=userclaimsp.getString("allclaims", "").split("/");
			for(int i=1;i<claims.length;i++) {
				Map<String,Object> listItem = new HashMap<String,Object>();		
				String iteminsid=userclaimsp.getString(claims[i], "");
				listItem.put("claimid",claims[i]);
				listItem.put("forinstype",iteminsid);
				listItems.add(listItem);
			}
		}
		myadapter=new SimpleAdapter(StaffMainActivity.this, listItems,
				R.layout.insurance_item,new String[]{"claimid","forinstype"},new int[]{R.id.claim_id,R.id.claim_forins});
		allclaims.setAdapter(myadapter);
		allclaims.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {    
            	AlertDialog.Builder builder= new AlertDialog.Builder(StaffMainActivity.this);
                View dialogView = LayoutInflater.from(StaffMainActivity.this).inflate(R.layout.staff_workclaim_detail,null);
                claimid=listItems.get(arg2).get("claimid").toString();
                final int position=arg2;
                builder.setTitle(claimid).setView(dialogView);
                TextView id=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_id);
                TextView forinsid=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_forins);
                TextView forinstype=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_forinstype);
                TextView statu=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_statu);
                TextView date=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_date);
                TextView problem=(TextView) dialogView.findViewById(R.id.staffet_claim_detail_problem);
                final EditText solution=(EditText) dialogView.findViewById(R.id.et_claim_detail_solution);
                //send request
                new Thread(new Runnable() {
					@Override
					public void run() {	
                lg.getClaimDetail(StaffMainActivity.this, claimid);
					}}).start();
                SharedPreferences userclaimsp= getApplicationContext().getSharedPreferences("currentclaimdetail",0);
                id.setText(claimid);
                forinsid.setText(userclaimsp.getString("forinsid", ""));
                statu.setText(userclaimsp.getString("statu", ""));
                forinstype.setText(userclaimsp.getString("forinstype", ""));
                date.setText(userclaimsp.getString("date", ""));
                problem.setText(userclaimsp.getString("problem", ""));
                //change***************                 
               builder.setPositiveButton("Solve",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if(solution.getText().toString().equals("")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(StaffMainActivity.this);
							builder.setTitle("Warning").setMessage("Solution cannot be empty.")
							.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									//dismiss();
								}
							}).show();
						}else {
							
							new Thread(new Runnable() {
								@Override
								public void run() {	
									SharedPreferences usersp= getApplicationContext().getSharedPreferences("currentuser",0);
							lg.actionclaim(StaffMainActivity.this,"solveclaim",solution.getText().toString(),usersp.getString("useraccount", "").toString(),claimid);
								}}).start();
							listItems.remove(position);
							myadapter.notifyDataSetChanged();
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
	}
}
