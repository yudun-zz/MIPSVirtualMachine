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

         Toast.makeText(this, "Data��onResume()������",
 	            Toast.LENGTH_SHORT).show();     
		System.out.println("Data��onResume()������");           /////////////////////////
	}	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "Data��onRestart()������",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onRestart()������");           /////////////////////////
	}	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Data��onDestroy()������",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onDestroy()������");           /////////////////////////
	}

	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "Data��onPause()������",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onPause()������");           /////////////////////////
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(this, "Data��onStop()������",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onStop()������");           /////////////////////////
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "Data��onStart()������",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onStart()������");           /////////////////////////
	}
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_view);
		
		Toast.makeText(this, "Data��onCreate()������",
                Toast.LENGTH_SHORT).show();                 
		System.out.println("Data��onCreate()������");           /////////////////////////
		
		myApp = ((MyApp)getApplication()); //����Զ����Ӧ�ó���MyApp 
		startadr=(EditText)findViewById(R.id.dataaddress);
		
		
		//**************************************�趨Datalist��ֵ **************************************
				//��XML�е�ListView����ΪItem������  
			    ListView list = (ListView) findViewById(R.id.Datalistview);  
			    //���ɶ�̬���飬����ת������  
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
		
			    //����������������===��ListItem  
			    SimpleAdapter mSchedule = new SimpleAdapter(this, //ûʲô����  
			                                                mylist,//������Դ   
			                                                R.layout.datalistitem,//ListItem��XMLʵ��                  
			                                                //��̬������ListItem��Ӧ������          
			                                                new String[] {"DataItemTitle", "DataItemText1", "DataItemText2", "DataItemText3", "DataItemText4", "DataItemText5", "DataItemText6", "DataItemText7", "DataItemText8"},   	                                                  
			                                                //ListItem��XML�ļ����������TextView ID  
			                                                new int[] {R.id.DataItemTitle,R.id.DataItemText1,R.id.DataItemText2,R.id.DataItemText3,R.id.DataItemText4,R.id.DataItemText5,R.id.DataItemText6,R.id.DataItemText7,R.id.DataItemText8});  
			    //��Ӳ�����ʾ  
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
				//**************************************�趨Datalist��ֵ **************************************
				//��XML�е�ListView����ΪItem������  
			    ListView list = (ListView) findViewById(R.id.Datalistview);  
			    //���ɶ�̬���飬����ת������  
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
	
			    //����������������===��ListItem  
			    SimpleAdapter mSchedule = new SimpleAdapter(this, //ûʲô����  
			                                                mylist,//������Դ   
			                                                R.layout.datalistitem,//ListItem��XMLʵ��                  
			                                                //��̬������ListItem��Ӧ������          
			                                                new String[] {"DataItemTitle", "DataItemText1", "DataItemText2", "DataItemText3", "DataItemText4", "DataItemText5", "DataItemText6", "DataItemText7", "DataItemText8"},   	                                                  
			                                                //ListItem��XML�ļ����������TextView ID  
			                                                new int[] {R.id.DataItemTitle,R.id.DataItemText1,R.id.DataItemText2,R.id.DataItemText3,R.id.DataItemText4,R.id.DataItemText5,R.id.DataItemText6,R.id.DataItemText7,R.id.DataItemText8});  
			    //��Ӳ�����ʾ  
			    list.setAdapter(mSchedule);  
			  //*********************************************end***************************************/
			}
		}
		catch (Exception e) { 
			new AlertDialog.Builder(this)  
            .setTitle("�������")
            .setMessage("���䰴����ȷ�ĵ�ַ��ʽ���롣����rz")
            .setPositiveButton("ȷ��", null)
            .show();
			return;
		}	
	}
}
