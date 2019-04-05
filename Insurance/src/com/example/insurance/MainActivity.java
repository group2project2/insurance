package com.example.insurance;

import com.example.insurance.AddUserDialog;
import com.example.insurance.MainActivity;
import com.example.insurance.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void showDialog(){
		final AddUserDialog dialog=new AddUserDialog(MainActivity.this);
		dialog.show();
		dialog.setTitle("Create an account!");
		dialog.setCreate(new AddUserDialog.onCreateListener() {
			@Override
			public void onCreate() {
				if(AddUserDialog.b.equals(AddUserDialog.c)){
					Toast.makeText(MainActivity.this, "Sign up successfully!", Toast.LENGTH_LONG).show();
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Two passwords are not same,please check and enter again.")
					.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							//dismiss();
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							dialog.dismiss();
							//dismiss();
						}
					}).show();
				}
				
			}
		});
	}
	
	public void showDialog2(){
		final LoginDialog dialog=new LoginDialog(MainActivity.this);
		dialog.show();
		dialog.setTitle("Log in");
		dialog.setCreate(new LoginDialog.onCreateListener() {
			@Override
			public void onCreate() {
				if(true){
					Toast.makeText(MainActivity.this, "Log in successfully!", Toast.LENGTH_LONG).show();
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Two passwords are not same,please check and enter again.")
					.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							//dismiss();
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							dialog.dismiss();
							//dismiss();
						}
					}).show();
				}
				
			}
		});
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.main_btn2:
			//if(true){
				//showDialog2();
				//Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(MainActivity.this,MyActivity.class);
				startActivity(intent);
				finish();
			//}
			//else{
				//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				//builder.setMessage("Nickname or password is wrong.");
				//builder.show();
			//}
			break;
		case R.id.main_btn1:
			showDialog();
			break;
		}
	}
}
