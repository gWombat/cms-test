package fr.gwombat.cmstest.converters;

import fr.gwombat.cmstest.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class PersonPostConverter implements PostConverter<Person> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonPostConverter.class);

    @Override
    public void postConvert(Person targetObject) {
        Collections.sort(targetObject.getPhones());
        LOGGER.debug("person's phone list sorted!!");
    }
}
