package Lexer;
import java.io.IOException;

import Token.Token;

public interface ILexer {
	Token getToken() throws IOException;

	String getTokenPosition();
}
