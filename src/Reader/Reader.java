package Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Reader implements IReader {
	private final static int Eof = -1;

	public Reader(File file) {
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

	}

	@Override
	public int getEof() {
		return Eof;
	}

	@Override
	public int read() throws IOException {
			return inputStream.read();
	}

	private FileInputStream inputStream;
}