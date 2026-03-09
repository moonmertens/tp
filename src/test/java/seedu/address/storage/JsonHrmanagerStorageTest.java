package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Hrmanager;
import seedu.address.model.ReadOnlyHrmanager;

public class JsonHrmanagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHrmanagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyHrmanager> readAddressBook(String filePath) throws Exception {
        return new JsonHrmanagerStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveHrmanager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Hrmanager original = getTypicalAddressBook();
        JsonHrmanagerStorage jsonHRmanagerStorage = new JsonHrmanagerStorage(filePath);

        // Save in new file and read back
        jsonHRmanagerStorage.saveHrmanager(original, filePath);
        ReadOnlyHrmanager readBack = jsonHRmanagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new Hrmanager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonHRmanagerStorage.saveHrmanager(original, filePath);
        readBack = jsonHRmanagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new Hrmanager(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonHRmanagerStorage.saveHrmanager(original); // file path not specified
        readBack = jsonHRmanagerStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new Hrmanager(readBack));

    }

    @Test
    public void saveAddressBook_nullHrmanager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHrmanager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveHrmanager(ReadOnlyHrmanager addressBook, String filePath) {
        try {
            new JsonHrmanagerStorage(Paths.get(filePath))
                    .saveHrmanager(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHrmanager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHrmanager(new Hrmanager(), null));
    }
}
