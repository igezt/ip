package duke.undolist;
import java.util.ArrayList;

import duke.commands.Command;

/**
 * Stores an ArrayList of commands that can be executed when the undo command is run.
 */
public class UndoList {
    private final ArrayList<Command> undoListOfCommands;

    public UndoList() {
        this.undoListOfCommands = new ArrayList<>();
    }

    public void add(Command c) {
        this.undoListOfCommands.add(c);
    }

    /**
     * Pops the latest undoCommand to be executed;
     * @return latest undoCommand.
     */
    public Command pop() {
        Command undoCommand = this.undoListOfCommands.get(this.undoListOfCommands.size() - 1);
        this.undoListOfCommands.remove(this.undoListOfCommands.size() - 1);
        return undoCommand;
    }
}
