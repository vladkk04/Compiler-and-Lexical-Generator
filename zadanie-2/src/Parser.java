public class Parser {
    private final Lexer lexer;
    private Token symbol;

    public Parser(Lexer lexer)
    {
        this.lexer = lexer;
        symbol = lexer.nextToken();
    }

    public int statement()
    {
        int result = F();
        match(Token.EOF);
        return result;
    }

    // E -> T {"+" | "-" T}
    private int E()
    {
        int result = T();
        while (symbol == Token.PLUS || symbol == Token.MINUS)
        {
            Token op = symbol;
            consume();
            int term = T();

            if (op == Token.PLUS)
            {
                result += term;
            }
            else
            {
                result -= term;
            }
        }
        return result;
    }

    // T -> F {("*" | "/" | "⊗") F}
    private int T()
    {
        int result = P();
        while (symbol == Token.MUL || symbol == Token.DIV || symbol == Token.MUL)
        {
            Token op = symbol;
            consume();
            int factor = T();
            if (op == Token.MUL || op == Token.MUL)
            {
                factor *= result;
                return factor;
            }
            else
            {
                factor /= result;
                return factor;
            }
        }
        return result;
    }

    // F -> P ["^" F]
    private int F()
    {
        int result = E();

        if (symbol == Token.Degree)
        {
            consume();
            result = (int) Math.pow(result, E());
        }
        return result;
    }

    // P -> "(" E ")" | "-" P | number
    private int P()
    {
        int result;
        if (symbol == Token.LPAR)
        {
            consume();
            result = F();
            match(Token.RPAR);
        }
        else if (symbol == Token.MINUS)
        {
            consume();
            result = -P();
        }
        else
        {
            result = lexer.getValue();
            match(Token.NUMBER);
        }
        return result;
    }

    private void match(Token expected)
    {
        if (symbol == expected)
        {
            consume();
        }
        else
        {
            throw new RuntimeException("Syntax error: expected " + expected + ", found " + symbol);
        }
    }

    private void consume() {
        symbol = lexer.nextToken();
    }
}
