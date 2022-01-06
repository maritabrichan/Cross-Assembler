package Reader;

import java.io.IOException;

public interface IReader {
    int getEof();
    int read() throws IOException;
}
