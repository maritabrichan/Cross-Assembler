package LineStatement;
import Token.Comment;
import Token.Instruction;
import Token.Label;

public class LineStatement {
    

    Instruction instruction;
	Label label;
	Comment comment;
	int address;
	
    public LineStatement(Label label,Instruction instruction,  Comment comment)
    {
        this.instruction=instruction;
        this.label=label;
        this.comment=comment;
        this.address = 0;
    }
    
    public LineStatement() {
    	this.instruction = new Instruction();
    	this.comment = new Comment();
    	this.label = new Label();
    }
  

    public Instruction getInstruction(){
       return instruction;
        
    }
    public Label getLabel(){
        return label;
    }

    public Comment getComment(){
        return comment;
    }
    
    public void setComment(Comment cmt) {
    	this.comment = cmt;
    }
    
    public void setLabel(Label label) {
    	this.label = label;
    }
    
    public void setInstruction(Instruction inst) {
    	this.instruction = inst;
    }
    
    public void setAddress(int addr) {
    	this.address = addr;
    }
    
    public int getAddress() {
    	return address;
    }

    


}
