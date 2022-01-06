package Parser;

import java.io.IOException;
import ErrorReporter.*;
import ErrorReporter.Error;
import IR.IR;
import Lexer.Lexer;
import LineStatement.LineStatement;
import SymbolTable.SymbolTable;
import Token.Comment;
import Token.Directive;
import Token.ImmediateInstruction;
import Token.InherentInstruction;
import Token.Instruction;
import Token.Label;
import Token.Mnemonic;
import Token.Number;
import Token.RelativeInstruction;
import Token.Token;


public class Parser implements IParser {


	Lexer lexer;
	IR assemblyUnit;
	SymbolTable<String, Mnemonic> tempTable;
	SymbolTable<String, Token> scanTable;
	Error addReporter;
	Token token;
	private ErrorReporter reporter;
	int addr;
	int instNum = 0;

	public Parser(Lexer lexer) {
		assemblyUnit = new IR();

		this.lexer = lexer;
		reporter = new ErrorReporter();
		addReporter = new Error();
		tempTable = lexer.getHashtable();
		addr = 0;
		try {
			token = lexer.getToken();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scanTable = new SymbolTable<String, Token>();
	}

	public IR parse() {
		while (token != null) {
			LineStatement r;
			try {
				r = parseLineStatement();
				assemblyUnit.add(r);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//update offset
		int offset = -10000;
		for (int i = 0; i < assemblyUnit.getsize(); i++) {
			LineStatement line = assemblyUnit.getIR().get(i);
			if (line.getInstruction() != null && line.getInstruction() instanceof RelativeInstruction) {
				if (line.getInstruction().getTokenName().equals("br.i8")) {
					if(scanTable.inSymbolTableKey(line.getInstruction().getOperand())){
						offset = ((Label) scanTable.getValue(line.getInstruction().getOperand())).getAddress() - ((RelativeInstruction) line.getInstruction()).getAddress();
						if (offset >= 0)
							line.getInstruction().setCompletedMachineCode(Integer.toHexString(tempTable.getValue(line.getInstruction().getTokenName()).opcode).toUpperCase() + " " + String.format("%02x", offset).toUpperCase());
						else
							line.getInstruction().setCompletedMachineCode(Integer.toHexString(tempTable.getValue(line.getInstruction().getTokenName()).opcode).toUpperCase() + " " + String.format("%02x", offset).toUpperCase().substring(6));

					}
					else {
						if(!Character.isDigit(line.getInstruction().getOperand().charAt(0)) ) {
							Position p1 = new Position(i+1, 0);
							Error e1 = new Error("lnf", p1);
							Error check = e1.checkerrors();
							reporter.addError(check);
						}
						else {
							Position p1 = new Position(i+1, 0);
							Error e1 = new Error("orl", p1);
							Error check = e1.checkerrors();
							reporter.addError(check);
						}
					}
				} else if (line.getInstruction().getTokenName().equals("lda.i16")) {
					if(scanTable.inSymbolTableKey(line.getInstruction().getOperand())){
						offset = ((Label) scanTable.getValue(line.getInstruction().getOperand())).getAddress() - ((RelativeInstruction) line.getInstruction()).getAddress();
						if (offset >= 0)
							line.getInstruction().setCompletedMachineCode(Integer.toHexString(tempTable.getValue(line.getInstruction().getTokenName()).opcode).toUpperCase() + " " + String.format("%04x", offset).toUpperCase());
						else
							line.getInstruction().setCompletedMachineCode(Integer.toHexString(tempTable.getValue(line.getInstruction().getTokenName()).opcode).toUpperCase() + " " + String.format("%04x", offset).toUpperCase().substring(6));
					}
					else {
						if(!Character.isDigit(line.getInstruction().getOperand().charAt(0)) ) {
							Position p1 = new Position(i+1, 0);
							Error e1 = new Error("lnf", p1);
							Error check = e1.checkerrors();
							reporter.addError(check);
						}
						else {
							Position p1 = new Position(i+1, 0);
							Error e1 = new Error("orl", p1);
							Error check = e1.checkerrors();
							reporter.addError(check);
						}
					}
				}
				((RelativeInstruction) line.getInstruction()).setOffset(offset);
			}
		}

		reporter.printError();
		return assemblyUnit;
	}

	public IR getAssemblyUnit() {
		return assemblyUnit;
	}

	public LineStatement parseLineStatement() throws IOException {
		Instruction instruction = null;
		Label label = null;
		Comment comment = null;
		LineStatement lineStmt = new LineStatement();

		int posLine = lexer.getline();
		while (posLine == lexer.getline()) {
			try {
				if (token instanceof Instruction) {
					if (instruction == null) {
						if (token instanceof InherentInstruction) {
							instruction = this.parseInherentInstruction();
							if (instNum == 1) {
								lineStmt.setAddress(0);
							} else {
								lineStmt.setAddress(addr);
							}
							addr++;
						}
						if (token instanceof ImmediateInstruction) {
							instruction = this.parseImmediateInstruction();
							if (instNum == 1) {
								lineStmt.setAddress(0);
							} else {
								lineStmt.setAddress(addr);
							}
							addr++;
						}
						if (token instanceof Directive) {
							instruction = this.parseDirective();
							if (instNum == 1) {
								lineStmt.setAddress(0);
							} else {
								lineStmt.setAddress(addr);
							}
							addr = addr + 3;
						}
						if (token instanceof RelativeInstruction) {
							instNum++;
							if (instNum == 1) {
								lineStmt.setAddress(0);
							} else {
								lineStmt.setAddress(addr);
							}
							instruction = this.parseRelativeInstruction();
						}
					}

				} else if (token instanceof Comment) {
					comment = this.parseComment();
				} else if (token instanceof Label) {
					label = this.parseLabel();
				}


			} catch (IOException e) {
				e.printStackTrace();
			}


		}
		lineStmt.setComment(comment);
		lineStmt.setLabel(label);
		lineStmt.setInstruction(instruction);

		return lineStmt;
	}

	public Label parseLabel() throws IOException {
		String labelString = token.getTokenName();
		Label label = new Label(labelString);
		if (scanTable.inSymbolTableKey(labelString)) {
			Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
			Error e1 = new Error("lad", p1);
			Error check = e1.checkerrors();
			reporter.addError(check);
		}
		else {
			label.setAddress(addr);
			scanTable.put(labelString, label);
		}
		token = lexer.getToken();
		return label;
	}

	public Comment parseComment() throws IOException {
		String commentString = token.getTokenName();
		Comment cmt = new Comment(commentString);
		token = lexer.getToken();
		return cmt;

	}

	public Instruction parseInherentInstruction() throws IOException {

		Instruction inst = new Instruction(token.getTokenName());
		if (!scanTable.inSymbolTableKey(token.getTokenName())) {
			scanTable.put(token.getTokenName(), inst);
		}
		token = lexer.getToken();
		return inst;
	}

	public ImmediateInstruction parseImmediateInstruction() throws IOException {

		String tokenString = token.getTokenName();
		ImmediateInstruction inst = new ImmediateInstruction(tokenString);
		String length = tokenString.substring(tokenString.indexOf('.') + 1);
		int wrongRng = 0;
		String operand = lexer.getToken().getTokenName();
		inst.setOperand(operand);
		if (inst.getOperand() != null) {
			wrongRng = Integer.parseInt(operand);
		} else {
			Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
			Error e1 = new Error("msI", p1);
			Error check = e1.checkerrors();
			reporter.addError(check);
		}

		switch (length) {
			case "u3":
				if (!(wrongRng >= 0 && wrongRng <= 7)) {
					Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
					Error e1 = new Error("oelu3", p1);
					Error check = e1.checkerrors();
					reporter.addError(check);
				} else {
					inst.opcode = tempTable.getValue(inst.getTokenName()).opcode + wrongRng;
				}
				break;
			case "i3":
				if (!(wrongRng >= -4 && wrongRng <= 3)) {
					Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
					Error e1 = new Error("oels3", p1);
					Error check = e1.checkerrors();
					reporter.addError(check);
				} else {
					if (wrongRng < 0) {
						wrongRng = 8 + wrongRng;
					}
					inst.opcode = tempTable.getValue(inst.getTokenName()).opcode + wrongRng;
				}
				break;
			case "u5":
				if (!(wrongRng >= 0 && wrongRng <= 31)) {
					Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
					Error e1 = new Error("OELU5", p1);
					Error check = e1.checkerrors();
					reporter.addError(check);
				} else {
					inst.opcode = tempTable.getValue(inst.getTokenName()).opcode + wrongRng;
				}
				break;
		}
		if (!scanTable.inSymbolTableKey(tokenString)) {
			scanTable.put(tokenString, inst);
		}
		token = lexer.getToken();
		return inst;
	}

	public RelativeInstruction parseRelativeInstruction() throws IOException {
		String tokenString = token.getTokenName();
		RelativeInstruction inst = new RelativeInstruction(tokenString);
		String length = tokenString.substring(tokenString.indexOf('.') + 1);
		int wrongRng = 0;
		Token operand = lexer.getToken();
		String strOperand = operand.getTokenName();
		inst.setOperand(strOperand);
		@SuppressWarnings("unused")
		int offset = 0;

		if (inst.getOperand() != null) {
			if (operand instanceof Number)
				wrongRng = Integer.parseInt(strOperand);
		} else {
			Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
			Error e1 = new Error("msI", p1);
			Error check = e1.checkerrors();
			reporter.addError(check);
		}

		switch (length) {
			case "i8":
				if (tokenString.equals("br.i8")) {

					inst.setCompletedMachineCode(Integer.toHexString(tempTable.getValue(inst.getTokenName()).opcode).toUpperCase()); 
				} else if (tokenString.equals("ldc.i8")) {
					if (!(wrongRng >= -128 && wrongRng <= 127)) {
						Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
						Error e1 = new Error("oelu3", p1);
						Error check = e1.checkerrors();
						reporter.addError(check);
					} else {
						inst.setCompletedMachineCode(Integer.toHexString(tempTable.getValue(inst.getTokenName()).opcode).toUpperCase() + " " + String.format("%02x", wrongRng).toUpperCase());
					}
				}
				inst.setAddress(addr);
				addr += 2;
				break;

			case "i16":

					inst.setCompletedMachineCode(Integer.toHexString(tempTable.getValue(inst.getTokenName()).opcode).toUpperCase()); 

				inst.setAddress(addr);
				addr += 3;
				break;

			case "u8":
				if (!(wrongRng >= 0 && wrongRng <= 255)) {
					Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
					Error e1 = new Error("OELU5", p1);
					Error check = e1.checkerrors();
					reporter.addError(check);
				} else {
					inst.setCompletedMachineCode(Integer.toHexString(tempTable.getValue(inst.getTokenName()).opcode).toUpperCase() + " " + String.format("%02x", wrongRng).toUpperCase());
				}
				addr += 2;
				break;
		}
		if (!scanTable.inSymbolTableKey(tokenString)) {
			scanTable.put(tokenString, inst);
		}
		token = lexer.getToken();
		return inst;
	}

	public Directive parseDirective() throws IOException {

		Directive directive = new Directive(token.getTokenName());
		String cString = lexer.getToken().getTokenName();
		directive.setOperand(cString);
		directive.setCompletedMachineCode("");
		for (int i = 0; i < cString.length(); i++) {
			int c = cString.charAt(i);
			if (c < 0 || c > 127) {
				Position p1 = new Position(lexer.getCurPosLine(), lexer.getCurPosCol());
				Error e1 = new Error("invdir", p1);
				Error check = e1.checkerrors();
				reporter.addError(check);
			} else {
				if (i == 0)
					directive.setCompletedMachineCode(String.format("%02x", c));
				else
					directive.setCompletedMachineCode(directive.getCompletedMachineCode() + " " + String.format("%02x", c));
			}
		}
		directive.setCompletedMachineCode(directive.getCompletedMachineCode() + " 00");

		token = lexer.getToken();
		return directive;
	}

	public boolean endOfLine() {
		String token = "";
		while (token != "end file") {
			try {
				token = lexer.getToken().getTokenName();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}


	public SymbolTable<String, Token> getScanTable() {
		return scanTable;
	}

	public void verbosePass1() {
		String label = "";
		String instruction = "";
		String stringCode = "";
		String operand = "";

		for (int i = 0; i < assemblyUnit.getsize(); i++) {
			LineStatement line = assemblyUnit.getIR().get(i);


			if (line.getLabel() != null) {
				label = line.getLabel().getTokenName();
			}

			if (line.getInstruction() != null) {
				instruction = line.getInstruction().getTokenName();

				operand = line.getInstruction().getOperand();
				if (line.getInstruction() instanceof RelativeInstruction) {
					if (line.getInstruction().getTokenName().equals("br.i8") && ((RelativeInstruction) line.getInstruction()).getOffset() > 0) {
						stringCode = line.getInstruction().getCompletedMachineCode().substring(0, 2) + " ??";
					} else if (line.getInstruction().getTokenName().equals("lda.i16") && ((RelativeInstruction) line.getInstruction()).getOffset() > 0) {

						stringCode = line.getInstruction().getCompletedMachineCode().substring(0, 2) + " ????";
					} else
						stringCode = line.getInstruction().getCompletedMachineCode();

				} else
					stringCode = line.getInstruction().getCompletedMachineCode();
			}
			if (line.getAddress() < 16 && line.getInstruction() != null) {
				System.out.printf("%-5s %-5s %-15s %-10s %-10s %-20s \n", i + 1, "000" + Integer.toHexString(line.getAddress()).toUpperCase(), stringCode, label, instruction, operand);
			} else if (line.getAddress() >= 16 && line.getInstruction() != null)
				System.out.printf("%-5s %-5s %-15s %-10s %-10s %-20s \n", i + 1, "00" + Integer.toHexString(line.getAddress()).toUpperCase(), stringCode, label, instruction, operand);

			label = "";
			instruction = "";
		}

	}

	public void verbosePass2() {
		String label = "";
		String instruction = "";
		String stringCode = "";
		String operand = "";

		for (int i = 0; i < assemblyUnit.getsize(); i++) {
			LineStatement line = assemblyUnit.getIR().get(i);


			if (line.getLabel() != null) {
				label = line.getLabel().getTokenName();
			}

			if (line.getInstruction() != null) {
				instruction = line.getInstruction().getTokenName();
				operand = line.getInstruction().getOperand();
				stringCode = line.getInstruction().getCompletedMachineCode();
			}
			if (line.getAddress() < 16 && line.getInstruction() != null) {
				System.out.printf("%-5s %-5s %-15s %-10s %-10s %-20s \n", i + 1, "000" + Integer.toHexString(line.getAddress()).toUpperCase(), stringCode, label, instruction, operand);
			} else if (line.getAddress() >= 16 && line.getInstruction() != null)
				System.out.printf("%-5s %-5s %-15s %-10s %-10s %-20s \n", i + 1, "00" + Integer.toHexString(line.getAddress()).toUpperCase(), stringCode, label, instruction, operand);

			label = "";
			instruction = "";
		}
	}
}
