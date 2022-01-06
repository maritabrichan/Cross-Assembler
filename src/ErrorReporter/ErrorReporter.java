package ErrorReporter;

import java.util.ArrayList;

public class ErrorReporter implements IErrorReporter {
    static ArrayList<Error> errors ;
    Error E = new Error();
    public ErrorReporter(){
        errors = new ArrayList<Error>();
    }
    public ErrorReporter(Error E){
       this.E=E;
    
    }
    
    public void addError(Error E){
        
        errors.add(E);
        
   }

	@Override
	public void printError() {
        int c =0;   
        for(int i=0; i<errors.size();i++){
         System.out.println("Error: at line " + errors.get(i).getpos().getcurPosLine() +" : "+ errors.get(i).getmg() );  
         c++   ;
        }
        System.out.println(c+ " Errors");      
	}
   
}

