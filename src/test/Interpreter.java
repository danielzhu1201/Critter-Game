package test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Responsible for loading critter species from text files and interpreting the
 * simple Critter language.
 * 
 * For more information on the purpose of the below two methods, see the
 * included API/ folder and the project description.
 */
public class Interpreter implements CritterInterpreter {

	public int gotostepn(String stepn, int i) {
		try {
			if (stepn.charAt(0) == '+') {
				i += Integer.parseInt(stepn.substring(1));
			} else if (stepn.charAt(0) == '-') {
				i -= Integer.parseInt(stepn.substring(1));
			} else {
				i = Integer.parseInt(stepn.substring(0));
			}
			return i;

		} catch (Exception e) {
			System.err.println("Invalid number format");
			return -1;
		}

	}

	public CritterSpecies loadSpecies(String filename) {
		// obviously, your code should do something

		Scanner filein = null;
		String species = "";
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("");
		try {

			filein = new Scanner(new FileReader(filename));
			species = filein.nextLine();
			while (filein.hasNextLine()) {
				String oneline = filein.nextLine();
				if (oneline.equals(""))
					break;
				commands.add(oneline);
			}

			if (commands.size() > 1)
				return new CritterSpecies(species, commands);
			else
				return null;

		} catch (Exception e) {
			System.err.println("Warning: Exception");
			return null;
		}

	}

	// infinite loop
	public int executeCritter(Critter c) {
		// obviously, your code should do something
		ArrayList<String> commands = (ArrayList<String>) c.getCode();

		// 1st time always go from 1, and regd default =0
		int i = c.getNextCodeLine();
		int p = 0;
		if(i>commands.size()){System.err.println("Next Line index out of bounds");}
		while (i < commands.size() && i >= 1 && p < 1000) {
			String cur = commands.get(i);

			// System.out.println("command = "+ cur);

			// Go - i put it at first b/c logically it looks better
			if (cur.length() >= 2 && cur.substring(0, 2).equals("go")) {
				p++;
				i = this.gotostepn(cur.substring(3), i);
				continue;
			}

			// ifRandom
			else if (cur.length() >= 8
					&& cur.substring(0, 8).equals("ifrandom")) {
				if (c.ifRandom() == true) {
					p++;
					i = this.gotostepn(cur.substring(9), i);
					continue;
				}
			}

			// IfEmpty
			else if (cur.length() >= 7 && cur.substring(0, 7).equals("ifempty")) {
				p++;
				String bearing = "";
				int temp = 8;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					bearing += cur.charAt(temp);
					temp++;
				}
				temp++;
				int bear = Interpreter.parseInt(bearing);
				if (bear <= 315 && bear % 45 == 0 && bear>=0) {
					if (c.getCellContent(bear) == c.EMPTY) {
						String instruction = cur.substring(temp);
						i = this.gotostepn(instruction, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
				}

			}

			// IfAlly
			else if (cur.length() >= 6 && cur.substring(0, 6).equals("ifally")) {
				p++;
				String bearing = "";
				int temp = 7;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					bearing += cur.charAt(temp);
					temp++;
				}
				temp++;
				int bear = Interpreter.parseInt(bearing);
				if (bear <= 315 && bear % 45 == 0 && bear>=0) {
					if (c.getCellContent(bear) == c.ALLY) {
						String instruction = cur.substring(temp);
						i = this.gotostepn(instruction, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
				}
			}

			// IfEnemy
			else if (cur.length() >= 7 && cur.substring(0, 7).equals("ifenemy")) {
				p++;
				String bearing = "";
				int temp = 8;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					bearing += cur.charAt(temp);
					temp++;
				}
				temp++;
				int bear = Interpreter.parseInt(bearing);
				if (bear <= 315 && bear % 45 == 0) {
					if (c.getCellContent(bear) == c.ENEMY) {
						String instruction = cur.substring(temp);
						i = this.gotostepn(instruction, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
				}
			}

			// IfWall
			else if (cur.length() >= 6 && cur.substring(0, 6).equals("ifwall")) {
				p++;
				String bearing = "";
				int temp = 7;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					bearing += cur.charAt(temp);
					temp++;
				}
				temp++;
				int bear = Interpreter.parseInt(bearing);
				if (bear <= 315 && bear % 45 == 0) {
					if (c.getCellContent(bear) == c.WALL) {
						String instruction = cur.substring(temp);
						i = this.gotostepn(instruction, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
				}

			}

			// IfAngle
			else if (cur.length() >= 7 && cur.substring(0, 7).equals("ifangle")) {
				p++;
				String bearing1 = "";
				String bearing2 = "";
				int temp = 8;
				while (cur.charAt(temp) != ' ' && temp < cur.length()
						&& temp < cur.length()) {
					bearing1 += cur.charAt(temp);
					temp++;
				}
				temp++;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					bearing2 += cur.charAt(temp);
					temp++;
				}

				int bear1 = Interpreter.parseInt(bearing1);
				int bear2 = Interpreter.parseInt(bearing2);
				temp++;
				if (bear1 <= 315 && bear2 <= 315 && bear1 % 45 == 0
						&& bear2 % 45 == 0) {
					if (c.getOffAngle(bear1) == bear2) {
						String instruction = Interpreter.subString(cur, temp);
						i = this.gotostepn(instruction, i);
						continue;
					}
				} else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
				}
			}
			// write
			else if (cur.length() >= 5 && cur.substring(0, 5).equals("write")) {
				p++;
				String r = "";
				int temp = 7;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r += cur.charAt(temp);
					temp++;
				}
				temp++;

				int register = Interpreter.parseInt(r);
				int value = Interpreter.parseInt(Interpreter.subString(cur,
						temp));
				if (register >= 1 && register <= 10 && value != -1)
					c.setReg(register, value);
				else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
					// set to a line number > max, so it will jump out
				}
			}

			// add
			else if (cur.length() >= 3 && cur.substring(0, 3).equals("add")) {
				p++;
				String r1 = "";
				int temp = 5;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r1 += cur.charAt(temp);
					temp++;
				}
				temp++;

				int register1 = Interpreter.parseInt(r1);
				int register2 = Interpreter.parseInt(Interpreter.subString(cur,
						temp + 1));
				// && register1<=10 &&register2>=1&&register2<=10
				if (register1 >= 1 && register1 <= 10 && register2 >= 1
						&& register2 <= 10) {
					c.setReg(register1,
							c.getReg(register1) + c.getReg(register2));
				} else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
					// set to a line number > max, so it will jump out
				}
			}

			// sub
			else if (cur.length() >= 3 && cur.substring(0, 3).equals("sub")) {
				p++;
				String r1 = "";
				int temp = 5;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r1 += cur.charAt(temp);
					temp++;
				}
				temp++;

				int register1 = Interpreter.parseInt(r1);
				int register2 = Interpreter.parseInt(Interpreter.subString(cur,
						temp + 1));

				if (register1 >= 1 && register1 <= 10 && register2 >= 1
						&& register2 <= 10) {
					c.setReg(register1,c.getReg(register1) - c.getReg(register2));
				} else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
					// set to a line number > max, so it will jump out
				}
			}

			// inc by 1
			else if (cur.length() >= 3 && cur.substring(0, 3).equals("inc")) {
				p++;
				int r = Interpreter.parseInt(Interpreter.subString(cur, 5));
				if (r >= 1 && r <= 10)
					c.setReg(r, c.getReg(r) + 1);
				else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
					// set to a line number > max, so it will jump out
				}
			}

			// dec by 1
			else if (cur.length() >= 3 && cur.substring(0, 3).equals("dec")) {
				p++;
				int r = Interpreter.parseInt(Interpreter.subString(cur, 5));
				if (r >= 1 && r <= 10)
					c.setReg(r, c.getReg(r) - 1);
				else {
					System.err.println("Wrong behaviour code.");
					c.setNextCodeLine(commands.size() + 1);
					i = commands.size() + 1;
					// set to a line number > max, so it will jump out
				}
			}

			// iflt
			else if (cur.length() >= 4 && cur.substring(0, 4).equals("iflt")) {
				p++;
				String r1 = "";
				String r2 = "";
				String n = "";
				int temp = 5;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r1 += cur.charAt(temp);
					temp++;
				}
				temp++;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r2 += cur.charAt(temp);
					temp++;
				}
				temp = temp + 2;
				n = Interpreter.subString(cur, temp);

				int reg1 = Interpreter.parseInt(r1);
				int reg2 = Interpreter.parseInt(r2);

				if (reg1 >= 1 && reg1 <= 10 && reg2 >= 1 && reg2 <= 1) {
					if (c.getReg(reg1) < c.getReg(reg2)) {
						i = this.gotostepn(n, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size());
				}
			}

			// ifeq
			else if (cur.length() >= 4 && cur.substring(0, 4).equals("ifeq")) {
				p++;
				String r1 = "";
				String r2 = "";
				String n = "";
				int temp = 5;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r1 += cur.charAt(temp);
					temp++;
				}
				temp++;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r2 += cur.charAt(temp);
					temp++;
				}
				temp = temp + 2;
				n = Interpreter.subString(cur, temp);

				int reg1 = Interpreter.parseInt(r1);
				int reg2 = Interpreter.parseInt(r2);

				if (reg1 >= 1 && reg1 <= 10 && reg2 >= 1 && reg2 <= 1) {
					if (c.getReg(reg1) == c.getReg(reg2)) {
						i = this.gotostepn(n, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size());
				}
			}

			// ifgt
			else if (cur.length() >= 4 && cur.substring(0, 4).equals("ifgt")) {
				p++;
				String r1 = "";
				String r2 = "";
				String n = "";
				int temp = 5;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r1 += cur.charAt(temp);
					temp++;
				}
				temp++;
				while (cur.charAt(temp) != ' ' && temp < cur.length()) {
					r2 += cur.charAt(temp);
					temp++;
				}
				temp = temp + 2;
				n = Interpreter.subString(cur, temp);

				int reg1 = Interpreter.parseInt(r1);
				int reg2 = Interpreter.parseInt(r2);

				if (reg1 >= 1 && reg1 <= 10 && reg2 >= 1 && reg2 <= 1) {
					if (c.getReg(reg1) > c.getReg(reg2)) {
						i = this.gotostepn(n, i);
						continue;
					}
				} else {
					c.setNextCodeLine(commands.size());
				}
			}

			// hop
			else if (cur.equals("hop")) {
				c.hop();
				c.setNextCodeLine(i + 1);
				return 0;
			}

			else if (cur.equals("left")) {
				c.setNextCodeLine(i + 1);
				return 1;
			}

			else if (cur.equals("right")) {
				c.setNextCodeLine(i + 1);
				return 2;
			}

			// infect - 2 situations
			else if (cur.length() >= 6 && cur.substring(0, 6).equals("infect")) {
				// either with or without a number
				if (cur.length() == 6) {
					c.setNextCodeLine(i + 1);
					return 3;
				}

				else {
					int line = Interpreter.parseInt(cur.substring(7));
					if (line == -1 || line >= commands.size())
						c.setNextCodeLine(commands.size() + 1);
					else {
						c.setNextCodeLine(i + 1);
						return 4;
					}
				}
			}

			// eat
			else if (cur.equals("eat")) {
				c.setNextCodeLine(i + 1);
				return 5;
			}
			
			else
			{
				c.setNextCodeLine(commands.size()+1);
				i = commands.size()+1;
			}

			i++;
		}
		c.setNextCodeLine(commands.size());
		return -1;
	}

	public static int parseInt(String parse) {
		int x = 0;
		try {
			parse = parse.trim();
			x = Integer.parseInt(parse);
		} catch (Exception e) {
			return -1;
		}

		return x;
	}

	public static String subString(String a, int index) {
		String string = "";
		try {
			string = a.substring(index);
		} catch (Exception e) {
			return "n/a";
		}
		return string;
	}

}