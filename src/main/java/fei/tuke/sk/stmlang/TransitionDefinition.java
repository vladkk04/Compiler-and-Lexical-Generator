package fei.tuke.sk.stmlang;

public record TransitionDefinition(String commandName, String targetName)
{
    
    @Override
    public boolean equals(Object o)
    {

        if (!(o instanceof TransitionDefinition t)) {
            return false;
        }

        return this.commandName.equals(t.commandName);
    }

    @Override
    public int hashCode() {
        return commandName.hashCode();
    }
}