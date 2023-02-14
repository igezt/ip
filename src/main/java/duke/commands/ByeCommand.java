package duke.commands;

import duke.database.Database;
import duke.exception.databaseexceptions.DatabaseNotUpdatingException;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/** Represents a command for shutting down Duke. */
public class ByeCommand extends Command {

    /**
     * Represents a command to shutting down Duke.
     */
    public ByeCommand() {
        super();
    }

    /**
     * Executes the generated ByeCommand by updating isActive to false, updating the database, and gives a response
     * to the Ui.
     *
     * @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @throws DatabaseNotUpdatingException thrown when the database was not updated due to some file update error.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database) throws DatabaseNotUpdatingException {
        this.setIsActive(false);
        database.update(taskList.getTasks());
        ui.response(FRAME
                + "Bye. Hope to see you again soon!\n"
                + FRAME);
    }
}
