package duke.commands;

import duke.database.Database;
import duke.exception.DukeException;
import duke.exception.TaskNumberNotFoundException;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

public class UndoCommand extends Command {

    public UndoCommand() {
        super();
    }

    /**
     * Executes the generated inverse command stored in undoList.
     *
     * @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @param undoList list of inverse commands that can be run to undo an action.
     * @param hasUndo if true, will generate an inverse command to undo that specific command if an inverse command
     *                exists.
     * @throws TaskNumberNotFoundException thrown when there is no task with that taskNumber
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo) throws
            DukeException {
        Command undoCommand = undoList.pop();
        undoCommand.execute(taskList, ui, database, undoList, false);
    }
}
