package test;

import java.io.IOException;

public class maintest {

	public static void main (String[] args) throws IOException
	{
		Interpreter i = new Interpreter();
		CritterSpecies sTest = i.loadSpecies("Test.cri"); //will define
		Critter c = new Critter(sTest);
		for(int j = 0; j<10; j++)
		{
			i.executeCritter(c);
		}
	}
}
