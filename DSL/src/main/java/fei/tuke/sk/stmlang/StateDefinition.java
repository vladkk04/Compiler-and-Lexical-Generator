package fei.tuke.sk.stmlang;

import java.util.*;

public class StateDefinition {
    private final List<String> actions = new ArrayList<>();
    private final List<TransitionDefinition> transitions = new ArrayList<>();

    public void addAction(String name) {
        actions.add(name);
    }

    public void addTransition(TransitionDefinition transition) {

        transitions.add(transition);
        Set<TransitionDefinition> uniqueTransitions = new HashSet<>(transitions);

        if (transitions.size() != uniqueTransitions.size())
        {
            throw new StateMachineException("Duplicate name in state definition! Duplicate name: " + transition.commandName());
        }
    }

    public List<String> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public List<TransitionDefinition> getTransitions() {
        return Collections.unmodifiableList(transitions);
    }

    @Override
    public String toString() {
        return "StateDefinition{" + "actions=" + actions + ", transitions=" + transitions + '}';
    }
}
