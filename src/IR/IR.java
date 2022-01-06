package IR;

import java.util.ArrayList;

import LineStatement.LineStatement;

public class IR implements IIR {
	 int size;
//interface
	ArrayList<LineStatement> AssemblyUnit;

	public IR(ArrayList<LineStatement> AssemblyUnit) {
		this.AssemblyUnit = AssemblyUnit;
		size=0;
	}

	public IR() {
		AssemblyUnit = new ArrayList<LineStatement>();
		size=0;
	}

	public ArrayList<LineStatement> getIR() {
		return AssemblyUnit;
	}

	public void setIR(ArrayList<LineStatement> AssemblyUnit) {
		this.AssemblyUnit = AssemblyUnit;
	}

	public void add(LineStatement lineStatement) {
		AssemblyUnit.add(lineStatement);
		size++;
	}

	public int getsize(){
		return size;
	}

	

}
