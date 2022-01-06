package LineStatement;
import Token.Instruction;
import Token.Token;

public interface ILineStatement {
    Instruction getInstruction();
    public Token getLabel();
    public Token getComment();


}
