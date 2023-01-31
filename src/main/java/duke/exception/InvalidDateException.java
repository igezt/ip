package duke.exception;

public class InvalidDateException extends DukeException {

    public InvalidDateException() {
        super("\n" + "    ____________________________________________________________\n" +
                "I can't seem to understand that date..." + "\n" +
                "Can you use DD-MM-YYYY HH:MM if possible?" + "\n" +
                "    ____________________________________________________________\n");
    }

}
