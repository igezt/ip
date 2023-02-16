package duke.parser;

import java.util.Objects;

import duke.commands.AddDeadlineCommand;
import duke.commands.AddEventCommand;
import duke.commands.AddToDoCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.commands.MarkCommand;
import duke.commands.UndoCommand;
import duke.commands.UnmarkCommand;
import duke.exception.TaskNumberNotFoundException;
import duke.exception.blankfieldexceptions.BlankFieldDeadlineException;
import duke.exception.blankfieldexceptions.BlankFieldEventException;
import duke.exception.blankfieldexceptions.BlankFieldTodoException;
import duke.exception.includeexceptions.IncludeByException;
import duke.exception.includeexceptions.IncludeToAndFromException;
import duke.exception.parserexceptions.NoCommandBodyException;
import duke.exception.parserexceptions.UnknownCommandError;


/**
 * Represents the wrapper for the parsing logic for commands for Duke.
 */
public class Parser {


    /**
     * Parses the command text given and returns the Command object associated with the command text.
     *
     * @param command the command text given.
     * @param lengthOfList the length of taskList currently
     * @return Command object associated with the command text.
     * @throws NoCommandBodyException thrown when there is no command body
     * @throws TaskNumberNotFoundException thrown when there is no task identifier found for mark, unmark, delete
     *      commands.
     * @throws BlankFieldTodoException thrown when there is a blank field for the addToDoCommand.
     * @throws BlankFieldDeadlineException thrown when there is a blank field for the addDeadlineCommand.
     * @throws BlankFieldEventException thrown when there is a blank field for the addEventCommand.
     * @throws UnknownCommandError thrown when the command's keyword does not match any of the ones known.
     */
    public Command parse(String command, int lengthOfList) throws TaskNumberNotFoundException, BlankFieldTodoException,
            BlankFieldDeadlineException, BlankFieldEventException, UnknownCommandError, NoCommandBodyException, IncludeToAndFromException, IncludeByException {
        if (Objects.equals(command, "list")) {
            return new ListCommand();
        } else if (Objects.equals(command, "bye")) {
            return new ByeCommand();
        } else if (Objects.equals(command, "undo")) {
            return new UndoCommand();
        }


        String[] words = command.split(" ", 2);

        if (words.length <= 1) {
            throw new NoCommandBodyException();
        }

        String keyWord = words[0];
        String commandBody = words[1];

        switch (keyWord) {
        case "mark":
            return parseMarkCommand(commandBody, lengthOfList);

        case "unmark":
            return parseUnmarkCommand(commandBody, lengthOfList);

        case "todo":
            return parseAddToDoCommand(commandBody);

        case "deadline":
            return parseAddDeadlineCommand(commandBody);

        case "event":
            return parseAddEventCommand(commandBody);

        case "delete":
            return parseDeleteCommand(commandBody, lengthOfList);

        case "find":
            return parseFindCommand(commandBody);
        default:
            throw new UnknownCommandError();
        }
    }

    private FindCommand parseFindCommand(String commandBody) {
        return new FindCommand(commandBody);
    }

    private DeleteCommand parseDeleteCommand(String commandBody, int lengthOfList) throws TaskNumberNotFoundException {
        try {
            int taskNumber = Integer.parseInt(commandBody);
            if (taskNumber > lengthOfList || taskNumber <= 0 || commandBody.trim().isEmpty()) {
                throw new TaskNumberNotFoundException();
            }
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new TaskNumberNotFoundException();
        }
    }

    private AddEventCommand parseAddEventCommand(String commandBody) throws BlankFieldEventException,
            IncludeToAndFromException {
        if (commandBody.trim().isEmpty()) {
            throw new BlankFieldEventException();
        }
        return new AddEventCommand(commandBody);
    }

    private AddDeadlineCommand parseAddDeadlineCommand(String commandBody) throws BlankFieldDeadlineException, IncludeByException {
        if (commandBody.trim().isEmpty()) {
            throw new BlankFieldDeadlineException();
        }
        return new AddDeadlineCommand(commandBody);
    }

    private AddToDoCommand parseAddToDoCommand(String commandBody) throws BlankFieldTodoException {
        if (commandBody.trim().isEmpty()) {
            throw new BlankFieldTodoException();
        }
        return new AddToDoCommand(commandBody);
    }

    private MarkCommand parseMarkCommand(String commandBody, int lengthOfList) throws TaskNumberNotFoundException {
        try {
            int taskNumber = Integer.parseInt(commandBody);
            if (taskNumber > lengthOfList || taskNumber <= 0 || commandBody.trim().isEmpty()) {
                throw new TaskNumberNotFoundException();
            }
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new TaskNumberNotFoundException();
        }
    }

    private UnmarkCommand parseUnmarkCommand(String commandBody, int lengthOfList) throws TaskNumberNotFoundException {
        try {
            int taskNumber = Integer.parseInt(commandBody);
            if (taskNumber > lengthOfList || taskNumber <= 0 || commandBody.trim().isEmpty()) {
                throw new TaskNumberNotFoundException();
            }
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new TaskNumberNotFoundException();
        }
    }
}
