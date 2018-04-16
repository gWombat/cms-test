package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.converters.PhoneNumberConverter;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
@CmsElement(converter = PhoneNumberConverter.class)
public class PhoneNumber {

    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
