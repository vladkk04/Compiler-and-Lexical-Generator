import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        try
        {
            Scanner input = new Scanner(System.in);
            Lexer lexer = new Lexer(input.nextLine());
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
