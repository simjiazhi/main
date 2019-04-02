package seedu.address.model.reminder;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Reminder}'s date is within the start and end date given.
 */
public class ReminderWithinDatesPredicate implements Predicate<Reminder> {
    private final LocalDate start;
    private final LocalDate end;

    public ReminderWithinDatesPredicate(LocalDate start, LocalDate end) {
        this.start = start.minusDays(1);
        this.end = end.plusDays(1);
    }

    @Override
    public boolean test(Reminder reminder) {
        LocalDate remDate = reminder.getDate();
        return remDate.isAfter(start) && remDate.isBefore(end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderWithinDatesPredicate // instanceof handles nulls
                && start.equals(((ReminderWithinDatesPredicate) other).start)
                && end.equals(((ReminderWithinDatesPredicate) other).end)); // state check
    }
}
