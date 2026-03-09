package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyHrmanager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Hrmanager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HrmanagerStorage HRmanagerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code HrmanagerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HrmanagerStorage HRmanagerStorage, UserPrefsStorage userPrefsStorage) {
        this.HRmanagerStorage = HRmanagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Hrmanager methods ==============================

    @Override
    public Path getHrmanagerFilePath() {
        return HRmanagerStorage.getHrmanagerFilePath();
    }

    @Override
    public Optional<ReadOnlyHrmanager> readAddressBook() throws DataLoadingException {
        return readAddressBook(HRmanagerStorage.getHrmanagerFilePath());
    }

    @Override
    public Optional<ReadOnlyHrmanager> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return HRmanagerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveHrmanager(ReadOnlyHrmanager addressBook) throws IOException {
        saveHrmanager(addressBook, HRmanagerStorage.getHrmanagerFilePath());
    }

    @Override
    public void saveHrmanager(ReadOnlyHrmanager addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        HRmanagerStorage.saveHrmanager(addressBook, filePath);
    }

}
