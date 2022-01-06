package ErrorReporter;



public class Error {
    
    private String mg;
    private int curPosLine;
	private int curPosCol;
    Position pos= new Position(curPosLine, curPosCol);
  

    public Error(String mg,Position pos){
        this.mg=mg;
       
       this.pos=pos;
      
    }
    public Error(){

    }
    


    public String getmg()
    {
        return mg;

    }

    public Position getpos()
    {
        return pos;
    }
  
   

    public Error checkerrors(){
        
        String message= getmg();
        Error e1 ;
        
        switch(message){
            case "invchar": e1=new Error("Character is invalid", getpos());
            break;

            case "nan": e1=new Error("Not a number", getpos());
            break;

            case "it": e1=new Error("Illegal token", getpos());
            break;
            
            case "lnf": e1=new Error("Label not found (or defined)", getpos());
            break;
            
            case "orl": e1=new Error("Operand must refer to a label.", getpos());
            break;
            
            case "lad": e1 = new Error("Label already defined.", getpos());
            break;

            case "eofS": e1=new Error("End of file in string", getpos());
            break;

            case "eolS": e1=new Error("End of line in string", getpos());
            break;

            case "fcr": e1=new Error("File cannot be read", getpos());
            break;

            case "unF": e1 = new Error("Unidentifiable File", getpos());
            break;

            case "msI": e1 = new Error("Missing operand", getpos());
            break;

            case "OELU5": e1 = new Error("Operand outside limit (unsigned 5-bit)", getpos());
            break;

            case "OELs5": e1 = new Error("Operand outside limit (signed 5-bit)", getpos());
            break;

            case "oelu3": e1 = new Error("Operand outside limit (unsigned 3-bit)", getpos());
            break;

            case "oels3": e1 = new Error("Operand outside limit (signed 3-bit)", getpos());
            break;
            
            case "invdir": e1 = new Error("CString is not ASCII characters", getpos());
            break;

            default: e1 = new Error("Wrong errors",getpos());
            break;
        }
        return e1;
    }
    
}

