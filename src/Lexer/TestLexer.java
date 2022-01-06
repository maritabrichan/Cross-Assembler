package Lexer;
import java.io.File;
import java.io.IOException;

import Reader.Reader;
import Token.Token;

public class TestLexer {
		
	public static void main(String[] args) {
		
		File f = new File("TestImmediate.asm");
		Reader reader = new Reader(f);
		
		Lexer lexer = new Lexer(reader);

		try {
			
			System.out.print("Test Lexer\n");
			System.out.println("; TestImmediate.asm - Test immediate instructions.");
			System.out.println("enter.u5");
			System.out.println("0");
			
			Token t = lexer.getToken();
				System.out.println(t.getTokenName() + " ");
				t = lexer.getToken();
				System.out.println(t.getTokenName() + " ");
				t = lexer.getToken();
				System.out.print(t.getTokenName() + " ");
				System.out.println();

			
		} catch (IOException e1) {

		}
	}

}
