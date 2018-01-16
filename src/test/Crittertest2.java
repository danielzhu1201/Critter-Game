package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class Crittertest2 {

	int[] exp = {1,1,1,1,1};
	Interpreter i = new Interpreter();
	@Test
	public void test() throws IOException{
		CritterSpecies sTest = i.loadSpecies("Test.cri");
		Critter c = new Critter(sTest);
		int[] registers = {0,0,0,0,0,0,0,0,0,0};
		for(int q = 0; q<5; q++)
		{
			int pcl = i.executeCritter(c);
			assertEquals(exp[q],pcl);
		}
		for(int q = 0; q <10; q++)
		{
			assertEquals(registers[q],c.reg[q]);
		}
	}

}
