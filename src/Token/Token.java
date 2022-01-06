package Token;

public class Token  implements IToken {
	private String tokenName;
	private int rollNumber;
	private int columnNumber;
	public int opcode;

	public Token(){
		tokenName="";
        rollNumber=1;
        columnNumber=1;
        opcode=0;
	}

	public String getTokenName() {
		return tokenName;
	}

	public Token(String tokenName) {
		this.tokenName = tokenName;
	}
	
	public int getOpcode() {
		return opcode;
	}
	
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	

	public void print(Token t1) {
		System.out.println("the name of mnemonic is" + getTokenName());

	}

	public void setColumnNumber(int columnNumber){
		this.columnNumber =columnNumber;
	}
	
	public int getColumnNumber(){
		return columnNumber;
	}

	public void setRollNumber(int rollNumber){
		this.rollNumber =rollNumber;
		
	}

	public int getRollNumber(){
		return rollNumber;
	}

}

