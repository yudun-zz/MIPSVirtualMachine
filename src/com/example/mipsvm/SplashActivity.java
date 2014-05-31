package com.example.mipsvm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {

	 private static final int LOAD_DISPLAY_TIME = 1500;
	 

		@Override
		protected void onResume() {
			super.onResume();

	         Toast.makeText(this, "Splash的onResume()被调用",
	 	            Toast.LENGTH_SHORT).show();     
			System.out.println("Splash的onResume()被调用");           /////////////////////////
		}	
		
		@Override
		protected void onRestart() {
			super.onRestart();
			Toast.makeText(this, "Splash的onRestart()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Splash的onRestart()被调用");           /////////////////////////
		}	

		@Override
		protected void onDestroy() {
			super.onDestroy();
			Toast.makeText(this, "Splash的onDestroy()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Splash的onDestroy()被调用");           /////////////////////////
		}

		@Override
		protected void onPause() {
			super.onPause();
			Toast.makeText(this, "Splash的onPause()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Splash的onPause()被调用");           /////////////////////////
		}
		
		@Override
		protected void onStop() {
			super.onStop();
			Toast.makeText(this, "Splash的onStop()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Splash的onStop()被调用");           /////////////////////////
		}
		
		@Override
		protected void onStart() {
			super.onStart();
			Toast.makeText(this, "Splash的onStart()被调用",
		            Toast.LENGTH_SHORT).show();                 
			System.out.println("Splash的onStart()被调用");           /////////////////////////
		}
		
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setFormat(PixelFormat.RGBA_8888);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.activity_splash);
		
		Toast.makeText(this, "Splash的onCreate()被调用",
                Toast.LENGTH_SHORT).show();                 
		System.out.println("Splash的onCreate()被调用");           /////////////////////////
		
		new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, LOAD_DISPLAY_TIME); //1500 for release
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
