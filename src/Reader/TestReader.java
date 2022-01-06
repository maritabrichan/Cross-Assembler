package Reader;

// Step 4 - Create the final release version and run it.

import java.io.File;
import java.io.IOException;

// Release (Final) Version - Reader from a file input (character array) stream.

public class TestReader {
    public static void main(String[] args) {
        System.out.println("Test Reader");
        
        File f = new File("TestImmediate.asm");

       Reader r= new Reader(f);
        try {
			r.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
      System.out.println("Reading the file was successful");
    }
}
