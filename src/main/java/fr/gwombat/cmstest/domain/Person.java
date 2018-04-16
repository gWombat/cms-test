package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.annotations.CmsProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@CmsElement
public class Person {

    @CmsProperty(name = "nom")
    private String            name;
    private String            firstname;
    private int               age;
    private List<PhoneNumber> phones;
    private Map<String, Long> idTests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PhoneNumber> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneNumber> phones) {
        this.phones = phones;
    }

    public Map<String, Long> getIdTests() {
        return idTests;
    }

    @CmsProperty(name = "id_test")
    public void setIdTests(Map<String, Long> idTests) {
        this.idTests = idTests;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                ", phones=" + phones +
                '}';
    }
}
