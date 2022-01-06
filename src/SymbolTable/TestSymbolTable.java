package SymbolTable;

public class TestSymbolTable {

	public static void main(String[] args) {

		SymbolTable<String,Integer> instructions = new SymbolTable<String,Integer>();
        instructions.put("halt", 0x00);
		instructions.put("pop", 0x01);
		instructions.put("dup", 0x02);

        System.out.print("Test SymbolTable\n");
		System.out.print("address in method : 0\n" + "0\n");
		System.out.print(instructions.getSymbol("halt"));
        System.out.println();
	}
}
