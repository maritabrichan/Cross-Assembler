package ErrorReporter;

public class Position {
    private int curPosLine;
	private int curPosCol;

    public Position(int curPosLine, int curPosCol){
        this.curPosCol=curPosCol;
        this.curPosLine=curPosLine;
    }
    public Position(){

    }

    public  int getcurPosLine()
    {
        return curPosLine;
    }
    public int getcurPosCol()
    {
        return curPosCol;
    }

    
    
    
}