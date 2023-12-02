package fei.tuke.sk.stmlang;

import java.io.IOException;
import java.io.Reader;

/**
 * Lexical analyzer of the state machine language.
 */
public class Lexer
{
    private final Reader input;
    private String previousToken = "";
    private final String empty = "";
    private static final char EOF_EVENT = (char) -1;

    public Lexer(Reader input)
    {
        this.input = input;
    }
    public Token nextToken()
    {
        String memoryString = callNextToken();

        // Use a switch statement to identify the type of token
        return switch (memoryString)
        {
            case "events" -> new Token(TokenType.EVENTS);
            case "resetCommands" -> new Token(TokenType.RESET_COMMANDS);
            case "commands" -> new Token(TokenType.COMMANDS);
            case "state" -> new Token(TokenType.STATE);
            case "actions" -> new Token(TokenType.ACTIONS);
            case "=>" -> new Token(TokenType.ARROW);
            case "{" -> new Token(TokenType.LBRACE);
            case "}" -> new Token(TokenType.RBRACE);
            case "EOF-EVENT" -> new Token(TokenType.EOF);
            case ";" -> new Token(TokenType.SEMICOLON);
            case "," -> new Token(TokenType.COMMA);
            case "=" -> new Token(TokenType.EQUAL);
            case "(" -> new Token(TokenType.LPAREN);
            case ")" -> new Token(TokenType.RPAREN);
            case ":" -> new Token(TokenType.COLON);
            case "\"" -> new Token(TokenType.STRING);
            case " " -> new Token(TokenType.WHITESPACE);
            default -> readNameOrKeyword(memoryString);
        };
    }

    private String callNextToken() {
        String strToken = null;
        try
        {
            if (previousToken.isEmpty())
            {
                strToken = consume();
            }
            else
            {
                strToken = previousToken;
                previousToken = empty;
            }
        } catch (IOException e) {
            throw new StateMachineException("Error in getting the next token");
        } finally {
            if (strToken == null) {
                previousToken = empty;
            }
            else if(strToken.isEmpty())
            {
                previousToken = empty;
            }
        }
        return strToken;
    }

    private Token readNameOrKeyword(String nameKeyword)
    {
        if (nameKeyword.startsWith("'") || nameKeyword.endsWith("'"))
        {
            if (!nameKeyword.matches("'[A-Za-z]'"))
            {
                throw new StateMachineException("Error in keyword value! Expected 'char', got: " + nameKeyword);
            }
            return new Token(TokenType.CHAR, nameKeyword.replaceAll("'", ""));
        }
        else if (nameKeyword.matches("[A-Za-z_1-9]+"))
        {
            return new Token(TokenType.NAME, nameKeyword);
        }
        throw new StateMachineException("Unexpected token(keyword): " + nameKeyword);
    }

    private String consume() throws IOException
    {
        char readSymbol = getReadSymbol();

        while (Character.isWhitespace(readSymbol))
        {
            readSymbol = (char) input.read();
        }

        if (readSymbol == EOF_EVENT)
        {
            return "EOF-EVENT";
        }
        else if (readSymbol == '{' )
        {
            return Character.toString(readSymbol);
        }
        else if(readSymbol == '}')
        {
            return Character.toString(readSymbol);
        }

        StringBuilder stringBuilder = new StringBuilder();

        do
        {
            stringBuilder.append(readSymbol);

            if (stringBuilder.toString().endsWith(String.valueOf(TokenType.ARROW)) && stringBuilder.length() > 2)
            {
                previousToken = String.valueOf(TokenType.ARROW);
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                break;
            }
            else if (stringBuilder.toString().startsWith(String.valueOf(TokenType.ARROW)) && stringBuilder.length() > 2)
            {
                stringBuilder.append(consume());
                stringBuilder.delete(0, 2);
                previousToken = stringBuilder.toString();
                return String.valueOf(TokenType.ARROW);
            }

            readSymbol = (char) input.read();

            if (readSymbol == '{' )
            {
                previousToken = Character.toString(readSymbol);
                break;
            }
            else if(readSymbol == '}')
            {
                previousToken = Character.toString(readSymbol);
                break;
            }

        } while (!Character.isWhitespace(readSymbol));

        return stringBuilder.toString();
    }

    char getReadSymbol() throws IOException {
        return (char) input.read();
    }

    void check()
    {

    }
}
