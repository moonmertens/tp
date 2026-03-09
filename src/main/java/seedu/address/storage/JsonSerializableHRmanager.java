package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Hrmanager;
import seedu.address.model.ReadOnlyHrmanager;
import seedu.address.model.person.Person;

/**
 * An Immutable Hrmanager that is serializable to JSON format.
 */
@JsonRootName(value = "hrmanager")
class JsonSerializableHRmanager {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHRmanager} with the given persons.
     */
    @JsonCreator
    public JsonSerializableHRmanager(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyHrmanager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHRmanager}.
     */
    public JsonSerializableHRmanager(ReadOnlyHrmanager source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Hrmanager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Hrmanager toModelType() throws IllegalValueException {
        Hrmanager HRmanager = new Hrmanager();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (HRmanager.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            HRmanager.addPerson(person);
        }
        return HRmanager;
    }

}
