package Lexer;

import java.io.IOException;
import ErrorReporter.Error;
import ErrorReporter.ErrorReporter;
import ErrorReporter.Position;
import Reader.Reader;
import SymbolTable.SymbolTable;
import Token.Comment;
import Token.Directive;
import Token.ImmediateInstruction;
import Token.InherentInstruction;
import Token.Label;
import Token.Mnemonic;
import Token.Number;
import Token.RelativeInstruction;
import Token.Token;


public class Lexer implements ILexer {
	private int read;
	private int curPosLine;
	private int curPosCol;
	private Reader reader;
	SymbolTable<String,Mnemonic> hashtable;
	private ErrorReporter reporter;


	public void setposition(int line, int column) {
		curPosCol = column;
		curPosLine = line;
	}

	public int getline() {
		return curPosLine;
	}

	public Lexer(Reader reader) {
		curPosCol = 1;
		curPosLine = 1;
		this.reader = reader;
		createSymbolTable();
		reporter = new ErrorReporter();
	}

	public void createSymbolTable() {
		hashtable = new SymbolTable<String,Mnemonic>();

		//inherent instructions
		hashtable.put("halt",new Mnemonic("halt", 0x00));
		hashtable.put("pop", new Mnemonic("pop", 0x01));
		hashtable.put("dup", new Mnemonic("dup", 0x02));
		hashtable.put("exit", new Mnemonic("exit", 0x03));
		hashtable.put("ret", new Mnemonic("ret", 0x04));
		hashtable.put("not", new Mnemonic("not", 0x0C));
		hashtable.put("and", new Mnemonic("and", 0x0D));
		hashtable.put("or", new Mnemonic("or", 0x0E));
		hashtable.put("xor", new Mnemonic("xor", 0x0F));
		hashtable.put("neg", new Mnemonic("neg", 0x10));
		hashtable.put("inc", new Mnemonic("inc", 0x11));
		hashtable.put("dec", new Mnemonic("dec", 0x12));
		hashtable.put("add", new Mnemonic("add", 0x13));
		hashtable.put("sub", new Mnemonic("sub", 0x14));
		hashtable.put("mul", new Mnemonic("mul", 0x15));
		hashtable.put("div", new Mnemonic("div", 0x16));
		hashtable.put("rem", new Mnemonic("rem", 0x17));
		hashtable.put("shl", new Mnemonic("shl", 0x18));
		hashtable.put("shr", new Mnemonic("shr", 0x19));
		hashtable.put("teq", new Mnemonic("teq", 0x1A));
		hashtable.put("tne", new Mnemonic("tne", 0x1B));
		hashtable.put("tlt", new Mnemonic("tlt", 0x1C));
		hashtable.put("tgt", new Mnemonic("tgt", 0x1D));
		hashtable.put("tle", new Mnemonic("tle", 0x1E));
		hashtable.put("tge", new Mnemonic("tge", 0x1F));

		//immediate instruction
		hashtable.put("enter.u5", new Mnemonic("enter.u5", 0x70));
		hashtable.put("ldc.i3", new Mnemonic("ldc.i3", 0x90));
		hashtable.put("addv.u3", new Mnemonic("addv.u3", 0x98));
		hashtable.put("ldv.u3", new Mnemonic("ldv.u3", 0xA0));
		hashtable.put("stv.u3", new Mnemonic("stv.u3", 0xA8));

		//relative instruction
		hashtable.put("br.i8", new Mnemonic("br.i8", 0xE0));
		hashtable.put("brf.i8", new Mnemonic("brf.i8", 0xE3));
		hashtable.put("ldc.i8", new Mnemonic("ldc.i8", 0xD9));
		hashtable.put("ldv.u8", new Mnemonic("ldv.u8", 0xB1));
		hashtable.put("stv.u8", new Mnemonic("stv.u8", 0xB2));
		hashtable.put("lda.i16", new Mnemonic("lda.i16", 0xD5));
	}

	public SymbolTable<String,Mnemonic> getHashtable(){
		return hashtable;
	}

	@Override
	public Token getToken() throws IOException {
		int c = ' ';
		while (c != -1) {

			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_' || c=='.') {

				return scanIdentifier(c);
			}

			else if(c=='"') {
				return scanString(c);
			}

			else if(c==';')
			{
				return scanComment(c);
			}

			else if(c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9'||c=='0'||c=='-'){
				return scanNumber(c);

			}

			else if (c == '\n' || c == '\r') {
				while (c == '\n' || c == '\r') {
					c = read();
					curPosLine = curPosLine + 1;
					curPosCol=1;
				}
			}

			else if (c == ' ' || c == '\t') {
				c = read();
			}
			else
			{
				Position p1 = new Position(curPosLine,curPosCol);
				Error e1=new Error("il",p1);
				Error check = e1.checkerrors();
				reporter.addError(check);				
				read();
				return null;
			}


		}
		return null;

	}

	@Override
	public String getTokenPosition() {
		return "current line position is" + curPosLine + "current column position is" + curPosCol;

	}

	public Token scanIdentifier(int c) throws IOException {
		String identifier = "";

		while (c != '\n' && c != ' ' && c != '\t'&& c != '\r') {
			char character = (char) c;
			identifier = identifier + character;
			c = read();
			if (!Character.isAlphabetic(c) && !Character.isDigit(c) && !(c=='.') && !(c == '\n' || c == '\r') && !(c==' ' || c=='\t')){
				Position p1 = new Position(curPosLine,curPosCol);
				Error e1=new Error("invchar",p1);
				Error check = e1.checkerrors();
				reporter.addError(check);
			}
			curPosCol++;
		}
		if (c == '\n' && c == '\r') {
			curPosLine ++;
		}
		if(identifier.equals(".cstring")) {
			Directive token = new Directive(identifier);
			token.setColumnNumber(curPosCol);
			token.setRollNumber(curPosLine);
			return token;
		}
		else if(hashtable.inSymbolTableKey(identifier)){
			int code = (hashtable.getValue(identifier)).getOpcode();
			if(code >= 0X00 && code<=0x1F) {
				InherentInstruction token = new InherentInstruction(identifier);
				token.setOpcode(code);
				token.setColumnNumber(curPosCol);
				token.setRollNumber(curPosLine);
				return token;
			}
			else if(code>0x1F && code<=0xAF) {
				ImmediateInstruction token = new ImmediateInstruction(identifier);
				token.setOpcode(code);
				token.setColumnNumber(curPosCol);
				token.setRollNumber(curPosLine);
				return token;
			}
			else {
				RelativeInstruction token = new RelativeInstruction(identifier);
				token.setOpcode(code);
				token.setColumnNumber(curPosCol);
				token.setRollNumber(curPosLine);
				return token;
			}
		}


		else {
			Label token = new Label(identifier);
			return token;
		}
	}


	public Comment scanComment(int c) throws IOException {
		String comment = "";
		while(c != '\r' && c != '\n'&& c!='\0') {
			char ch = (char) c;
			comment = comment + ch;
			c = read();
		}
		Comment t = new Comment(comment);
		t.setColumnNumber(curPosCol++);
		t.setRollNumber(curPosLine);
		curPosCol=1;
		return t;
	}

	public Number scanNumber(int c) throws IOException {
		String number = "";
		while(c != '\r' && c != '\n'&& c!='\0'&& c!=' '&& c!='\t') {
			char ch = (char) c;
			number = number+ ch;
			c = read();
		}	try{
			Integer.parseInt(number);
			return new Number(number);

		}catch (NumberFormatException e){
			Position p1 = new Position(curPosLine,curPosCol);
			Error e1=new Error("nan",p1);
			Error check = e1.checkerrors();
			reporter.addError(check);
		}
		Number t = new Number(number);
		t.setColumnNumber(curPosCol = curPosCol + 1);
		t.setRollNumber(curPosLine);
		if(c=='\n'||c=='\r')
		{
			curPosLine = curPosLine + 1;
			curPosCol=1;
		}
		return t;
	}

	public Token scanString(int c) {
		String cstring = "";
		c = read();
		while(c != '\r' && c != '\n'&& c!='\0' && c!='"') {	
			char ch = (char) c;
			cstring = cstring+ ch;
			c = read();
		}
		Token t = new Token(cstring);
		t.setColumnNumber(curPosCol = curPosCol + 1);
		t.setRollNumber(curPosLine);
		if(c=='\n'||c=='\r')
		{
			curPosLine = curPosLine + 1;
			curPosCol=1;
		}
		return t;
	}



	public int read() {
		try {
			read = reader.read();
		}catch(IOException e)
		{
			Position p1 = new Position(curPosLine,curPosCol);
			Error e1=new Error("fcr",p1);
			Error check = e1.checkerrors();
			reporter.addError(check);
			System.exit(1);
		}
		return read;
	}



	public void setCurPosLine(int curPosLine) {
		this.curPosLine = curPosLine;
	}

	public int getCurPosCol() {
		return curPosCol;
	}

	public int getCurPosLine() {
		return curPosLine;
	}


}
