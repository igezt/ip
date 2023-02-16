package duke.commands;

import java.time.format.DateTimeParseException;
import java.util.Objects;

import duke.commands.enums.AddEventParserLogic;
import duke.database.Database;
import duke.exception.InvalidDateException;
import duke.exception.blankfieldexceptions.BlankFieldEventException;
import duke.exception.includeexceptions.IncludeToAndFromException;
import duke.task.Event;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.undolist.UndoList;

/** Represents a command to add an event task to the TaskList. */
public class AddEventCommand extends Command {
    private final Event event;

    /**
     * Represents a command to add an event task to the TaskList.
     *
     * @param commandBody Parameters of the command, pre-parsed.
     * @throws IncludeToAndFromException /to and/or /from was not included in the command.
     * @throws BlankFieldEventException No task, from date and/or to date was included in the command.
     */
    public AddEventCommand(String commandBody) throws IncludeToAndFromException, BlankFieldEventException {
        super();
        assert this.isActive();
        // Extract event's start date and end date
        String[] lines = commandBody.split(" ");
        // State = 0 if extracting duke.task item
        // State = 1 if extracting start date
        // State = 2 if extracting end date
        AddEventParserLogic state = AddEventParserLogic.inTask;
        StringBuilder task = new StringBuilder();
        StringBuilder startDate = new StringBuilder();
        StringBuilder endDate = new StringBuilder();

        for (String line : lines) {
            if (Objects.equals(line, "/from") && state == AddEventParserLogic.inTask) {
                state = AddEventParserLogic.inStartDate;
                continue;
            } else if (Objects.equals(line, "/to") && state == AddEventParserLogic.inStartDate) {
                state = AddEventParserLogic.inEndDate;
                continue;
            }

            switch (state) {
            case inTask:
                task.append(" ").append(line);
                break;
            case inStartDate:
                startDate.append(" ").append(line);
                break;
            case inEndDate:
                endDate.append(" ").append(line);
                break;
            default:
                //will never reach here.
            }
        }

        if (state != AddEventParserLogic.inEndDate) {
            throw new IncludeToAndFromException();
        }

        if (task.toString().trim().isEmpty()
                || startDate.toString().trim().isEmpty()
                || endDate.toString().trim().isEmpty()) {
            throw new BlankFieldEventException();
        }
        this.event = new Event(task.toString(), startDate.toString().stripLeading(),
                endDate.toString().stripLeading());
    }

    public AddEventCommand(Event task) {
        this.event = task;
    }


    /**
     * Executes the generated AddEventCommand by adding a new Event task into the TaskList and gives a response
     * to the Ui.
     *
     * @param taskList taskList of Duke.
     * @param ui user interface object of Duke.
     * @param database database of Duke.
     * @param undoList list of inverse commands that can be run to undo an action.
     * @param hasUndo  if true, will generate an inverse command to undo that specific command if an inverse command
     *                 exists.
     * @throws InvalidDateException Date given in the command is invalid.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Database database, UndoList undoList, boolean hasUndo) throws InvalidDateException {
        try {
            taskList.addTask(this.event);
            ui.response(FRAME + "\n"
                    + "     Got it. I've added this task:" + "\n"
                    + "     " + this.event.getStatus() + "\n"
                    + "     Now you have " + taskList.length() + " tasks in the list" + "\n"
                    + FRAME);
            if (hasUndo) {
                undoList.add(new DeleteCommand(taskList.length()));
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }
}
