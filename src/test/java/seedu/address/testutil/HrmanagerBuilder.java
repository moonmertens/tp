package seedu.address.testutil;

import seedu.address.model.Hrmanager;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Hrmanager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class HrmanagerBuilder {

    private Hrmanager HRmanager;

    public HrmanagerBuilder() {
        HRmanager = new Hrmanager();
    }

    public HrmanagerBuilder(Hrmanager HRmanager) {
        this.HRmanager = HRmanager;
    }

    /**
     * Adds a new {@code Person} to the {@code Hrmanager} that we are building.
     */
    public HrmanagerBuilder withPerson(Person person) {
        HRmanager.addPerson(person);
        return this;
    }

    public Hrmanager build() {
        return HRmanager;
    }
}
