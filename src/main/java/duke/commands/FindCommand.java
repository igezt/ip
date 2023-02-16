package duke.commands;

import java.util.ArrayList;

import duke.database.Database;
import duke.exception.DukeException;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/**
 * Represents a find command to find a task with the relevant query string.
 */
public class FindCommand extends Command {

    private final String query;

    /**
     * Represents a find command to find a task with the relevant query string.
     *
     * @param query query string.
     */
    public FindCommand(String query) {
        this.query = query;
    }

    /**
     * Executes the generated FindCommand by finding a list of tasks in Duke that contain the query string in its
     * details and giving it as a response to the Ui.
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
        ArrayList<Task> tasks = taskList.getTasks();
        StringBuilder queriedTasks = new StringBuilder(FRAME);
        int numOfQueriedTasks = 0;
        for (Task task : tasks) {
            if (task.isContains(this.query)) {
                queriedTasks.append("     ").append(numOfQueriedTasks + 1).append(". ")
                        .append(task.getStatus()).append("\n");
                numOfQueriedTasks++;
            }
        }
        ui.response(queriedTasks.append(FRAME).toString());
    }
}
