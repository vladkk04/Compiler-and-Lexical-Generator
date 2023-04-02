public class Lexer {
    private int current;
    private int value;
    private final String input;
    private final int inputLength;

    public Lexer(String input)
    {
        this.input = input;
        this.current = 0;
        this.inputLength = input.length();
    }

    public Token nextToken()
    {
        if(current < inputLength)
        {
            while (Character.isWhitespace(input.charAt(current)))
            {
                consume();
            }

            switch (input.charAt(current))
            {
                case '+':
                    consume();
                    return Token.PLUS;

                case '-':
                    consume();
                    return Token.MINUS;

                case '*':
                    consume();
                    return Token.MUL;

                case '/':
                    consume();
                    return Token.DIV;

                case '^':
                    consume();
                    return Token.Degree;

                case '⊗':
                {
                    consume();
                    return Token.MUL;
                }

                case '(':
                    consume();
                    return Token.LPAR;

                case ')':
                    consume();
                    return Token.RPAR;
            }

            if (Character.isDigit(input.charAt(current)))
            {
                StringBuilder sb = new StringBuilder();
                do
                {
                    sb.append(input.charAt(current));
                    consume();
                }
                while (current < inputLength && Character.isDigit(input.charAt(current)));
                value = Integer.parseInt(sb.toString());
                return Token.NUMBER;
            }

            throw new RuntimeException("Invalid character: " + input.charAt(current));
        }

        return Token.EOF;
    }

    private void consume()
    {
        current++;
    }

    public int getValue() {
        return value;
    }
}
