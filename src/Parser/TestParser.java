package Parser;

import java.io.File;
import java.io.IOException;
import Lexer.Lexer;
import LineStatement.LineStatement;
import Reader.Reader;

public class TestParser {
    public static void main(String args[]) throws IOException
    {
        File file = new File("TestImmediate.asm");
        Reader reader= new Reader(file);
        Lexer lexer = new Lexer(reader);
        Parser parser=new Parser(lexer);
        LineStatement line=parser.parseLineStatement();
        LineStatement line2=parser.parseLineStatement();
        
        System.out.print("Test Parser\n");
        System.out.println("; TestImmediate.asm - Test immediate instructions.");
        System.out.print("enter.u5 ");
        System.out.print("0 ");
        System.out.print("; OK, number <u5> [0..31].\n");
        System.out.println(line.getComment().getTokenName());     
        System.out.print(line2.getInstruction().getTokenName() + " ");
        System.out.print(line2.getInstruction().getOperand() + " ");
        System.out.print(line2.getComment().getTokenName());     
 

       
       System.out.println(); 


    }
}
