package com.example.mipsvm;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mipsvm.ShakeListener.OnShakeListener;

public class DataView extends Activity {
	ShakeListener mShakeListener = null; 
    private MyApp myApp;  
    private EditText startadr;
    
    @Override
	protected void onResume() {
		super.onResume();

         Toast.makeText(this, "Data的onResume()被调用",
 	            Toast.LENGTH_SHORT).show();     
		System.out.println("Data的onResume()被调用");           /////////////////////////
	}	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "Data的onRestart()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onRestart()被调用");           /////////////////////////
	}	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Data的onDestroy()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onDestroy()被调用");           /////////////////////////
	}

	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "Data的onPause()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onPause()被调用");           /////////////////////////
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(this, "Data的onStop()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onStop()被调用");           /////////////////////////
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "Data的onStart()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onStart()被调用");           /////////////////////////
	}
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_view);
		
		Toast.makeText(this, "Data的onCreate()被调用",
                Toast.LENGTH_SHORT).show();                 
		System.out.println("Data的onCreate()被调用");           /////////////////////////
		
		myApp = ((MyApp)getApplication()); //获得自定义的应用程序MyApp 
		startadr=(EditText)findViewById(R.id.dataaddress);
		
		
		//**************************************设定Datalist的值 **************************************
				//绑定XML中的ListView，作为Item的容器  
			    ListView list = (ListView) findViewById(R.id.Datalistview);  
			    //生成动态数组，并且转载数据  
			    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
			     int datashowfrom;
			     if(myApp.lastdataadr%8 != 0) datashowfrom=myApp.lastdataadr - myApp.lastdataadr%8;
			     else datashowfrom=myApp.lastdataadr;
			    	for(int i=datashowfrom;i<datashowfrom+8*20;i+=8)  
				    {  
			    		String address=Integer.toHexString(i);
				    	String data[]=new String[8];
				    	
			    		while(address.length()<8){
				    		address="0"+address;
				    	}
			    		
				    	for(int j=0;j<8;j++){
				    		data[j]=Integer.toHexString(((int)myApp.memory[i+j])& 0x000000ff);
				    		while(data[j].length()<2){
				    			data[j]="0"+data[j];
				    	    }
				    		if(i+j==myApp.lastdataadr) data[j]=data[j]+"*";
				    	}
				    	
				        HashMap<String, String> map = new HashMap<String, String>();  
				        map.put("DataItemTitle", address);  
				        map.put("DataItemText1", data[0]);   
				        map.put("DataItemText2", data[1]);   
				        map.put("DataItemText3", data[2]);   
				        map.put("DataItemText4", data[3]);   
				        map.put("DataItemText5", data[4]);   
				        map.put("DataItemText6", data[5]);   
				        map.put("DataItemText7", data[6]);   
				        map.put("DataItemText8", data[7]);  				        
				        mylist.add(map);  
				    }  
		
			    //生成适配器，数组===》ListItem  
			    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
			                                                mylist,//数据来源   
			                                                R.layout.datalistitem,//ListItem的XML实现                  
			                                                //动态数组与ListItem对应的子项          
			                                                new String[] {"DataItemTitle", "DataItemText1", "DataItemText2", "DataItemText3", "DataItemText4", "DataItemText5", "DataItemText6", "DataItemText7", "DataItemText8"},   	                                                  
			                                                //ListItem的XML文件里面的两个TextView ID  
			                                                new int[] {R.id.DataItemTitle,R.id.DataItemText1,R.id.DataItemText2,R.id.DataItemText3,R.id.DataItemText4,R.id.DataItemText5,R.id.DataItemText6,R.id.DataItemText7,R.id.DataItemText8});  
			    //添加并且显示  
			    list.setAdapter(mSchedule);  
			  //*********************************************end***************************************/
		
		mShakeListener = new ShakeListener(this);  
		mShakeListener.setOnShakeListener(new shakeLitener());  
	}

	private class shakeLitener implements OnShakeListener{  
		  @Override  
		  public void onShake() {  
		   // TODO Auto-generated method stub 
			  Intent intent = new Intent(DataView.this,MainActivity.class);
			  startActivity(intent);
		      mShakeListener.stop();  
		  }  		    
	}  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_view, menu);
		return true;
	}

	public void showdata(View view){
		try{
			long input=Long.parseLong(startadr.getText().toString(),16);
			int x=(int)input;
			
			if(x>=myApp.reg[myApp.S0] && x <myApp.MAXMEM){
				myApp.lastdataadr=x;
				//**************************************设定Datalist的值 **************************************
				//绑定XML中的ListView，作为Item的容器  
			    ListView list = (ListView) findViewById(R.id.Datalistview);  
			    //生成动态数组，并且转载数据  
			    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
			     int datashowfrom;
			     if(myApp.lastdataadr%8 != 0) datashowfrom=myApp.lastdataadr - myApp.lastdataadr%8;
			     else datashowfrom=myApp.lastdataadr;
			    	for(int i=datashowfrom;i<datashowfrom+8*20;i+=8)  
				    {  
			    		String address=Integer.toHexString(i);
				    	String data[]=new String[8];
				    	
			    		while(address.length()<8){
				    		address="0"+address;
				    	}
			    		
				    	for(int j=0;j<8;j++){
				    		data[j]=Integer.toHexString(((int)myApp.memory[i+j])& 0x000000ff);
				    		while(data[j].length()<2){
				    			data[j]="0"+data[j];
				    	    }
				    		if(i+j==myApp.lastdataadr) data[j]=data[j]+"*";
				    	}
				    	
				        HashMap<String, String> map = new HashMap<String, String>();  
				        map.put("DataItemTitle", address);  
				        map.put("DataItemText1", data[0]);   
				        map.put("DataItemText2", data[1]);   
				        map.put("DataItemText3", data[2]);   
				        map.put("DataItemText4", data[3]);   
				        map.put("DataItemText5", data[4]);   
				        map.put("DataItemText6", data[5]);   
				        map.put("DataItemText7", data[6]);   
				        map.put("DataItemText8", data[7]);  				        
				        mylist.add(map);  
				    }  
	
			    //生成适配器，数组===》ListItem  
			    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
			                                                mylist,//数据来源   
			                                                R.layout.datalistitem,//ListItem的XML实现                  
			                                                //动态数组与ListItem对应的子项          
			                                                new String[] {"DataItemTitle", "DataItemText1", "DataItemText2", "DataItemText3", "DataItemText4", "DataItemText5", "DataItemText6", "DataItemText7", "DataItemText8"},   	                                                  
			                                                //ListItem的XML文件里面的两个TextView ID  
			                                                new int[] {R.id.DataItemTitle,R.id.DataItemText1,R.id.DataItemText2,R.id.DataItemText3,R.id.DataItemText4,R.id.DataItemText5,R.id.DataItemText6,R.id.DataItemText7,R.id.DataItemText8});  
			    //添加并且显示  
			    list.setAdapter(mSchedule);  
			  //*********************************************end***************************************/
			}
		}
		catch (Exception e) { 
			new AlertDialog.Builder(this)  
            .setTitle("输入错误")
            .setMessage("请输按照正确的地址格式输入。。囧rz")
            .setPositiveButton("确定", null)
            .show();
			return;
		}	
	}
}
