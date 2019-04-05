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

public class AddUserDialog extends Dialog implements android.view.View.OnClickListener{
	public static EditText nick,pass,ensure,email,auth;
	private Button btn_create;
	private  onCreateListener createListener;
	public static String a,b,c,d,e;
	
	@SuppressLint("Instantiatable")
	public AddUserDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		nick=(EditText) findViewById(R.id.register_et1);
		pass=(EditText) findViewById(R.id.register_et2);
		ensure=(EditText) findViewById(R.id.register_et3);
		email=(EditText) findViewById(R.id.register_et4);
		auth=(EditText) findViewById(R.id.register_et6);
				
		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		Point size=new Point();
		d.getSize(size);
		p.height=(int) (size.y*0.7);
		getWindow().setAttributes(p);
		btn_create=(Button) findViewById(R.id.register_btn);
		
		btn_create.setOnClickListener(this);
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
			c=ensure.getText().toString();
			d=email.getText().toString();
			e=auth.getText().toString();
			
			createListener.onCreate();
			dismiss();
			break;
		}
	}
	
	
}
