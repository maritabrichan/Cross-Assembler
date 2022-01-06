package Token;

public class RelativeInstruction extends Instruction{
	int address;
	int offset;

	public RelativeInstruction (String tokenName) {
		super(tokenName);
	}
	public int getAddress() {
		return address;
	}
	
	public void setAddress(int address) {
		this.address = address;
	}
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}