package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.mapping.annotations.CmsElement;
import fr.gwombat.cmstest.mapping.annotations.CmsProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
// 1. with wrapper:
//@CmsElement(postConverters = PersonPostConverter.class)
// 2. without wrapper:
@CmsElement(nodeName = "my-page/person", postConverters = PersonPostConverter.class)
public class Person {

    @CmsProperty(name = "nom")
    private String            name;
    private String            firstname;
    private int               age;
    private List<PhoneNumber> phones;
    private List<Integer>     intList;
    private Map<String, Long> idTests;
    private Address           address;
    private Gender            gender;

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

    public List<Integer> getIntList() {
        return intList;
    }

    @CmsProperty(name = "list")
    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                ", phones=" + phones +
                ", intList=" + intList +
                ", idTests=" + idTests +
                ", address=" + address +
                ", gender=" + gender +
                '}';
    }
}
