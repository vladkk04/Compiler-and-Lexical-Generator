import java.io.StringReader;
import java.io.Reader;

public class ParserTests {
    public ParserTests() {
        test();
    }

    private void test() {
        testAddition();
        testDivision();
        testMultiplication();
        testPower();
        testSubtraction();
        testIntegerDivision();
        testNegativeNumber();
        testNestedParentheses();
    }

    private void testAddition() {
        String input = "2 + 3";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 5 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testSubtraction() {
        String input = "10 - 5";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 5 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testMultiplication() {
        String input = "3 * 4";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 12 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testDivision() {
        String input = "10 / 2";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 121 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testIntegerDivision() {
        String input = "7 ⊗ 2";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 14 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testPower() {
        String input = "2^3";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 8 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testNegativeNumber() {
        String input = "-5";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == -5 : "Error";
        System.out.println("Result is correct: " + result);
    }

    private void testNestedParentheses() {
        String input = "(((4 + 3) * 2) - 5) ⊗ 2";
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        int result = parser.statement();
        assert result == 18 : "Error";
        System.out.println("Result is correct: " + result);
    }
}
