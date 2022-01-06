package Generator;
import java.io.File;
import java.io.IOException;

import Lexer.Lexer;
import Parser.Parser;
import Reader.Reader;

public class TestGenerator {

	public static void main(String[] args) throws IOException {
	    File file = new File("rela01.asm");
        Reader reader= new Reader(file);
        Lexer lexer = new Lexer(reader);
        Parser parser=new Parser(lexer);
        Generator generator = new Generator(parser);
        
        parser.parse();
		generator.generateLstFile();
		generator.generateExeFile();
		
		
	}

}
