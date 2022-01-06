package ErrorReporter;


public class TestErrorReporter {
    public static void main(String args[]){
    
    	

        Position p1 = new Position(2,3);        //position class for indetifing line and column
        Error e1= new Error("invchar",p1);      //error obj with error type and psotion object
        Error check = e1.checkerrors();   //check the type of error that has occured

       


        
        ErrorReporter printer = new ErrorReporter();   //create error reproter obj
        printer.addError(check);                // record the error
                    

        System.out.print("Test ErrorReporter\n");
        System.out.print("Error: at line 2 : character is invalid\n");
        System.out.println("1 Errors");
        printer.printError();       //print the error message
        System.out.println();


    }
    
}
