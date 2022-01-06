package IR;
import LineStatement.LineStatement;
import Token.Comment;
import Token.Instruction;
import Token.Label;

public class TestIR {

    public static void main(String args[]) {

Instruction m1 = new Instruction("halt");
Instruction m2 = new Instruction("pop");
Instruction m3 = new Instruction("exit");
       

    Label l1 = new Label("END");
    Label l2 = new Label("LOOP");
    Label l3 = new Label("HERE");
    Comment c1 = new Comment(";hello");
    Comment c2 = new Comment(";hi");
    Comment c3 = new Comment(";people");

    LineStatement ls1 = new LineStatement(l1, m1, c1);
    LineStatement ls2 = new LineStatement(l2, m2, c2);
    LineStatement ls3 = new LineStatement(l3, m3, c3);

    IR i= new IR();

    i.add(ls1);
    i.add(ls2);
    i.add(ls3);

    System.out.print("Test IR\n");
    System.out.println("3");
    System.out.println(i.getsize());
    System.out.println();

}
}
