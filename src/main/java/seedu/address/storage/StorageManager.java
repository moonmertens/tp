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
    private HrmanagerStorage hrmanagerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code hrmanagerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HrmanagerStorage hrmanagerStorage, UserPrefsStorage userPrefsStorage) {
        this.hrmanagerStorage = hrmanagerStorage;
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
        return hrmanagerStorage.getHrmanagerFilePath();
    }

    @Override
    public Optional<ReadOnlyHrmanager> readHrmanager() throws DataLoadingException {
        return readHrmanager(hrmanagerStorage.getHrmanagerFilePath());
    }

    @Override
    public Optional<ReadOnlyHrmanager> readHrmanager(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return hrmanagerStorage.readHrmanager(filePath);
    }

    @Override
    public void saveHrmanager(ReadOnlyHrmanager hrmanager) throws IOException {
        saveHrmanager(hrmanager, hrmanagerStorage.getHrmanagerFilePath());
    }

    @Override
    public void saveHrmanager(ReadOnlyHrmanager hrmanager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        hrmanagerStorage.saveHrmanager(hrmanager, filePath);
    }

}
