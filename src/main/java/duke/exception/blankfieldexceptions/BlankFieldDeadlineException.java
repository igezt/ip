package duke.exception.blankfieldexceptions;

public class BlankFieldDeadlineException extends BlankFieldException{
    public BlankFieldDeadlineException() {
        super("\n" + "    ____________________________________________________________\n" + "\n" +
                "     ☹ OOPS!!! The description or date of a deadline cannot be empty." + "\n" +
                "    ____________________________________________________________\n");
    }
}
