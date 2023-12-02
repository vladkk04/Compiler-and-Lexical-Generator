package fei.tuke.sk.stmlang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
Grammar:
        * StateMachine  -> { Statement }
        * Statement     -> Commands | ResetCommands | Events | State
        * Commands      -> "commands" "{" { NAME CHAR } "}"
        * Events        -> "events" "{" { NAME CHAR } "}"
        * ResetCommands -> "resetCommands" "{" { NAME } "}"
        * State         -> "state" "{" [Actions] { Transition } "}"
        * Actions       -> "actions" "{" { NAME } "}"
        * Transition    -> NAME "->" NAME
 */

public class Parser {
    private final Lexer lexer;
    private Token symbol;
    private StateMachineDefinition definition;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public StateMachineDefinition stateMachine() {
        definition = new StateMachineDefinition();
        consume();
        while (symbol.tokenType() != TokenType.EOF) {
            switch (symbol.tokenType()) {
                case EVENTS:
                    events();
                    break;
                case COMMANDS:
                    commands();
                    break;
                case RESET_COMMANDS:
                    resetCommands();
                    break;
                case STATE:
                    state();
                    break;
                default:
                    throw new StateMachineException("Unexpected symbol: " + symbol);
            }
        }

        // Perform some semantic checks on the states and transitions.
        checkStateAndTransitionDefinitions();

        return definition;
    }

    private void checkStateAndTransitionDefinitions()
    {
        // Check if there is at least one state defined
        if (definition.getStates().isEmpty()) {
            throw new StateMachineException("At least one state must be defined!");
        }

        // Check if the initial state is defined
        if (!definition.getStates().containsKey(definition.getInitialStateName())) {
            throw new StateMachineException("Initial state \"" + definition.getInitialStateName() + "\" is not defined!");
        }

        // Check if there are any transitions that reference an undefined state
        for (StateDefinition state : definition.getStates().values()) {
            for (TransitionDefinition transition : state.getTransitions()) {
                if (!definition.getStates().containsKey(transition.targetName())) {
                    throw new StateMachineException("State \"" + transition.targetName() + "\" is referenced in a transition but not defined!");
                }
            }
        }
    }
    private void events() {
        match(TokenType.EVENTS);
        match(TokenType.LBRACE);
        while (symbol.tokenType() == TokenType.NAME) {
            String name = symbol.attribute();
            consume();
            String value = symbol.attribute();
            match(TokenType.CHAR);
            definition.addEvent(name, value.charAt(0));
        }
        match(TokenType.RBRACE);
    }

    private void commands() {
        match(TokenType.COMMANDS);
        match(TokenType.LBRACE);
        while (symbol.tokenType() == TokenType.NAME) {
            String name = symbol.attribute();
            consume();
            String value = symbol.attribute();
            match(TokenType.CHAR);
            definition.addCommand(name, value.charAt(0));
        }
        match(TokenType.RBRACE);
    }

    private void resetCommands()
    {
        match(TokenType.RESET_COMMANDS);
        match(TokenType.LBRACE);
        while (symbol.tokenType() == TokenType.NAME)
        {
            String name = symbol.attribute();
            consume();
            definition.addResetCommand(name);
        }
        match(TokenType.RBRACE);
    }
    private void state() {
        StateDefinition currentState  = new StateDefinition();
        match(TokenType.STATE);

        String newStateName = symbol.attribute();
        consume();
        match(TokenType.LBRACE);

        if (symbol.tokenType() == TokenType.ACTIONS)
        {
            checkingSymbols(currentState);
        }

        addTransitionForDefinition(currentState);

        match(TokenType.RBRACE);
        definition.addState(newStateName, currentState);
    }

    private void checkingSymbols(StateDefinition newDefinition)
    {
        consume();
        match(TokenType.LBRACE);
        while (symbol.tokenType() == TokenType.NAME)
        {
            String name = symbol.attribute();
            newDefinition.addAction(name);
            consume();
        }
        match(TokenType.RBRACE);
    }

    private void addTransitionForDefinition(StateDefinition newDefinition)
    {
        while (symbol.tokenType() == TokenType.NAME)
        {
            newDefinition.addTransition(transition());
        }
    }

    private TransitionDefinition transition()
    {
        String eventName = symbol.attribute();
        match(TokenType.NAME);
        match(TokenType.ARROW);

        String targetName = symbol.attribute();
        match(TokenType.NAME);

        return new TransitionDefinition(eventName, targetName);
    }

    private void match(TokenType expectedSymbol)
    {
        if (symbol.tokenType().equals(expectedSymbol))
        {
            consume();
        }
        else
        {
            throw new StateMachineException("Match error in token: " + symbol.tokenType() + ", but excepted: " + expectedSymbol);
        }
    }
    private void consume()
    {
        symbol = lexer.nextToken();
    }
}
