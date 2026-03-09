package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Hrmanager;
import seedu.address.model.ReadOnlyHrmanager;

/**
 * Represents a storage for {@link Hrmanager}.
 */
public interface HrmanagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHrmanagerFilePath();

    /**
     * Returns Hrmanager data as a {@link ReadOnlyHrmanager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHrmanager> readAddressBook() throws DataLoadingException;

    /**
     * @see #getHrmanagerFilePath()
     */
    Optional<ReadOnlyHrmanager> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHrmanager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHrmanager(ReadOnlyHrmanager addressBook) throws IOException;

    /**
     * @see #saveHrmanager(ReadOnlyHrmanager)
     */
    void saveHrmanager(ReadOnlyHrmanager addressBook, Path filePath) throws IOException;

}
