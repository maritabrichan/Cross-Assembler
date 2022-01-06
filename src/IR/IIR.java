package IR;
import java.util.ArrayList;

import LineStatement.LineStatement;

public interface IIR {
	
	public ArrayList<LineStatement> getIR();
	
	public void setIR(ArrayList<LineStatement> AssemblyUnit);
	
	public void add(LineStatement lineStatement);
	
	public int getsize();
}
