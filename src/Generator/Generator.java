package Generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import LineStatement.LineStatement;
import Parser.*;

public class Generator implements IGenerator {
	Parser parser;
	
	public Generator(Parser parser) {
		this.parser = parser;
	}
	
	public void generateLstFile() {
		
		String comment = "";
		String label = "";
		String instruction = "";
		String stringCode = "";
		String operand = "";
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream("output.lst"));
			pw.printf("%-5s %-5s %-15s %-10s %-10s %-20s %-20s\n","Line","Addr","Machine Code","Label", "Assembly", "code","Comments");
			pw.print("\n");
			for(int i=0;i<parser.getAssemblyUnit().getsize(); i++){
					LineStatement line = parser.getAssemblyUnit().getIR().get(i);
					if(line.getComment() != null) {
						comment = line.getComment().getTokenName();
					}
					
					if(line.getLabel() != null) {
						label = line.getLabel().getTokenName();
					}
					
					if(line.getInstruction() != null) {
						instruction = line.getInstruction().getTokenName();
						stringCode = line.getInstruction().getCompletedMachineCode();
						operand = line.getInstruction().getOperand();
					}
					
					if(line.getAddress()<16) {
						pw.printf("%-5s %-5s %-15s %-10s %-10s %-20s %-20s\n",i+1,"000"+Integer.toHexString(line.getAddress()).toUpperCase(),stringCode,label, instruction,operand,comment);
						}
					else
						pw.printf("%-5s %-5s %-15s %-10s %-10s %-20s %-20s\n",i+1,"00"+Integer.toHexString(line.getAddress()).toUpperCase(),stringCode,label, instruction,operand,comment);
					comment = "";
					label = "";
					instruction = "";
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				

	}

	// create and write the exe file
	public void generateExeFile() { 
		String stringCode = "";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream("output.exe"));
			for(int i=0;i<parser.getAssemblyUnit().getsize(); i++){
				LineStatement line = parser.getAssemblyUnit().getIR().get(i);
				if(line.getInstruction() != null) {

					stringCode = line.getInstruction().getCompletedMachineCode() + " ";

					pw.printf(stringCode);
				}
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
