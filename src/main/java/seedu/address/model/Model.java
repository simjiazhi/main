package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Reminder> getFilteredReminderList();

    ReadOnlyProperty<Reminder> selectedReminderProperty();

    void setSelectedReminder(Reminder reminder);


    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    /**
     * Removes the given {@code tag} from all {@code Person}s.
     */
    void deleteTag(Tag tag);
    //void addMedicine(String medicineName, String[] path);

    //void addMedicine(String medicineName, int quantity, String[] path);

    //void addDirectory(String directoryName, String[] path);

    //Optional<Medicine> findMedicine(String medicineName);

    //Optional<Medicine> findMedicine(String[] path);

    //void purchaseMedicine(String[] path, int quantity);

    //void purchaseMedicine(String medicineName, int quantity);

    //Optional<Directory> findDirectory(String[] path);

    //===========Patient module operations============================
    boolean duplicatePatient(Patient patient);

    void addPatient(Patient patient);

    boolean isPatientListEmpty();

    boolean checkValidIndex(int index);

    Patient getPatientAtIndex(int index);

    boolean checkDuplicatePatientAfterEdit(int index, Patient editedPatient);

    void replacePatient(int index, Patient editedPatient);

    String findPatientsByName(String searchSequence);

    String listFiftyPatients();

    String findPatientsByNric(String searchSequence);

    String findPatientsByTag(Tag tag);

    Patient getPatientByNric(String nric);

    //==========Consultation methods=====================

    void createConsultation(Patient patient);

    Optional<Patient> getPatientWithNric(Nric nric);

    void diagnosePatient(Diagnosis diagnosis);

    //===========Appointment module operations========================
    boolean duplicateApp(Appointment app);

    void addApp(Appointment app);

    String listApp();

    //===========Reminder module operations===========================
    boolean duplicateRem(Reminder rem);

    void addRem(Reminder rem);

    String listRem();

}
