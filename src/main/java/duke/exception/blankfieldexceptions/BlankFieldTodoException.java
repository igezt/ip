package duke.exception.blankfieldexceptions;

public class BlankFieldTodoException extends BlankFieldException{
    public BlankFieldTodoException() {
        super("\n" + "    ____________________________________________________________\n" + "\n" +
                "     ☹ OOPS!!! The description of a todo cannot be empty." + "\n" +
                "    ____________________________________________________________\n");
    }
}
