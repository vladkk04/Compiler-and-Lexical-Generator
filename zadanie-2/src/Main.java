import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args)
    {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Enter an arithmetic expression:");
            String input = reader.readLine();
            Lexer lexer = new Lexer(new StringReader(input));
            int result = new Parser(lexer).statement();

            System.out.printf("\nResult: %d\n", result);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        new ParserTests();
    }
}
