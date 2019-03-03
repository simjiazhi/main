package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_COMMENT;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_END;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_NRIC;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_START;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to quickdocs.
 */
public class AddAppCommand extends Command {

    public static final String COMMAND_WORD = "appadd";
    //public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to quickdocs. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "START "
            + PREFIX_END + "END "
            + PREFIX_COMMENT + "COMMENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9625555I "
            + PREFIX_DATE + "2019-10-23 "
            + PREFIX_START + "16:00 "
            + PREFIX_END + "17:00 "
            + PREFIX_COMMENT + "<any comments>\n";

    public static final String MESSAGE_SUCCESS = "Appointment added:\n%1$s\n";
    public static final String MESSAGE_DUPLICATE_APP = "The time slot has already been taken";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with the given nric found";

    private final Nric nric;
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final String comment;

    /**
     * Creates an AddAppCommand to add the specified {@code Appointment}
     */
    public AddAppCommand(Nric nric, LocalDate date, LocalTime start, LocalTime end, String comment) {
        this.nric = nric;
        this.date = date;
        this.start = start;
        this.end = end;
        this.comment = comment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Optional<Patient> patientToAdd = model.getPatientWithNric(nric);
        if (!patientToAdd.isPresent()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Appointment appToAdd = new Appointment(patientToAdd.get(), date, start, end, comment);
        if (model.duplicateApp(appToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APP);
        }

        model.addApp(appToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppCommand // instanceof handles nulls
                && nric.equals(((AddAppCommand) other).nric));
    }
}
