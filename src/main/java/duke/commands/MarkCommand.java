package duke.commands;

import duke.database.Database;
import duke.exception.TaskNumberNotFoundException;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/** Represents a command for marking an existing task in the taskList of Duke. */
public class MarkCommand extends Command {

    private final int taskNumber;

    /**
     * Represents a command for marking an existing task in the taskList of Duke.
     *
     * @param taskNumber The identifier of the task.
     */
    public MarkCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the generated MarkCommand by marking the relevant task in Duke as complete and
     * giving it as a response to the Ui.
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
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo) throws TaskNumberNotFoundException {
        assert this.isActive();
        Task task = taskList.getTask(this.taskNumber);
        task.setComplete();
        ui.response(FRAME
                + "Nice! I've marked this task as done:\n"
                + "[X] " + task.getDetails() + "\n"
                + FRAME);
        if (hasUndo) {
            undoList.add(new UnmarkCommand(this.taskNumber));
        }
    }

}
