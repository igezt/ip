import duke.commands.AddToDoCommand;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.task.Deadline;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class DeadlineTest {
    @Test
    public void deadline_creationCompareWithStatus_isEqual() throws DukeException {

        String[] data = new String[]{"D", " ", "Dumb Dumb", "2000-10-10T10:10"};
        assertEquals("[D][ ] Dumb Dumb (by: Tuesday, 10 October 2000 [10:10 AM])", new Deadline(data).status());
    }
}
