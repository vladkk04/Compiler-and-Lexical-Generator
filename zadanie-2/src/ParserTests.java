public class ParserTests
{
    public ParserTests()
    {
        Test();
    }
    private void Test()
    {
        testAddition();
        testDivision();
        testMultiplication();
        testPower();
        testSubtraction();
        testIntegerDivision();
        testNegativeNumber();
        testNestedParentheses();
    }
    private void testAddition()
    {
        Lexer lexer = new Lexer("2 + 3");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 5 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testSubtraction() {
        Lexer lexer = new Lexer("10 - 5");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 5 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testMultiplication() {
        Lexer lexer = new Lexer("3 * 4");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 12 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testDivision() {
        Lexer lexer = new Lexer("10 / 2");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 5 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testIntegerDivision()
    {
        Lexer lexer = new Lexer("7 ⊗ 2");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 14 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testPower()
    {
        Lexer lexer = new Lexer("2^3");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 8 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testNegativeNumber() {
        Lexer lexer = new Lexer("-5");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == -5 : "Error";
        System.out.println("Result is correct: " + result);

    }

    private void testNestedParentheses() {
        Lexer lexer = new Lexer("(((4 + 3) * 2) - 5) ⊗ 2");
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 14 : "Error";
        System.out.println("Result is correct: " + result);

    }
}
