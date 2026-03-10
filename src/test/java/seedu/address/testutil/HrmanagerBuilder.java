package seedu.address.testutil;

import seedu.address.model.Hrmanager;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Hrmanager objects.
 * Example usage: <br>
 *     {@code Hrmanager ab = new HrmanagerBuilder().withPerson("John", "Doe").build();}
 */
public class HrmanagerBuilder {

    private Hrmanager hrmanager;

    public HrmanagerBuilder() {
        hrmanager = new Hrmanager();
    }

    public HrmanagerBuilder(Hrmanager hrmanager) {
        this.hrmanager = hrmanager;
    }

    /**
     * Adds a new {@code Person} to the {@code Hrmanager} that we are building.
     */
    public HrmanagerBuilder withPerson(Person person) {
        hrmanager.addPerson(person);
        return this;
    }

    public Hrmanager build() {
        return hrmanager;
    }
}
