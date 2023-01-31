package duke.commands;

import duke.database.Database;
import duke.task.Event;
import duke.exception.BlankFieldExceptions.BlankFieldEventException;
import duke.exception.IncludeExceptions.IncludeToAndFromException;
import duke.exception.InvalidDateException;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.time.format.DateTimeParseException;
import java.util.Objects;

public class AddEventCommand extends Command {


    private final String commandBody;

    public AddEventCommand(String commandBody) {
        super();
        this.commandBody = commandBody;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Database database) throws IncludeToAndFromException, BlankFieldEventException, InvalidDateException {
        // Extract event's start date and end date
        String[] lines = this.commandBody.split(" ");
        // State = 0 if extracting duke.task item
        // State = 1 if extracting start date
        // State = 2 if extracting end date
        int state = 0;
        StringBuilder task = new StringBuilder();
        StringBuilder startDate = new StringBuilder();
        StringBuilder endDate = new StringBuilder();

        for (String line : lines) {
            if (Objects.equals(line, "/from") && state == 0) {
                state = 1;
            } else if (Objects.equals(line, "/to") && state == 1) {
                state = 2;
            } else switch (state) {
            case 0:
                task.append(" ").append(line);
                break;
            case 1:
                startDate.append(" ").append(line);
                break;
            case 2:
                endDate.append(" ").append(line);
                break;
            }
        }

        if (state != 2) {
            throw new IncludeToAndFromException();
        }

        if (task.toString().trim().isEmpty() ||
                startDate.toString().trim().isEmpty() ||
                endDate.toString().trim().isEmpty()) {
            throw new BlankFieldEventException();
        }

        try {
            Event newEvent = new Event(task.toString(), startDate.toString().stripLeading(), endDate.toString().stripLeading());
            taskList.addTask(newEvent);
            ui.response(FRAME + "\n" +
                    "     Got it. I've added this duke.task:" + "\n" +
                    "     " + newEvent.status() + "\n" +
                    "     Now you have " + taskList.length() + " tasks in the list" + "\n" +
                    FRAME);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }
}
