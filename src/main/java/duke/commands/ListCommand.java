package duke.commands;

import duke.database.Database;
import duke.exception.DukeException;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/** Represents a command to view the list of all items in the taskList of Duke. */
public class ListCommand extends Command {

    /**
     * Represents a command to view the list of all items in the taskList of Duke.
     */
    public ListCommand() {
        super();
    }

    /**
     * Executes the generated ListCommand by generating the list of tasks in Duke as a String and
     * giving it as a response to the Ui.
     *
     * @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @param undoList list of inverse commands that can be run to undo an action.
     * @param hasUndo if true, will generate an inverse command to undo that specific command if an inverse command
     *                exists.
     * @throws DukeException
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo) throws DukeException {
        assert this.isActive();
        StringBuilder res = new StringBuilder(FRAME);
        for (int i = 0; i < taskList.length(); i++) {
            Task task = taskList.getTask(i + 1);
            res.append(i + 1).append(". ").append(task.getStatus()).append("\n");
        }
        ui.response(res.append(FRAME).toString());
    }

}
