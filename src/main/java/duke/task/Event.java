package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Represents a Event task in Duke.
 */
public class Event extends Task {
    private static final String typeToString = "E";
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    /**
     * Represents a Event task in Duke.
     *
     * @param task task details.
     * @param startDateTime start date and time of the event.
     * @param endDateTime end date and time of the event.
     * @throws DateTimeParseException thrown when either of the date and/or time is not parsable.
     */
    public Event(String task, String startDateTime, String endDateTime) throws DateTimeParseException {
        super(task);
        this.type = Types.EVENT;

        this.startDateTime = LocalDateTime.parse(startDateTime, FORMATTER);
        this.endDateTime = LocalDateTime.parse(endDateTime, FORMATTER);
    }

    /**
     * Represents an Event task in Duke.
     *
     * @param data an array of Strings with relevant information typically obtained from the database in Duke.
     */
    public Event(String[] data) {
        super(data[2]);
        this.isCompleted = Objects.equals(data[1], "X");
        this.startDateTime = LocalDateTime.parse(data[3]);
        this.endDateTime = LocalDateTime.parse(data[4]);
    }

    /**
     * @return the status of the Event task with its time formatted.
     */
    @Override
    public String status() {
        String status = this.isCompleted ? "[X] " : "[ ] ";
        return "[" + typeToString + "]" + status + this.details +
                " (from: " + this.startDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " [" +
                this.startDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "]" + ")" + " to: " +
                this.endDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " [" +
                this.endDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "]" + ")" + ")";
    }

    /**
     * @return all relevant information of the Event task in an ArrayList of Strings to be saved into the Database.
     */
    @Override
    public ArrayList<String> data() {
        ArrayList<String> data = new ArrayList<>();
        data.add(typeToString);
        data.add(this.isCompleted ? "X" : " ");
        data.add(this.details);
        data.add(this.startDateTime.toString());
        data.add(this.endDateTime.toString());
        return data;
    }
}
