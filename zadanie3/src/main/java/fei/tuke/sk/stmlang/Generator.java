package fei.tuke.sk.stmlang;

import java.io.IOException;
import java.io.Writer;

public class Generator {
    private final StateMachineDefinition stateMachine;
    private final Writer writer;

    public Generator(StateMachineDefinition stateMachine, Writer writer)
    {
        this.stateMachine = stateMachine;
        this.writer = writer;
    }

    public void generate() throws IOException
    {
        try (writer)
        {
            writer.write("#include \"common.h\"\n\n");

            writeStateFunctionDeclarations();
            writer.write("\n");

            writeStateFunctions();

            writeMainFunction();
        }
    }

    private void writeStateFunctionDeclarations()
    {
        for (String stateNumber : stateMachine.getStates().keySet())
        {
            try {
                writer.write("void state_" + stateNumber + "();\n");
            } catch (IOException e) {
                throw new StateMachineException("Error in get states!");
            }
        }
    }

    private void writeStateFunctions() throws IOException {
        for (String stateNumber : stateMachine.getStates().keySet()) {
            StateDefinition state = stateMachine.getStates().get(stateNumber);

            // Write state function header
            writer.write("void state_" + stateNumber + "() {\n");

            writeStateFunctionActions(state);

            writeStateFunctionTransitions(state);

            writer.write("}\n\n");
        }
    }

    private void writeStateFunctionActions(StateDefinition state) throws IOException {
        // Write actions
        for (String action : state.getActions()) {
            Character event = stateMachine.getEvents().get(action);
            writer.write("\tsend_event('" + event + "');\n");
        }
    }

    private void writeStateFunctionTransitions(StateDefinition state) throws IOException {
        // Write transitions and reset commands
        if (!state.getTransitions().isEmpty() || !stateMachine.getResetCommands().isEmpty()) {
            writer.write("\tchar ev;\n\twhile (ev = read_command()) {\n");
            writer.write("\t\tswitch (ev) {\n");
        }

        writeStateFunctionTransitionsCases(state);

        if (!state.getTransitions().isEmpty() || !stateMachine.getResetCommands().isEmpty()) {
            writer.write("\t\t}\n\t}\n");
        }
    }

    private void writeStateFunctionTransitionsCases(StateDefinition state) throws IOException {
        // Write transitions
        for (TransitionDefinition transition : state.getTransitions()) {
            Character command = stateMachine.getCommands().get(transition.commandName());
            writer.write("\t\tcase '" + command  + "':\n");
            writer.write("\t\t\treturn state_" + transition.targetName() + "();\n");
        }

        // Write reset commands
        for (String resetCommand : stateMachine.getResetCommands()) {
            Character command1 = stateMachine.getCommands().get(resetCommand);
            writer.write("\t\tcase '" + command1 + "':\n");
            writer.write("\t\t\treturn state_" + stateMachine.getInitialStateName() + "();\n");
        }
    }

    private void writeMainFunction() throws IOException {
        writer.write("void main() {\n");
        if (stateMachine.getInitialStateName() != null) {
            writer.write("\tstate_" + stateMachine.getInitialStateName() + "();\n");
        }
        writer.write("}");
    }
}