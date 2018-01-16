package test;

import java.io.IOException;

public interface CritterInterpreter {

	public CritterSpecies loadSpecies(String filename) throws IOException;
	public int executeCritter(Critter c);
}
