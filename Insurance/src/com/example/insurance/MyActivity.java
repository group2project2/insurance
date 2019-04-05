package com.example.insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_personal);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.btn_my_logout:
			Intent intent1=new Intent(MyActivity.this,MainActivity.class);
			startActivity(intent1);
			finish();
			break;
		case R.id.btn_my_insurance:
			Intent intent2=new Intent(MyActivity.this,InsuranceActivity.class);
			startActivity(intent2);
			finish();
		}
	}
}
