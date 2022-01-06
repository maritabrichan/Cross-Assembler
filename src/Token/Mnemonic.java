package Token;
public class Mnemonic extends Token {

	public Mnemonic(String tokenName, int opcode) {
		super(tokenName);
		super.opcode = opcode;
	}

	public Mnemonic() {
	}

	public int getOpcode() {
		return opcode;
	}

}


