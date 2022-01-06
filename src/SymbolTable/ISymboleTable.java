package SymbolTable;

import java.util.HashMap;
import java.util.Set;

import Token.Token;


public interface ISymboleTable {
	public Token getValue(String key);

	public HashMap<String, Token> getMap();

	public void put(String key, Token mnemonicToken);

	public int getSymbol(String mnem) ;

	public int size();

	public Set<String> keySet();
}
