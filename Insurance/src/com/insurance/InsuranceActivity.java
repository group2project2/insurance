package com.insurance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InsuranceActivity extends Activity{
	
	//private MySimpleAdapter adapter;
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
	private GridView gridview;
	private TextView f_title;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private List<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_insurance);
		
		//all my insurances
		SharedPreferences userinsurancesp= getApplicationContext().getSharedPreferences("currentuserinsurance",0);					
		String[] insids=userinsurancesp.getString("insurances", null).split("/");		
		
		for(int i=0;i<insids.length;i++) {
			Map<String,Object> listItem = new HashMap<String,Object>();			
			String[] insdetail=userinsurancesp.getString(insids[i], null).split("/");
			listItem.put("insuranceimg","pic");
			listItem.put("insurance",insdetail[0]);
			listItem.put("insuranceid",insids[i]);
			listItems.add(listItem);
		}
		gridview=(GridView) findViewById(R.id.gv_insurance);
		SimpleAdapter myadapter=new SimpleAdapter(InsuranceActivity.this, listItems,
				R.layout.insurance_item,new String[]{"insuranceimg","insurance"},new int[]{R.id.img_insurance_item,R.id.tv_insurance_item});
		gridview.setAdapter(myadapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            AlertDialog.Builder builder= new AlertDialog.Builder(InsuranceActivity.this);
            builder.setTitle(listItems.get(arg2).get("insuranceid").toString());
            }
        });
		
		//
		spinner=(Spinner) findViewById(R.id.spinner_insurance);
		list=new ArrayList<String>();
		list.add("Overseas+Only valueables");
		list.add("Overseas+All cover");
		list.add("Domestic+Only valueables");
		list.add("Domestic+All cover");
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
		spinner.setAdapter(adapter);
	}
}
