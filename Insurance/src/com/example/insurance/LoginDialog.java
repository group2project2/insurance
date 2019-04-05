package com.example.insurance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener{
	public static EditText nick,pass;
	private Button btn_create_cus;
	private Button btn_create_sta;
	private  onCreateListener createListener;
	public static String a,b;
	
	@SuppressLint("Instantiatable")
	public LoginDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		nick=(EditText) findViewById(R.id.login_et1);
		pass=(EditText) findViewById(R.id.login_et2);
				
		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		Point size=new Point();
		d.getSize(size);
		p.height=(int) (size.y*0.45);
		getWindow().setAttributes(p);
		btn_create_cus=(Button) findViewById(R.id.login_btn1);
		btn_create_sta=(Button) findViewById(R.id.login_btn2);		
		
		btn_create_cus.setOnClickListener(this);
		btn_create_sta.setOnClickListener(this);
	}
	public void setCreate(onCreateListener listener){
		this.createListener=listener;
	}
	public interface onCreateListener{
		void onCreate();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.register_btn:
			a=nick.getText().toString();
			b=pass.getText().toString();
			
			createListener.onCreate();
			dismiss();
			break;
		}
	}
	
	
}
