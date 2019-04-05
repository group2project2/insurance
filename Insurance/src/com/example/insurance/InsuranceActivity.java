package com.example.insurance;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

public class InsuranceActivity extends Activity{
	
	//private MySimpleAdapter adapter;
	private GridView gridview;
	private TextView f_title;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private List<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_insurance);
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
