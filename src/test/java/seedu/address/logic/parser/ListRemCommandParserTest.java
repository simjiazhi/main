package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListRemCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.ListRemCommandParser.PREFIX_FORMAT;
import static seedu.address.logic.parser.ParserUtil.FORMAT_DAY;
import static seedu.address.logic.parser.ParserUtil.FORMAT_MONTH;
import static seedu.address.logic.parser.ParserUtil.FORMAT_WEEK;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ListRemCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListRemCommandParserTest {
    private ListRemCommandParser parser = new ListRemCommandParser();

    private String formatStringDay = FORMAT_DAY;
    private String formatStringWeek = FORMAT_WEEK;
    private String formatStringMonth = FORMAT_MONTH;
    private String dateString = "2019-10-23";

    private LocalDate date = LocalDate.parse(dateString);

    @Test
    public void parse_noFieldsPresent_success() throws ParseException {
        List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
        assertParseSuccess(parser, "", new ListRemCommand(dates.get(0), dates.get(1)));
    }

    @Test
    public void parse_allRequiredFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(date, date));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE);

        // missing format prefix
        assertParseFailure(parser,
                formatStringDay + " "
                        + PREFIX_DATE + dateString,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                PREFIX_FORMAT + formatStringWeek + " "
                        + dateString,
                expectedMessage);

        // missing date and format prefix
        assertParseFailure(parser,
                formatStringMonth + " "
                        + dateString,
                expectedMessage);
    }

    @Test
    public void parse_invalidFormatInput_failure() {
        String invalidFormat = "year";
        String expectedMessage = ParserUtil.MESSAGE_INVALID_FORMAT;

        // all prefixes included
        assertParseFailure(parser,
                "              "
                        + PREFIX_FORMAT + invalidFormat + " "
                        + PREFIX_DATE + dateString,
                expectedMessage);
    }

    @Test
    public void parse_formatDateRange_success() {
        // format == day
        LocalDate start = LocalDate.parse("2019-10-23");
        LocalDate end = LocalDate.parse("2019-10-23");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-21");
        end = LocalDate.parse("2019-10-27");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringWeek + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-01");
        end = LocalDate.parse("2019-10-31");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringMonth + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(start, end));
    }
}
