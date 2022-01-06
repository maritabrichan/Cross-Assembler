package Token;

public class Label extends Token{
	int address;
	
	public Label(String tokenName) {
		super(tokenName);
	}
	
	public Label() {
		super();
	}
	
	public int getAddress() {
		return address;
	}
	
	public void setAddress(int address) {
		this.address = address;
	}
}
