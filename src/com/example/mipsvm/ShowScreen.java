package com.example.mipsvm;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipsvm.ShakeListener.OnShakeListener;

public class ShowScreen extends Activity {
	 ShakeListener mShakeListener = null; 
	 private MyApp myApp;  

		@Override
		protected void onResume() {
			super.onResume();

	         Toast.makeText(this, "Screen的onResume()被调用",
	 	            Toast.LENGTH_SHORT).show();     
			System.out.println("Screen的onResume()被调用");           /////////////////////////
		}	
		
		@Override
		protected void onRestart() {
			super.onRestart();
			Toast.makeText(this, "Screen的onRestart()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Screen的onRestart()被调用");           /////////////////////////
		}	

		@Override
		protected void onDestroy() {
			super.onDestroy();
			Toast.makeText(this, "Screen的onDestroy()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Screen的onDestroy()被调用");           /////////////////////////
		}

		@Override
		protected void onPause() {
			super.onPause();
			Toast.makeText(this, "Screen的onPause()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Screen的onPause()被调用");           /////////////////////////
		}
		
		@Override
		protected void onStop() {
			super.onStop();
			Toast.makeText(this, "Screen的onStop()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Screen的onStop()被调用");           /////////////////////////
		}
		
		@Override
		protected void onStart() {
			super.onStart();
			Toast.makeText(this, "Screen的onStart()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Screen的onStart()被调用");           /////////////////////////
		}
		
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_screen); 
		
		Toast.makeText(this, "Screen的onCreate()被调用",
                Toast.LENGTH_SHORT).show();                 
		System.out.println("Screen的onCreate()被调用");           /////////////////////////
		
		myApp = ((MyApp)getApplication()); //获得自定义的应用程序MyApp 
		
		TextView screen=(TextView)findViewById(R.id.Screen);
		String screencontent="";
		for(int i=myApp.vmaddress; i<279+myApp.vmaddress;i++){
			screencontent+=myApp.memory[i]==0 ? " " : (char)myApp.memory[i];
		}

		screen.setText(screencontent);  
		
		mShakeListener = new ShakeListener(this);  
		mShakeListener.setOnShakeListener(new shakeLitener());  
	}

	 private class shakeLitener implements OnShakeListener{  
		  @Override  
		  public void onShake() {  
		   // TODO Auto-generated method stub 
			  Intent intent = new Intent(ShowScreen.this,MainActivity.class);
			  startActivity(intent);
		      mShakeListener.stop();  
		  }  		    
	}  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_screen, menu);
		return true;
	}
}
