package fei.tuke.sk.stmlang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
    public static void main(String[] args)
    {
        if (args.length != 2) {
            System.err.println("Usage: java Main <input file> <output file>");
            System.exit(1);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]));
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            StateMachineDefinition definition = parser.stateMachine();
            Generator generator = new Generator(definition, writer);
            generator.generate();
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        } catch (StateMachineException e) {
            System.err.println("Error parsing state machine definition: " + e.getMessage());
        }
    }
}
