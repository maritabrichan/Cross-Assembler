package Token;

public class Instruction extends Token {
	String operand;
	String completedMachineCode;
	
	public Instruction(String tokenName) {
		super(tokenName);
		completedMachineCode ="  ";
	}
	
	public Instruction() {
		completedMachineCode = "  ";
	}
	
	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	public String getOperand() {
		return operand;
	}
	
	public void setCompletedMachineCode(String completedMachineCode) {
		this.completedMachineCode = completedMachineCode;
	}
	
	public String getCompletedMachineCode() {
		return completedMachineCode;
	}
	
}