package com.insurance;

import com.util.CustomVideoView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	String gender;
	private CustomVideoView videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}	
	
	public void LoginDialog() {
		AlertDialog.Builder addDialog = new AlertDialog.Builder(MainActivity.this);		
		View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.login,null);
		addDialog.setTitle("Log in");
		addDialog.setView(dialogView);
		final EditText nick=(EditText) dialogView.findViewById(R.id.login_et1);
		final EditText pass=(EditText) dialogView.findViewById(R.id.login_et2);
		final jsonHelper lg=new jsonHelper();
		final Button btn_cus=(Button) dialogView.findViewById(R.id.login_btn1);
		btn_cus.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(nick.getText().toString().equals("")||pass.getText().toString().equals("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Warning").setMessage("Id/password cannot be empty.")
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
				        lg.log(MainActivity.this,"customer", nick.getText().toString(), pass.getText().toString());				        
					}}).start();;
				
				
			}}
		});
		
		final Button btn_sta=(Button) dialogView.findViewById(R.id.login_btn2);		
		btn_sta.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(nick.getText().toString().equals("")||pass.getText().toString().equals("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Warning").setMessage("Id/password cannot be empty.")
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
				        lg.log(MainActivity.this,"staff", nick.getText().toString(), pass.getText().toString());				        
					}}).start();;
				}
				
				
			}
		});
		Dialog  dialog=addDialog.create();
		dialog.show();
		dialog.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT,700);
	}
	//register dialog
	public void RegisterDialog() {
		AlertDialog.Builder addDialog = new AlertDialog.Builder(MainActivity.this);		
		View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.register,null);
		addDialog.setTitle("Create an account");
		addDialog.setView(dialogView);
		final EditText nick=(EditText) dialogView.findViewById(R.id.register_et1);
		final EditText pass=(EditText) dialogView.findViewById(R.id.register_et2);
		final EditText ensure=(EditText) dialogView.findViewById(R.id.register_et3);
		final EditText mobile=(EditText) dialogView.findViewById(R.id.register_et4);
		final EditText address=(EditText) dialogView.findViewById(R.id.register_et6);
		final EditText nationalid=(EditText) dialogView.findViewById(R.id.register_et7);	
		
		
		RadioGroup genderradio=(RadioGroup)dialogView.findViewById(R.id.genderradioGroup);
		genderradio.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup rg,int checkedId) {
				
				switch(checkedId){
				case R.id.gender1:
					gender="female";
				case R.id.gender2:
					gender="male";
				}
			}
		});
		final jsonHelper lg=new jsonHelper();
		final Button btn_create=(Button) dialogView.findViewById(R.id.register_btn);
		btn_create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(pass.getText().toString().equals("")||ensure.getText().toString().equals("")||nick.getText().toString().equals("")||mobile.getText().toString().equals("")||address.getText().toString().equals("")||nationalid.getText().toString().equals("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Warning").setMessage("Each infomation cannot be empty.")
					.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							//dismiss();
						}
					}).show();
				}
				else if(pass.getText().toString().equals(ensure.getText().toString())) {
				new Thread(new Runnable() {
					@Override
					public void run() {	
				        lg.register(MainActivity.this,nick.getText().toString(), pass.getText().toString(),mobile.getText().toString(),address.getText().toString(),nationalid.getText().toString(),gender);				        
					}}).start();;
				}else {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Warning").setMessage("Two passwords are not same,please check and enter again.")
					.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							//dismiss();
						}
					}).show();
				}
				
			}
		});		
		
		Dialog  dialog=addDialog.create();
		dialog.show();
		dialog.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT,800);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.main_btn2:			
				LoginDialog();				
			break;
		case R.id.main_btn1:
			RegisterDialog();
			break;
		}
	}
	public void initView(){
		videoview = (CustomVideoView) findViewById(R.id.videoview);
		videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainvideo2));
		videoview.start();
		 videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
	            @Override
	            public void onCompletion(MediaPlayer mediaPlayer) {
	                videoview.start();
	            }
	        });

	

	}
}
