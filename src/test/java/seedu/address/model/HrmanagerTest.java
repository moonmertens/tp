package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class HrmanagerTest {

    private final Hrmanager HRmanager = new Hrmanager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), HRmanager.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> HRmanager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Hrmanager newData = getTypicalAddressBook();
        HRmanager.resetData(newData);
        assertEquals(newData, HRmanager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withRole(VALID_ROLE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        HrmanagerStub newData = new HrmanagerStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> HRmanager.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> HRmanager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(HRmanager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        HRmanager.addPerson(ALICE);
        assertTrue(HRmanager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        HRmanager.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withRole(VALID_ROLE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(HRmanager.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> HRmanager.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Hrmanager.class.getCanonicalName() + "{persons=" + HRmanager.getPersonList() + "}";
        assertEquals(expected, HRmanager.toString());
    }

    /**
     * A stub ReadOnlyHrmanager whose persons list can violate interface constraints.
     */
    private static class HrmanagerStub implements ReadOnlyHrmanager {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        HrmanagerStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
