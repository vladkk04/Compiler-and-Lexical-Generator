import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private int current;
    private int value;
    private final Reader reader;

    public Lexer(Reader reader)
    {
        this.reader = reader;
        consume();
    }

    public Token nextToken()
    {
        while (Character.isWhitespace(current)) {
            consume();
        }

        switch (current) {
            case '+':
                consume();
                return Token.PLUS;

            case '-':
                consume();
                return Token.MINUS;

            case '*', '⊗':
                consume();
                return Token.MUL;

            case '/':
                consume();
                return Token.DIV;

            case '^':
                consume();
                return Token.Degree;

            case '(':
                consume();
                return Token.LPAR;

            case ')':
                consume();
                return Token.RPAR;
        }

        if (Character.isDigit(current)) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append((char) current);
                consume();
            } while (current >= '0' && current <= '9');
            value = Integer.parseInt(sb.toString());
            return Token.NUMBER;
        }

        if (current == -1)
        {
            return Token.EOF;
        }

        throw new RuntimeException("Invalid character: " + (char) current);
    }

    private void consume()
    {
        try
        {
            current = reader.read();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }
}
