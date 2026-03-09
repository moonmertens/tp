package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Hrmanager HRmanager;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given Hrmanager and userPrefs.
     */
    public ModelManager(ReadOnlyHrmanager hrmanager, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(hrmanager, userPrefs);

        logger.fine("Initializing with address book: " + hrmanager + " and user prefs " + userPrefs);

        this.HRmanager = new Hrmanager(hrmanager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.HRmanager.getPersonList());
    }

    public ModelManager() {
        this(new Hrmanager(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getHrmanagerFilePath() {
        return userPrefs.getHrmanagerFilePath();
    }

    @Override
    public void setHrmanagerFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setHrmanagerFilePath(addressBookFilePath);
    }

    //=========== Hrmanager ================================================================================

    @Override
    public void setHrmanager(ReadOnlyHrmanager addressBook) {
        this.HRmanager.resetData(addressBook);
    }

    @Override
    public ReadOnlyHrmanager getHrmanager() {
        return HRmanager;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return HRmanager.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        HRmanager.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        HRmanager.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        HRmanager.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return HRmanager.equals(otherModelManager.HRmanager)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
