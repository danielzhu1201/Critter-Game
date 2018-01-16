package test;

import java.util.*;
import java.io.*;

public class Critter extends CritterSpecies{
	//public int[][] m = new int[3][3];
	public int nextcodeline = 1;
	public int bearing;
	public int[] reg = {0,0,0,0,0,0,0,0,0,0};
	public ArrayList<String> s = new ArrayList<>();
	public static final int ALLY = 3;
	public static final int BAD = -1;
	public static final int EMPTY = 0;
	public static final int ENEMY = 2;
	public static final int FRONT = 0;
	public static final int FRONT_LEFT = 315;
	public static final int FRONT_RIGHT = 45;
	public static final int LEFT = 270;
	public static final int REAR = 180;
	public static final int REAR_LEFT = 225;
	public static final int REAR_RIGHT = 135;
	public static final int REGISTERS = 10;
	public static final int RIGHT = 90;
	public static final int WALL = 1;
	public static final int WELL_FED_DURATION = 0;
	public String hop = "hop";
	public Critter(CritterSpecies cssp)
	{
		/*for(int i = 0; i<3; i++)
		{
			for(int j = 0; i<3; j++)
			{
				this.m[i][j] = 0;
			}
		}*/
		//this.m[1][1] = 1;
		this.bearing = 0;
		this.s = cssp.getCode();
		
	}
	
	public void hop()
	{

	}
	
	public void left() throws IOException
	{
		
	}
	
	public void right() throws IOException
	{

	}
	
	public void eat() throws IOException
	{

	}
	
	public void infect() throws IOException
	{

	}
	
	public void infect(int x) throws IOException
	{

	}
	
	public void setNextCodeLine(int x)
	{
		this.nextcodeline=x;
	}
	
	public int getNextCodeLine()
	{
		return this.nextcodeline;
	}
	
	public int getCellContent(int x)
	{
		if (x==0)
		{
			return EMPTY;
		}
		else if (x==45||x==90||x==135) return WALL;
		else if (x==180||x==270) return ALLY;
		else return ENEMY;
	}
	
	public ArrayList<String> getCode()
	{
		return super.getCode();
	}
	
	public Boolean ifRandom()
	{
		double i = Math.random();
		
		if(i>=0.5) return true;
		
		else return false;
	}
	
	public int getOffAngle(int b)
	{
		if(b==45)
		return RIGHT;
		else return LEFT;
	}
	
	public void setReg(int r, int value) {
		
		this.reg[r-1] = value;
	}
	
	public int getReg(int r)
	{
		return this.reg[r-1];
	}
};