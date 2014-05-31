package com.example.mipsvm;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mipsvm.ShakeListener.OnShakeListener;

public class MainActivity extends Activity {

	ShakeListener mShakeListener = null;  
	private EditText instruction;
	private MyApp myApp; 
	public final int S0=16;
	public final int T0=8;
	
	public final int ADD=0;
	public final int SRL=1;
	public final int SLT=2;
	public final int DIV=3;
	public final int BREAK=4;
	public final int LW=5;
	public final int BEQ=6;
	public final int BNE=7;
	public final int ADDI=8;
	public final int SB=9;
	public final int LB=10;
	public final int SW=11;
	public final int J=12;
	public final int JAL=13;
	public final int BLT=14;
	public final int LI=15;
	public final int MOVE=16;

	@Override
	protected void onResume() {
		super.onResume();

         Toast.makeText(this, "Main的onResume()被调用",
 	            Toast.LENGTH_SHORT).show();     
		System.out.println("Main的onResume()被调用");           /////////////////////////
	}	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "Main的onRestart()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onRestart()被调用");           /////////////////////////
	}	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Main的onDestroy()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onDestroy()被调用");           /////////////////////////
	}

	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "Main的onPause()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onPause()被调用");           /////////////////////////
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(this, "Main的onStop()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onStop()被调用");           /////////////////////////
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "Main的onStart()被调用",
	            Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onStart()被调用");           /////////////////////////
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(this, "Main的onCreate()被调用",
                Toast.LENGTH_SHORT).show();                 
		System.out.println("Main的onCreate()被调用");           /////////////////////////
		
		myApp = ((MyApp)getApplication()); //获得自定义的应用程序MyApp 
		
		instruction=(EditText)findViewById(R.id.inputcode);
	     instruction.setSelectAllOnFocus(true); //当成为焦点时选中文本
	     
		//**************************************设定Codelist的值 **************************************
		//绑定XML中的ListView，作为Item的容器  
	    ListView list = (ListView) findViewById(R.id.Codelistview);  
	    //生成动态数组，并且转载数据  
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
	    if(myApp.insnum==0){
		        HashMap<String, String> map = new HashMap<String, String>();  
		        map.put("CodeItemTitle", "END");  
		        map.put("CodeItemText", "END");   
		        map.put("CodeItemString", "END");  
		        mylist.add(map); 
	    }
	    else{
	    	for(int i=0;i<myApp.insnum;i++)  
		    {  
		    	int y=(myApp.memory[i*4+0] & 0x000000ff) | ((myApp.memory[i*4+1] & 0x000000ff)<<8) | ((myApp.memory[i*4+2] & 0x000000ff)<<16) | ((myApp.memory[i*4+3] & 0x000000ff)<<24);
		    	String address=Integer.toHexString(i*4), code=Integer.toHexString(y);
		    	while(address.length()<8){
		    		address="0"+address;
		    	}
		    	while(code.length()<8){
		    		code="0"+code;
		    	}
		    	if(i*4==myApp.pc) code=code+"  ******";
		        HashMap<String, String> map = new HashMap<String, String>();  
		        map.put("CodeItemTitle", address);  
		        map.put("CodeItemText", code);   
		        map.put("CodeItemString", myApp.mipscode[i]);  
		        mylist.add(map);  
		    }  
	    	    HashMap<String, String> map = new HashMap<String, String>();  
		        map.put("CodeItemTitle", "END");  
		        map.put("CodeItemText", "END"); 
		        map.put("CodeItemString", "END");     
		        mylist.add(map);  
	    }
	    
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
	                                                mylist,//数据来源   
	                                                R.layout.codelistitem,//ListItem的XML实现                  
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"CodeItemTitle", "CodeItemText", "CodeItemString"},   	                                                  
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.CodeItemTitle,R.id.CodeItemText,R.id.CodeItemString});  
	    //添加并且显示  
	    list.setAdapter(mSchedule);  
	  //*********************************************end***************************************/
		
	  //**************************************设定Reglist的值 **************************************
	  		//绑定XML中的ListView，作为Item的容器  
	  	    ListView list2 = (ListView) findViewById(R.id.Reglistview);  
	  	    //生成动态数组，并且转载数据  
	  	    ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();  
	  	    for(int i=0;i<34;i++)  
	  	    {  
	  	    	String regvalue=Integer.toHexString(myApp.reg[i]), regname=myApp.regname[i];
	  	    	while(regvalue.length()<8){
	  	    		regvalue="0"+regvalue;
	  	    	}
	  	        HashMap<String, String> map2 = new HashMap<String, String>();  
	  	        map2.put("RegItemTitle", regname);  
	  	        map2.put("RegItemText", regvalue);  
	  	        mylist2.add(map2);  
	  	    }  
	  	    //生成适配器，数组===》ListItem  
	  	    SimpleAdapter mSchedule2 = new SimpleAdapter(this, //没什么解释  
	  	                                                mylist2,//数据来源   
	  	                                                R.layout.reglistitem,//ListItem的XML实现                  
	  	                                                //动态数组与ListItem对应的子项          
	  	                                                new String[] {"RegItemTitle", "RegItemText"},   	                                                  
	  	                                                //ListItem的XML文件里面的两个TextView ID  
	  	                                                new int[] {R.id.RegItemTitle,R.id.RegItemText});  
	  	    //添加并且显示  
	  	    list2.setAdapter(mSchedule2);  
	  	  //*********************************************end***************************************/
	  	
	  		
			
	    	  mShakeListener = new ShakeListener(this);  
	    	  mShakeListener.setOnShakeListener(new shakeLitener());  
	}
	
	private class shakeLitener implements OnShakeListener{  
		  @Override  
		  public void onShake() {  
		   // TODO Auto-generated method stub  
			  Intent intent = new Intent(MainActivity.this, ShowScreen.class);
			   startActivity(intent);
			   finish();
		   mShakeListener.stop();  
		  }  
		    
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//往Menu中添加两个菜单项
    	//第一个参数int groupId表示：组的编号
    	//第二个参数int itemId表示：菜单项的id
    	//第三个参数表示int order：显示的顺序
    	//第四个参数表示CharSequence title：要显示的文字
    	menu.add(0,1,1,R.string.exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//判断被选中的控件id是否等于1
		if(item.getItemId() == 1){
			//进行释放、销毁
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public int findregnum(String reg){
		int i;
		for(i=0;i<32;i++){
			if(reg.equals(myApp.regname[i])) break;
		}
		return i;
	}
	
	public void addtocodelist(View view) {
		int op, rs, rt, rd, sft, fun, imm;
		int x[]=new int[4];
		int fakeinsnum=1;
		Exception e= new Exception();
	    // Do something in response to button
		try{
			String code=instruction.getText().toString();
			String ins[]=code.split("[ :,\\(\\)]+");
			String temp;
			System.out.println("len="+ins.length);
			for(String yy:ins){
				System.out.println(yy+"!");
			}
			
			
			boolean haslabel=(code.indexOf(':'))!=-1;
            int start=0;
            int opnum;
            int i;
			if(haslabel){

				for(i=0;i<myApp.labelnum;i++){
					if(ins[0].equals(myApp.labeltable[i].labelname)){
						if(myApp.labeltable[i].decided!=true){
							myApp.labeltable[i].address=myApp.insnum*4;
							 for(int adri:myApp.labeltable[i].adrlisti){
								 int newins=(myApp.memory[adri+0] & 0x000000ff) | ((myApp.memory[adri+1] & 0x000000ff)<<8) | ((myApp.memory[adri+2] & 0x000000ff)<<16) | ((myApp.memory[adri+3] & 0x000000ff)<<24);							 
								 newins=(newins & 0xffff0000)| ((myApp.insnum*4-(adri+4))&0x0000ffff); 
								 myApp.memory[adri+0]=(byte)(newins & 0x000000ff);
								 myApp.memory[adri+1]=(byte)((newins & 0x0000ff00)>>>8);
								 myApp.memory[adri+2]=(byte)((newins & 0x00ff0000)>>>16);
								 myApp.memory[adri+3]=(byte)((newins & 0xff000000)>>>24);
							 }
							 for(int adrj:myApp.labeltable[i].adrlistj){
								 int newins=(myApp.memory[adrj+0] & 0x000000ff) | ((myApp.memory[adrj+1] & 0x000000ff)<<8) | ((myApp.memory[adrj+2] & 0x000000ff)<<16) | ((myApp.memory[adrj+3] & 0x000000ff)<<24);
								 newins=(newins & 0xfc000000)| ((myApp.insnum*4-(adrj+4))&0x03ffffff); 
								 myApp.memory[adrj+0]=(byte)(newins & 0x000000ff);
								 myApp.memory[adrj+1]=(byte)((newins & 0x0000ff00)>>>8);
								 myApp.memory[adrj+2]=(byte)((newins & 0x00ff0000)>>>16);
								 myApp.memory[adrj+3]=(byte)((newins & 0xff000000)>>>24);
							 }  
							 myApp.labeltable[i].decided=true;
						}
						else {
							throw(e);
						}
					   break;				
					}
				}

				if(i==myApp.labelnum){
					myApp.labeltable[i]=new label();
					myApp.labeltable[i].address=myApp.insnum*4;
					myApp.labeltable[i].labelname=ins[0];
					myApp.labeltable[i].decided=true;
					myApp.labelnum++;
				}		
                start++; 
			}  //refresh the label table
			
			System.out.println("start = "+start);
			for(opnum=0;opnum<17;opnum++){
				if(myApp.opname[opnum].equals(ins[start])) break;
			}
			
			System.out.println("op = "+ins[start]+", opnum = "+opnum);
			switch(opnum){
			case ADD:    op=0;
			           fun=0x20;
			           sft=0;
				       rd=findregnum(ins[start+1]);
			           rs=findregnum(ins[start+2]);
			           rt=findregnum(ins[start+3]);
			           x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;

				          System.out.println("THIS IS ADD:"+ ins[start]+" "+rd+" "+rs+" "+rt); 
			           break;
			           
			case SRL:    op=0;
			           fun=0x02;
			           sft=Integer.parseInt(ins[start+3]);
				       rd=findregnum(ins[start+1]);
			           rt=findregnum(ins[start+2]);
			           rs=0;
			           x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;
			           break;
			           
			case SLT:   op=0;
			           fun=0x2a;
			           sft=0;
				       rd=findregnum(ins[start+1]);
			           rs=findregnum(ins[start+2]);
			           rt=findregnum(ins[start+3]);
			           x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;
			           break;

			case DIV:    op=0;
			           fun=0x1a;
			           sft=0;
				       rs=findregnum(ins[start+1]);
			           rt=findregnum(ins[start+2]);
			           rd=0;
			           x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;
			           break;
			           
			case BREAK:    op=0;
			           fun=0x0d;
			           sft=0;
				       rd=0;
			           rs=0;
			           rt=0;
			           x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;
			           break;
			           
			case LW: op=0x23;
			         rt=findregnum(ins[start+1]);
			         imm=Integer.parseInt(ins[start+2]);
			         rs=findregnum(ins[start+3]);
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
			         break;

			case BEQ: op=0x04;
			         rs=findregnum(ins[start+1]);
			         rt=findregnum(ins[start+2]);       		         
						for(i=0;i<myApp.labelnum;i++){
							if(ins[start+3].equals(myApp.labeltable[i].labelname)){
								if(myApp.labeltable[i].decided==false) myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
								break;
							}
						}
						if(i==myApp.labelnum){
							myApp.labeltable[i]=new label();
							myApp.labeltable[i].address=0;
							myApp.labeltable[i].labelname=ins[start+3];
							myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
							for(int adri:myApp.labeltable[i].adrlisti) System.out.println(adri);///////////
							myApp.labeltable[i].decided=false;
							myApp.labelnum++;
						}
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | ((myApp.labeltable[i].address-(myApp.insnum+1)*4)/4)&0xffff;
			         break;

			case BNE: op=0x05;
					 rs=findregnum(ins[start+1]);
			         rt=findregnum(ins[start+2]);		         
			         for(i=0;i<myApp.labelnum;i++){
							if(ins[start+3].equals(myApp.labeltable[i].labelname)){
								if(myApp.labeltable[i].decided==false) myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
								break;
							}
						}
						if(i==myApp.labelnum){
							myApp.labeltable[i]=new label();
							myApp.labeltable[i].address=0;
							myApp.labeltable[i].labelname=ins[start+3];
							myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
							myApp.labeltable[i].decided=false;
							myApp.labelnum++;
						}
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | ((myApp.labeltable[i].address-(myApp.insnum+1)*4)/4)&0xffff;
			         break;

			case ADDI: op=0x08;
			         rt=findregnum(ins[start+1]);
			         rs=findregnum(ins[start+2]);
			         imm=Integer.parseInt(ins[start+3]);
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
			         break;

			case SB: op=0x28;
			         rt=findregnum(ins[start+1]);
			         rs=findregnum(ins[start+3]);
			         imm=Integer.parseInt(ins[start+2]);
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
			         break;

			case LB: op=0x20;
			         rt=findregnum(ins[start+1]);
			         rs=findregnum(ins[start+3]);
			         imm=Integer.parseInt(ins[start+2]);
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
			         break;

			case SW: op=0x2b;
			         rt=findregnum(ins[start+1]);
			         rs=findregnum(ins[start+3]);
			         imm=Integer.parseInt(ins[start+2]);
			         x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
			         break;
			         
			case J: op=0x02;
					for(i=0;i<myApp.labelnum;i++){
						if(ins[start+1].equals(myApp.labeltable[i].labelname)){
							if(myApp.labeltable[i].decided==false) myApp.labeltable[i].adrlistj.add(myApp.insnum*4);
							break;
						}
					}
					if(i==myApp.labelnum){
						myApp.labeltable[i]=new label();
						myApp.labeltable[i].address=0;
						myApp.labeltable[i].labelname=ins[start+1];
						myApp.labeltable[i].adrlistj.add(myApp.insnum*4);
						myApp.labeltable[i].decided=false;
						myApp.labelnum++;
					}
					x[0]=(op<<26) | ((myApp.labeltable[i].address>>2) & 0x3ffffff);
					break;
					
			case JAL: op=0x03;
						for(i=0;i<myApp.labelnum;i++){
							if(ins[start+1].equals(myApp.labeltable[i].labelname)){
								if(myApp.labeltable[i].decided==false) myApp.labeltable[i].adrlistj.add(myApp.insnum*4);
								break;
							}
						}
						if(i==myApp.labelnum){
							myApp.labeltable[i]=new label();
							myApp.labeltable[i].address=0;
							myApp.labeltable[i].labelname=ins[start+1];
							myApp.labeltable[i].adrlistj.add(myApp.insnum*4);
							myApp.labeltable[i].decided=false;
							myApp.labelnum++;
						}
					x[0]=(op<<26) | ((myApp.labeltable[i].address>>2) & 0x3ffffff);
					break;
					
			case BLT:op=0;
				      fun=0x2a;
			          rs=findregnum(ins[start+1]);
			          rt=findregnum(ins[start+2]);
					  rd=1;
					  sft=0;
					  x[0] =(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) | fun;
					  op=0x05;
					  rs=1;
					  rt=0;
					  for(i=0;i<myApp.labelnum;i++){
							if(ins[start+3].equals(myApp.labeltable[i].labelname)){
								if(myApp.labeltable[i].decided==false) myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
								break;
							}
						}
						if(i==myApp.labelnum){
							myApp.labeltable[i]=new label();
							myApp.labeltable[i].address=0;
							myApp.labeltable[i].labelname=ins[start+3];
							myApp.labeltable[i].adrlisti.add(myApp.insnum*4);
							myApp.labeltable[i].decided=false;
							myApp.labelnum++;
						}
					  fakeinsnum=2;
			          x[1]=(op<<26) | (rs<<21) | (rt<<16) | ((myApp.labeltable[i].address-(myApp.insnum+2)*4)/4)&0xffff;
					  break;
					  
			case LI: op=0x08;
		            rt=findregnum(ins[start+1]);
		            rs=0;
		            imm=Integer.parseInt(ins[start+2]);
		            x[0]=(op<<26) | (rs<<21) | (rt<<16) | imm;
		            break;
		            
			case MOVE:op=0; 
			          sft=0;
				      fun=0x20;
			          rd=findregnum(ins[start+1]);
			          rs=findregnum(ins[start+2]);
			          rt=0;
			          x[0]=(op<<26) | (rs<<21) | (rt<<16) | (rd<<11) | (sft<<6) |fun;
			          System.out.println("THIS IS MOVE:"+ ins[start]+" "+rs+" "+rd); 
			          break;
			          
			default: throw (e);		         
			}
			
			if(myApp.labelnum!=0)
			System.out.println("labelnum = "+myApp.labelnum+", labeltable[num-1].name = "+myApp.labeltable[myApp.labelnum-1].labelname+" .address="+ myApp.labeltable[myApp.labelnum-1].address); 
			//long input=Long.parseLong(instruction.getText().toString(),16);
			//int x=(int)input;
			
			if(haslabel) temp=ins[0]+": ";
			else temp="";
			if(fakeinsnum==1){
				System.out.println(fakeinsnum);
				switch(opnum){
				case MOVE:
					temp+="add "+ins[start+1]+", $zero, "+ins[start+2];
					myApp.mipscode[myApp.insnum]=temp;
					break;
				case LI:
					temp+="addi "+ins[start+1]+", "+ins[start+2]+", 0";
					myApp.mipscode[myApp.insnum]=temp;
					break;
				case LB:
				case LW:
				case SB:
				case SW: temp+=ins[start];
					     temp+=" "+ins[start+1]+", "+ins[start+2]+"("+ins[start+3]+")";
				         break;
				default:
					temp+=ins[start];
					if(ins.length-1>0)
					temp+=" "+ins[start+1];
					for(i=start+2;i<ins.length;i++) 
						temp+=", "+ins[i];
				}		
				myApp.mipscode[myApp.insnum]=temp;
			}
			else{
				switch(opnum){			
				case BLT:
					temp+="slt $at, "+ins[start+1]+", "+ins[start+2];
					myApp.mipscode[myApp.insnum]=temp;
					temp="bne $at, $zero, "+ins[start+3];
					myApp.mipscode[myApp.insnum+1]=temp;
					break;
				}
				
			}
			
			for(int num=0;num<fakeinsnum;num++){
				myApp.memory[myApp.insnum*4+0]=(byte)(x[num] & 0x000000ff);
				myApp.memory[myApp.insnum*4+1]=(byte)((x[num] & 0x0000ff00)>>>8);
				myApp.memory[myApp.insnum*4+2]=(byte)((x[num] & 0x00ff0000)>>>16);
				myApp.memory[myApp.insnum*4+3]=(byte)((x[num] & 0xff000000)>>>24);
				myApp.insnum++;
				myApp.reg[S0]=myApp.insnum*4;  //让S0为内存堆区首地址，堆区紧跟代码段
			}
			
		}
		catch (Exception ee) { 
			Toast.makeText(this, "输入错误。。囧rz",
	                Toast.LENGTH_SHORT).show();
			
			 instruction.clearFocus();  //使失去焦点
		     InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);        		     
		       im.hideSoftInputFromWindow(instruction.getWindowToken(), 0); //当失去焦点时关闭软键盘   
			return;
			
		}

			Toast.makeText(this, "指令提交成功！^_^",
	                Toast.LENGTH_SHORT).show();

		//**************************************更新显示Codelist的值 **************************************//
				//绑定XML中的ListView，作为Item的容器  
			    ListView list = (ListView) findViewById(R.id.Codelistview);  
			    //生成动态数组，并且转载数据  
			    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
			    for(int i=0;i<myApp.insnum;i++)  
			    {  
			    	int y=(myApp.memory[i*4+0] & 0x000000ff) | ((myApp.memory[i*4+1] & 0x000000ff)<<8) | ((myApp.memory[i*4+2] & 0x000000ff)<<16) | ((myApp.memory[i*4+3] & 0x000000ff)<<24);
			    	String address=Integer.toHexString(i*4), code=Integer.toHexString(y);
			    	while(address.length()<8){
			    		address="0"+address;
			    	}
			    	while(code.length()<8){
			    		code="0"+code;
			    	}
			    	if(i*4==myApp.pc) code=code+"  ******";
			        HashMap<String, String> map = new HashMap<String, String>();  
			        map.put("CodeItemTitle", address);  
			        map.put("CodeItemText", code); 
			        map.put("CodeItemString", myApp.mipscode[i]);
			        mylist.add(map);  
			    }   
			    HashMap<String, String> map = new HashMap<String, String>();  
		        map.put("CodeItemTitle", "END");  
		        map.put("CodeItemText", "END");    
		        map.put("CodeItemString", "END");   
		        mylist.add(map);  
			    //生成适配器，数组===》ListItem  
			    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
			                                                mylist,//数据来源   
			                                                R.layout.codelistitem,//ListItem的XML实现                  
			                                                //动态数组与ListItem对应的子项          
			                                                new String[] {"CodeItemTitle", "CodeItemText", "CodeItemString"},   	                                                  
			                                                //ListItem的XML文件里面的两个TextView ID  
			                                                new int[] {R.id.CodeItemTitle,R.id.CodeItemText,R.id.CodeItemString});  
			    //添加并且显示  
			    list.setAdapter(mSchedule);  
			  //*********************************************end**************************************//

			    //**************************************刷新Reglist的值 **************************************
		  		//绑定XML中的ListView，作为Item的容器  
		  	    ListView list2 = (ListView) findViewById(R.id.Reglistview);  
		  	    //生成动态数组，并且转载数据  
		  	    ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();  
		  	    for(int i=0;i<34;i++)  
		  	    {  
		  	    	String regvalue=Integer.toHexString(myApp.reg[i]), regname=myApp.regname[i];
		  	    	while(regvalue.length()<8){
		  	    		regvalue="0"+regvalue;
		  	    	}
		  	        HashMap<String, String> map2 = new HashMap<String, String>();  
		  	        map2.put("RegItemTitle", regname);  
		  	        map2.put("RegItemText", regvalue);  
		  	        mylist2.add(map2);  
		  	    }  
		  	    //生成适配器，数组===》ListItem  
		  	    SimpleAdapter mSchedule2 = new SimpleAdapter(this, //没什么解释  
		  	                                                mylist2,//数据来源   
		  	                                                R.layout.reglistitem,//ListItem的XML实现                  
		  	                                                //动态数组与ListItem对应的子项          
		  	                                                new String[] {"RegItemTitle", "RegItemText"},   	                                                  
		  	                                                //ListItem的XML文件里面的两个TextView ID  
		  	                                                new int[] {R.id.RegItemTitle,R.id.RegItemText});  
		  	    //添加并且显示  
		  	    list2.setAdapter(mSchedule2);  
		  	  //*********************************************end***************************************//  

			     
			     instruction.clearFocus();  //使失去焦点
			     InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);        		     
			       im.hideSoftInputFromWindow(instruction.getWindowToken(), 0); //当失去焦点时关闭软键盘   
			     
	}
	public void Step(View view){
		int op, rs, rt, rd, dat, adr, sft, fun, IR;
        
		IR=(myApp.memory[myApp.pc+0] & 0x000000ff) | ((myApp.memory[myApp.pc+1] & 0x000000ff)<<8) | ((myApp.memory[myApp.pc+2] & 0x000000ff)<<16) | ((myApp.memory[myApp.pc+3] & 0x000000ff)<<24);
            myApp.pc+=4;
        op =(IR>>>26)&63; //IR{31..26}
        rs =(IR>>>21)&31; //IR{25..21}
        rt =(IR>>>16)&31; //IR{20..16}
        rd =(IR>>>11)&31; //IR{15..11}
        sft=(IR>>>6)&31; //IR{11..6}
        fun=IR&63; //IR{5..0}
        dat=(int)(short)(IR&0xFFFF); //IR{15..0}
        adr=(IR&0x3FFFFFF)<<2; //IR{25..0}<<2
        
        switch(op){
            case 0: //R-type
            switch(fun){
                case 32: //ADD
                myApp.reg[rd] = myApp.reg[rs]+myApp.reg[rt];
                break;
                case 34: //SUB
                myApp.reg[rd] = myApp.reg[rs]-myApp.reg[rt];
                break;
                case 2: //SRL
                	myApp.reg[rd]=myApp.reg[rt]>>>sft;
                break;
                 case 42: //SLT
                	 myApp.reg[rd]=(myApp.reg[rs]<myApp.reg[rt]) ? 1 : 0;
                	 break;
                 case 13: //break
                	 myApp.pc=myApp.insnum*4;
                	 break;
                 case 26: //DIV	 
                	 myApp.reg[32]=myApp.reg[rs]/myApp.reg[rt];
                	 myApp.reg[33]=myApp.reg[rs]%myApp.reg[rt];
                	 break;
            }
            break;
            case 32: //LB
            	 myApp.reg[rt] =(myApp.memory[myApp.reg[rs]+dat] & 0x000000ff);
            	 break;
            case 35: //LW
            myApp.reg[rt] =
            (myApp.memory[myApp.reg[rs]+dat+0] & 0x000000ff)
            |((myApp.memory[myApp.reg[rs]+dat+1] & 0x000000ff)<<8)
            |((myApp.memory[myApp.reg[rs]+dat+2] & 0x000000ff)<<16)
            |((myApp.memory[myApp.reg[rs]+dat+3] & 0x000000ff)<<24);
            break;
            case 8: //ADDI
            	myApp.reg[rt] = myApp.reg[rs]+dat;
            	break;
            case 40: //SB
            	myApp.memory[myApp.reg[rs]+dat] = (byte)(myApp.reg[rt]& 0x000000ff);
            	break;
            case 43: //SW
            myApp.memory[myApp.reg[rs]+dat+0] = (byte)(myApp.reg[rt]& 0x000000ff);
            myApp.memory[myApp.reg[rs]+dat+1] = (byte)((myApp.reg[rt]>>8)& 0x000000ff);
            myApp.memory[myApp.reg[rs]+dat+2] = (byte)((myApp.reg[rt]>>16)& 0x000000ff);
            myApp.memory[myApp.reg[rs]+dat+3] = (byte)((myApp.reg[rt]>>24)& 0x000000ff);
            break;
            case 4: //BEQ
            if(myApp.reg[rs] == myApp.reg[rt])myApp.pc+=(dat<<2);
            break;
            case 2: //J
            myApp.pc=myApp.pc&0xc0000000 | adr;
            break;
            case 3: //JAL
            	myApp.reg[31]=myApp.pc;
                myApp.pc=myApp.pc&0xc0000000 | adr;
                break;
            case 5://BNE
            	if(myApp.reg[rs] != myApp.reg[rt])myApp.pc+=(dat<<2);
            	break;
            default: myApp.pc-=4;
            System.out.println("Instruction Error!");
            // break;
        }

      //**************************************更新显示Codelist的值 **************************************//
		//绑定XML中的ListView，作为Item的容器  
	    ListView list = (ListView) findViewById(R.id.Codelistview);  
	    //生成动态数组，并且转载数据  
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
	    for(int i=0;i<myApp.insnum;i++)  
	    {  
	    	int y=(myApp.memory[i*4+0] & 0x000000ff) | ((myApp.memory[i*4+1] & 0x000000ff)<<8) | ((myApp.memory[i*4+2] & 0x000000ff)<<16) | ((myApp.memory[i*4+3] & 0x000000ff)<<24);
	    	String address=Integer.toHexString(i*4), code=Integer.toHexString(y);
	    	while(address.length()<8){
	    		address="0"+address;
	    	}
	    	while(code.length()<8){
	    		code="0"+code;
	    	}
	    	if(i*4==myApp.pc) code=code+"  ******";
	        HashMap<String, String> map = new HashMap<String, String>();  
	        map.put("CodeItemTitle", address);  
	        map.put("CodeItemText", code); 
	        map.put("CodeItemString", myApp.mipscode[i]); 
	        mylist.add(map);  
	    }  
	    HashMap<String, String> map = new HashMap<String, String>();  
        map.put("CodeItemTitle", "END");  
        map.put("CodeItemText", "END");  
        map.put("CodeItemString", "END"); 
        mylist.add(map);  
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
	                                                mylist,//数据来源   
	                                                R.layout.codelistitem,//ListItem的XML实现                  
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"CodeItemTitle", "CodeItemText", "CodeItemString"},   	                                                  
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.CodeItemTitle,R.id.CodeItemText,R.id.CodeItemString});  
	    //添加并且显示  
	    list.setAdapter(mSchedule);  
	  //*********************************************end**************************************//

	    //**************************************刷新Reglist的值 **************************************
  		//绑定XML中的ListView，作为Item的容器  
  	    ListView list2 = (ListView) findViewById(R.id.Reglistview);  
  	    //生成动态数组，并且转载数据  
  	    ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();  
  	    for(int i=0;i<34;i++)  
  	    {  
  	    	String regvalue=Integer.toHexString(myApp.reg[i]), regname=myApp.regname[i];
  	    	while(regvalue.length()<8){
  	    		regvalue="0"+regvalue;
  	    	}
  	        HashMap<String, String> map2 = new HashMap<String, String>();  
  	        map2.put("RegItemTitle", regname);  
  	        map2.put("RegItemText", regvalue);  
  	        mylist2.add(map2);  
  	    }  
  	    //生成适配器，数组===》ListItem  
  	    SimpleAdapter mSchedule2 = new SimpleAdapter(this, //没什么解释  
  	                                                mylist2,//数据来源   
  	                                                R.layout.reglistitem,//ListItem的XML实现                  
  	                                                //动态数组与ListItem对应的子项          
  	                                                new String[] {"RegItemTitle", "RegItemText"},   	                                                  
  	                                                //ListItem的XML文件里面的两个TextView ID  
  	                                                new int[] {R.id.RegItemTitle,R.id.RegItemText});  
  	    //添加并且显示  
  	    list2.setAdapter(mSchedule2);  
  	  //*********************************************end***************************************//
        
	}
	
    public void Run(View view){  	
    	
        int op, rs, rt, rd, dat, adr, sft, fun, IR;  

        while(myApp.pc<myApp.insnum*4){
        	IR=(myApp.memory[myApp.pc+0] & 0x000000ff) | ((myApp.memory[myApp.pc+1] & 0x000000ff)<<8) | ((myApp.memory[myApp.pc+2] & 0x000000ff)<<16) | ((myApp.memory[myApp.pc+3] & 0x000000ff)<<24);
            myApp.pc+=4;
        op =(IR>>>26)&63; //IR{31..26}
        rs =(IR>>>21)&31; //IR{25..21}
        rt =(IR>>>16)&31; //IR{20..16}
        rd =(IR>>>11)&31; //IR{15..11}
        sft=(IR>>>6)&31; //IR{11..6}
        fun=IR&63; //IR{5..0}
        dat=(int)(short)(IR&0xFFFF); //IR{15..0}
        adr=(IR&0x3FFFFFF)<<2; //IR{25..0}<<2
	         switch(op){
	            case 0: //R-type
	            switch(fun){
	                case 32: //ADD
	                myApp.reg[rd] = myApp.reg[rs]+myApp.reg[rt];
	                break;
	                case 34: //SUB
	                myApp.reg[rd] = myApp.reg[rs]-myApp.reg[rt];
	                break;
	                case 2: //SRL
	                	myApp.reg[rd]=myApp.reg[rt]>>>sft;
	                break;
	                 case 42: //SLT
	                	 myApp.reg[rd]=(myApp.reg[rs]<myApp.reg[rt]) ? 1 : 0;
	                	 break;
	                 case 13: //break
	                	 myApp.pc=myApp.insnum*4;
	                	 break;
	                 case 26: //DIV	 
	                	 myApp.reg[32]=myApp.reg[rs]/myApp.reg[rt];
	                	 myApp.reg[33]=myApp.reg[rs]%myApp.reg[rt];
	                	 break;
	            }
	            break;
	            case 32: //LB
	            	 myApp.reg[rt] =(myApp.memory[myApp.reg[rs]+dat] & 0x000000ff);
	            	 break;
	            case 35: //LW
	            myApp.reg[rt] =
	            (myApp.memory[myApp.reg[rs]+dat+0] & 0x000000ff)
	            |((myApp.memory[myApp.reg[rs]+dat+1] & 0x000000ff)<<8)
	            |((myApp.memory[myApp.reg[rs]+dat+2] & 0x000000ff)<<16)
	            |((myApp.memory[myApp.reg[rs]+dat+3] & 0x000000ff)<<24);
	            break;
	            case 8: //ADDI
	            	myApp.reg[rt] = myApp.reg[rs]+dat;
	            	break;
	            case 40: //SB
	            	myApp.memory[myApp.reg[rs]+dat] = (byte)(myApp.reg[rt]& 0x000000ff);
	            	break;
	            case 43: //SW
	            myApp.memory[myApp.reg[rs]+dat+0] = (byte)(myApp.reg[rt]& 0x000000ff);
	            myApp.memory[myApp.reg[rs]+dat+1] = (byte)((myApp.reg[rt]>>8)& 0x000000ff);
	            myApp.memory[myApp.reg[rs]+dat+2] = (byte)((myApp.reg[rt]>>16)& 0x000000ff);
	            myApp.memory[myApp.reg[rs]+dat+3] = (byte)((myApp.reg[rt]>>24)& 0x000000ff);
	            break;
	            case 4: //BEQ
	            if(myApp.reg[rs] == myApp.reg[rt])myApp.pc+=(dat<<2);
	            break;
	            case 2: //J
	            myApp.pc=myApp.pc&0xc0000000 | adr;
	            break;
	            case 3: //JAL
	            	myApp.reg[31]=myApp.pc;
	                myApp.pc=myApp.pc&0xc0000000 | adr;
	                break;
	            case 5://BNE
	            	if(myApp.reg[rs] != myApp.reg[rt])myApp.pc+=(dat<<2);
	            	break;
	            default: myApp.pc-=4;
	            System.out.println("Instruction Error!");
	            // break;
	        }
        }//程序运行完成
        
        new AlertDialog.Builder(this)  
        .setTitle("DBL！运行成功！")
        //.setMessage("DBL！运行成功！")
        .setPositiveButton("确定", null)
        .show();
        
      //**************************************更新显示Codelist的值 **************************************//
      		//绑定XML中的ListView，作为Item的容器  
      	    ListView list = (ListView) findViewById(R.id.Codelistview);  
      	    //生成动态数组，并且转载数据  
      	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
      	    for(int i=0;i<myApp.insnum;i++)  
      	    {  
      	    	int y=(myApp.memory[i*4+0] & 0x000000ff) | ((myApp.memory[i*4+1] & 0x000000ff)<<8) | ((myApp.memory[i*4+2] & 0x000000ff)<<16) | ((myApp.memory[i*4+3] & 0x000000ff)<<24);
      	    	String address=Integer.toHexString(i*4), code=Integer.toHexString(y);
      	    	while(address.length()<8){
      	    		address="0"+address;
      	    	}
      	    	while(code.length()<8){
      	    		code="0"+code;
      	    	}
      	    	if(i*4==myApp.pc) code=code+"  ******";
      	        HashMap<String, String> map = new HashMap<String, String>();  
      	        map.put("CodeItemTitle", address);  
      	        map.put("CodeItemText", code); 
    	        map.put("CodeItemString", myApp.mipscode[i]);  
      	        mylist.add(map);  
      	    }  
      	    HashMap<String, String> map = new HashMap<String, String>();  
              map.put("CodeItemTitle", "END");  
              map.put("CodeItemText", "END");   
              map.put("CodeItemString", "END");    
              mylist.add(map);  
      	    //生成适配器，数组===》ListItem  
      	    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
      	                                                mylist,//数据来源   
      	                                                R.layout.codelistitem,//ListItem的XML实现                  
      	                                                //动态数组与ListItem对应的子项          
      	                                                new String[] {"CodeItemTitle", "CodeItemText", "CodeItemString"},   	                                                  
      	                                                //ListItem的XML文件里面的两个TextView ID  
      	                                                new int[] {R.id.CodeItemTitle,R.id.CodeItemText,R.id.CodeItemString});  
      	    //添加并且显示  
      	    list.setAdapter(mSchedule);  
      	  //*********************************************end**************************************//
      	    
        //**************************************刷新Reglist的值 **************************************
  		//绑定XML中的ListView，作为Item的容器  
  	    ListView list2 = (ListView) findViewById(R.id.Reglistview);  
  	    //生成动态数组，并且转载数据  
  	    ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();  
  	    for(int i=0;i<34;i++)  
  	    {  
  	    	String regvalue=Integer.toHexString(myApp.reg[i]), regname=myApp.regname[i];
  	    	while(regvalue.length()<8){
  	    		regvalue="0"+regvalue;
  	    	}
  	        HashMap<String, String> map2 = new HashMap<String, String>();  
  	        map2.put("RegItemTitle", regname);  
  	        map2.put("RegItemText", regvalue);  
  	        mylist2.add(map2);  
  	    }  
  	    //生成适配器，数组===》ListItem  
  	    SimpleAdapter mSchedule2 = new SimpleAdapter(this, //没什么解释  
  	                                                mylist2,//数据来源   
  	                                                R.layout.reglistitem,//ListItem的XML实现                  
  	                                                //动态数组与ListItem对应的子项          
  	                                                new String[] {"RegItemTitle", "RegItemText"},   	                                                  
  	                                                //ListItem的XML文件里面的两个TextView ID  
  	                                                new int[] {R.id.RegItemTitle,R.id.RegItemText});  
  	    //添加并且显示  
  	    list2.setAdapter(mSchedule2);  
  	  //*********************************************end***************************************//
    }   
    public void Reset(View view){
    	myApp.pc=0;
    	for(int i=0;i<34;i++){
        	myApp.reg[i]=0;          //reset the registers
        }
    	myApp.reg[S0+1]=myApp.vmaddress;
    	myApp.reg[S0]=myApp.insnum*4;
    	for(int i=myApp.vmaddress; i<279+myApp.vmaddress;i++){
			myApp.memory[i]=0;      //reset the video memory 
		}
    	
    	Toast.makeText(this, "指令重置成功！^_^",
                Toast.LENGTH_SHORT).show();
    	
    	//**************************************更新显示Codelist的值 **************************************//
    			//绑定XML中的ListView，作为Item的容器  
    		    ListView list = (ListView) findViewById(R.id.Codelistview);  
    		    //生成动态数组，并且转载数据  
    		    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
    		    for(int i=0;i<myApp.insnum;i++)  
    		    {  
    		    	int y=(myApp.memory[i*4+0] & 0x000000ff) | ((myApp.memory[i*4+1] & 0x000000ff)<<8) | ((myApp.memory[i*4+2] & 0x000000ff)<<16) | ((myApp.memory[i*4+3] & 0x000000ff)<<24);
    		    	String address=Integer.toHexString(i*4), code=Integer.toHexString(y);
    		    	while(address.length()<8){
    		    		address="0"+address;
    		    	}
    		    	while(code.length()<8){
    		    		code="0"+code;
    		    	}
    		    	if(i*4==myApp.pc) code=code+"  ******";
    		        HashMap<String, String> map = new HashMap<String, String>();  
    		        map.put("CodeItemTitle", address);  
    		        map.put("CodeItemText", code);  
    		        map.put("CodeItemString", myApp.mipscode[i]);  
    		        mylist.add(map);  
    		    }  
    		    HashMap<String, String> map = new HashMap<String, String>();  
    	        map.put("CodeItemTitle", "END");  
    	        map.put("CodeItemText", "END");    
    	        map.put("CodeItemString", "END");  
    	        mylist.add(map);  
    		    //生成适配器，数组===》ListItem  
    		    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
    		                                                mylist,//数据来源   
    		                                                R.layout.codelistitem,//ListItem的XML实现                  
    		                                                //动态数组与ListItem对应的子项          
    		                                                new String[] {"CodeItemTitle", "CodeItemText", "CodeItemString"},   	                                                  
    		                                                //ListItem的XML文件里面的两个TextView ID  
    		                                                new int[] {R.id.CodeItemTitle,R.id.CodeItemText,R.id.CodeItemString});  
    		    //添加并且显示  
    		    list.setAdapter(mSchedule);  
    		  //*********************************************end**************************************//

    		    //**************************************刷新Reglist的值 **************************************
    	  		//绑定XML中的ListView，作为Item的容器  
    	  	    ListView list2 = (ListView) findViewById(R.id.Reglistview);  
    	  	    //生成动态数组，并且转载数据  
    	  	    ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();  
    	  	    for(int i=0;i<34;i++)  
    	  	    {  
    	  	    	String regvalue=Integer.toHexString(myApp.reg[i]), regname=myApp.regname[i];
    	  	    	while(regvalue.length()<8){
    	  	    		regvalue="0"+regvalue;
    	  	    	}
    	  	        HashMap<String, String> map2 = new HashMap<String, String>();  
    	  	        map2.put("RegItemTitle", regname);  
    	  	        map2.put("RegItemText", regvalue);  
    	  	        mylist2.add(map2);  
    	  	    }  
    	  	    //生成适配器，数组===》ListItem  
    	  	    SimpleAdapter mSchedule2 = new SimpleAdapter(this, //没什么解释  
    	  	                                                mylist2,//数据来源   
    	  	                                                R.layout.reglistitem,//ListItem的XML实现                  
    	  	                                                //动态数组与ListItem对应的子项          
    	  	                                                new String[] {"RegItemTitle", "RegItemText"},   	                                                  
    	  	                                                //ListItem的XML文件里面的两个TextView ID  
    	  	                                                new int[] {R.id.RegItemTitle,R.id.RegItemText});  
    	  	    //添加并且显示  
    	  	    list2.setAdapter(mSchedule2);  
    	  	  //*********************************************end***************************************//
    }

    public void Showdata(View view){
    	Intent intent = new Intent(MainActivity.this, DataView.class);
		   startActivity(intent);
    }
}
    
