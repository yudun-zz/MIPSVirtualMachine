package com.example.mipsvm;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

class label{
	public int address;
	public String labelname; 
	public boolean decided;
	public List<Integer> adrlisti =new ArrayList<Integer>();
	public List<Integer> adrlistj =new ArrayList<Integer>();
}

public class MyApp extends Application{ 
	
	public final int MAXMEM =8192;
	public final int MAXLABELNUM =100;
	public final int S0=16;
	public final int T0=8;
	public final int SP=29;
	public final int vmaddress=4000;
	public String[] mipscode = new String[100]; 
    public int reg[]= new int[34];
    public byte memory[] = new byte[MAXMEM];
    public label[] labeltable = new label[MAXLABELNUM];
    public int pc, ins, insnum, labelnum;
    public int lastdataadr;
    public final String regname[]={"$zero","$at","$v0","$v1","$a0","$a1","$a2","$a3",
 		   "$t0","$t1","$t2","$t3","$t4","$t5","$t6","$t7","$s0","$s1",
 		   "$s2","$s3","$s4","$s5","$s6","$s7","$t8","$t9","$k0","$k1",
 		   "$gp","$sp","$fp","$ra", "HI", "LO"};
    public final String opname[]={"add","srl","slt","div","break",//R-type instructions   0，1，2，3，4 
    	     "lw","beq","bne","addi","sb","lb","sw",             //I-Type Instructions   5, 6, 7, 8, 9, 10, 11 
    		  "j", "jal",                                             //J-Type Instructions   13
    	      "blt", "li", "move"};                             //PSEUDO Instructions    14, 15, 16
    
    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        
        //initialize global variable
        pc=0;
        for(int i=0;i<34;i++){
        	reg[i]=0;          //clear the registers
        }
        reg[S0+1]=vmaddress; //将S1初始化为显存首地址
        reg[SP]=0x1fff; //将堆栈顶放到末尾
        for(int i=0;i<MAXMEM;i++){
        	memory[i]=0;       //clear memory
        }

        insnum=labelnum=lastdataadr=0;
    }    
} 
