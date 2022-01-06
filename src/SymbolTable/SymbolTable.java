package SymbolTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import Token.Instruction;
import Token.Label;

public class SymbolTable<K,Token> {
	private int size;
	private HashMap<K, Token> symbolTable;

	

	public SymbolTable() {
		symbolTable = new HashMap<>();
		size=0;
	}

	public Token getValue(K key) {
		return symbolTable.get(key);
	}


	public HashMap<K, Token> getMap() {
		return symbolTable;
	}

	public void put(K key, Token mnemonicToken) {
		symbolTable.put(key, mnemonicToken);
		size++;

	}

	public Token getSymbol(String mnem) {
		System.out.println("address in method : " + symbolTable.get(mnem));
		return symbolTable.get(mnem);
	}

	public int size(){
		return size;
	}

	public Set<K> keySet() {
		return symbolTable.keySet();
	}

   public boolean inSymbolTableKey(K name) {
       return symbolTable.containsKey(name);
   }
   public boolean inSymbolTableValue(Token value){
		return symbolTable.containsValue(value);
   }
   
   @SuppressWarnings("rawtypes")
public void printSymbolTable() {
	    Iterator it = symbolTable.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        System.out.printf("%-10s",pair.getKey());
	        if(pair.getValue() instanceof Label) {
	        	Label tempLabel = (Label) pair.getValue();
	        	System.out.printf("%-10s","Label");
	        	System.out.print(String.format("%04x", tempLabel.getAddress()).toUpperCase());
	        }
	        else if(pair.getValue() instanceof Instruction) {
	        	Instruction tempInstruction = (Instruction) pair.getValue();
	        	System.out.printf("%-10s","Mnemonic");
	        	System.out.print(tempInstruction.getCompletedMachineCode().substring(0,2));
	        }
	        System.out.println();
	        it.remove();
	    }
	}
}
