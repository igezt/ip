package duke.commands;

import duke.database.Database;
import duke.exception.TaskNumberNotFoundException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/** Represents a command to delete a task stored in from Duke. */
public class DeleteCommand extends Command {

    private final int taskNumber;

    /**
     * Represents a command to delete a task stored in from Duke.
     *
     * @param taskNumber The identifier of the task.
     */
    public DeleteCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the generated DeleteCommand by deleting the task related to the taskNumber
     * and gives a response to the Ui.
     *
     * @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @param undoList list of inverse commands that can be run to undo an action.
     * @param hasUndo if true, will generate an inverse command to undo that specific command if an inverse command
     *                exists.
     * @throws TaskNumberNotFoundException thrown when the taskNumber identifier does not exist in the taskList of Duke.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo)
            throws TaskNumberNotFoundException {
        assert this.isActive();
        Task deletedTask = taskList.getTask(taskNumber);
        String taskDescription = deletedTask.getStatus();
        taskList.deleteTask(taskNumber);
        ui.response(FRAME
                + " Noted. I've removed this task:\n"
                + taskDescription
                + "Now you have " + taskList.length() + " tasks in the list." + "\n"
                + FRAME);

        if (hasUndo) {
            switch (deletedTask.getType()) {
            case TODO:
                assert deletedTask instanceof ToDo;
                undoList.add(new AddToDoCommand((ToDo) deletedTask));
                break;
            case EVENT:
                assert deletedTask instanceof Event;
                undoList.add(new AddEventCommand((Event) deletedTask));
                break;
            case DEADLINE:
                assert deletedTask instanceof Deadline;
                undoList.add(new AddDeadlineCommand((Deadline) deletedTask));
                break;
            default:
                //Should not reach here.
            }
        }
    }
}
