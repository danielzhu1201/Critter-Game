package test;

import java.util.ArrayList;


public class CritterSpecies {
	public String title = "";
	static public ArrayList<String> lol;
	public CritterSpecies(String name, ArrayList<String> a)
	{
		this.title = name;
		lol = a;
	}
	public CritterSpecies()
	{
		
	}
	public ArrayList<String> getCode()
	{
		return lol;
	}
	
}
