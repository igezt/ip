package duke.commands;

import duke.database.Database;
import duke.task.ToDo;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/** Represents a command to add a toDo task to the TaskList. */

public class AddToDoCommand extends Command {


    private final ToDo task;

    /**
     * Represents a command to add a todo task to the TaskList.
     *
     * @param task Task to be done.
     */
    public AddToDoCommand(String task) {
        super();
        this.task = new ToDo(task);
    }

    /**
     * Represents a command to add a todo task to the TaskList.
     *
     * @param toDo toDo task object.
     */
    public AddToDoCommand(ToDo toDo) {
        super();
        this.task = toDo;
    }

    /**
     * Executes the generated AddToDoCommand by adding a new todo task into the TaskList and gives a response
     * to the Ui.
     *  @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @param undoList list of inverse commands that can be run to undo an action.
     * @param hasUndo  if true, will generate an inverse command to undo that specific command if an inverse command
     *                 exists.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo) {
        assert this.isActive();
        taskList.addTask(task);
        ui.response(FRAME + "\n"
                + "Got it. I've added this task:" + "\n"
                + task.getStatus() + "\n"
                + "Now you have " + taskList.length() + " tasks in the list" + "\n"
                + FRAME);
        if (hasUndo) {
            undoList.add(new DeleteCommand(taskList.length()));
        }
    }
}
