package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.annotations.CmsElement;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
@CmsElement(converter = PhoneNumberConverter.class)
public class PhoneNumber implements Comparable<PhoneNumber> {

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
    public int compareTo(PhoneNumber o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
