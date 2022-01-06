package LineStatement;

import Token.Comment;
import Token.Instruction;
import Token.Label;


public class TestLineStatement {
    public static void main(String args[]){
    Instruction m1 = new Instruction("halt");
       

    Label l1 = new Label("END");
    Comment c1 = new Comment(";hello");


    LineStatement ls1 = new LineStatement(l1, m1, c1);
    
    System.out.print("Test LineStatement\n");
    System.out.print("halt\n");
    System.out.println(ls1.getInstruction().getTokenName());
    System.out.println();
}
}