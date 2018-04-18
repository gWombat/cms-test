package fr.gwombat.cmstest;

import fr.gwombat.cmstest.domain.ExtendedPerson;
import fr.gwombat.cmstest.domain.Gender;
import fr.gwombat.cmstest.domain.MyCmsPageResultWrapper;
import fr.gwombat.cmstest.domain.Person;
import fr.gwombat.cmstest.mapping.manager.CmsManager;
import fr.gwombat.cmstest.mapping.service.CmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(CmsManagerTest.class);

    @MockBean
    private CmsService cmsService;

    @Autowired
    private CmsManager cmsManager;

    @Test
    public void test_simple_object() {
        final Map<String, String> results = new HashMap<>(0);
        results.put("fr/my-site/myId", "1000");
        given(cmsService.getCmsResults()).willReturn(results);

        final Integer intTest = cmsManager.produceSimpleObject(Integer.class, "myId");
        assertNotNull(intTest);
        assertEquals(new Integer(1000), intTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_simple_object_without_propertyName_should_fail() {
        final Map<String, String> results = new HashMap<>(0);
        results.put("fr/my-site/myId", "1000");
        given(cmsService.getCmsResults()).willReturn(results);

        cmsManager.produceComplexObject(Integer.class);
    }

//    @Test
//    public void test_collection_object() {
//        final Map<String, String> results = new HashMap<>(0);
//        results.put("fr/my-site/list/0", "0");
//        results.put("fr/my-site/list/1", "1");
//        results.put("fr/my-site/list/2", "2");
//        given(cmsService.getCmsResults()).willReturn(results);
//
//        final List<Integer> list = cmsManager.produceSimpleObject(List.class, "list");
//        assertNotNull(list);
//        assertEquals(results.size(), list.size());
//    }

    private Map<String, String> initResults(){
        final Map<String, String> results = new HashMap<>(0);

        // simple properties
        results.put("fr/my-site/my-page/person/nom", "Fabbi");
        results.put("fr/my-site/my-page/person/firstname", "Guillaume");
        results.put("fr/my-site/my-page/person/age", "30");

        // lists
        // list complex
        results.put("fr/my-site/my-page/person/phones/0/name", "Mobile");
        results.put("fr/my-site/my-page/person/phones/0/number", "0623456789");
        results.put("fr/my-site/my-page/person/phones/1/name", "Home");
        results.put("fr/my-site/my-page/person/phones/1/number", "0123456789");

        // list simple
        results.put("fr/my-site/my-page/person/list/1", "1");
        results.put("fr/my-site/my-page/person/list/2", "2");
        results.put("fr/my-site/my-page/person/list/3", "3");

        // map
        results.put("fr/my-site/my-page/person/id_test/id1", "1");
        results.put("fr/my-site/my-page/person/id_test/id2", "2");
        results.put("fr/my-site/my-page/person/id_test/id3", "3");

        // complex object
        results.put("fr/my-site/my-page/person/address/street", "Test street");
        results.put("fr/my-site/my-page/person/address/city", "Paris");
        results.put("fr/my-site/my-page/person/address/zip", "12345");

        // enum
        results.put("fr/my-site/my-page/person/gender", "MALE");

        return results;
    }

    @Test
    public void test_complex_object() {
        final Map<String, String> results = initResults();
        given(cmsService.getCmsResults()).willReturn(results);

        // 1. test with wrapper
        final MyCmsPageResultWrapper wrapper = cmsManager.produceComplexObject(MyCmsPageResultWrapper.class);
        assertNotNull(wrapper);
        final Person personResult = wrapper.getPerson();

        // 2. test without wrapper
//        final Person personResult = cmsManager.produceComplexObject(Person.class);

        assertNotNull(personResult);
        assertEquals("Fabbi", personResult.getName());
        assertEquals(30, personResult.getAge());
        assertEquals("Guillaume", personResult.getFirstname());

        assertNotNull(personResult.getPhones());
        assertEquals(2, personResult.getPhones().size());
        assertEquals("Home", personResult.getPhones().get(0).getName());
        assertEquals("01 23 45 67 89", personResult.getPhones().get(0).getNumber());
        assertEquals("Mobile", personResult.getPhones().get(1).getName());
        assertEquals("06 23 45 67 89", personResult.getPhones().get(1).getNumber());

        assertNotNull(personResult.getIntList());
        assertEquals(3, personResult.getIntList().size());
        assertEquals(new Integer(1), personResult.getIntList().get(0));
        assertEquals(new Integer(3), personResult.getIntList().get(2));

        assertNotNull(personResult.getAddress());
        assertEquals("12345", personResult.getAddress().getZip());
        assertEquals("Paris", personResult.getAddress().getCity());
        assertEquals("Test street", personResult.getAddress().getStreet());

        assertNotNull(personResult.getIdTests());
        assertEquals(new Long(1), personResult.getIdTests().get("id1"));
        assertEquals(new Long(3), personResult.getIdTests().get("id3"));

        assertEquals(Gender.MALE, personResult.getGender());

        logger.info("{}", personResult);
    }

    @Test
    public void test_inheritance(){
        final Map<String, String> results = initResults();
        results.put("fr/my-site/my-page/person/customProperty", "test");
        results.put("fr/my-site/my-page/person/birthDate", "01/02/1985");
        given(cmsService.getCmsResults()).willReturn(results);

        final ExtendedPerson personResult = cmsManager.produceComplexObject(ExtendedPerson.class);
        assertNotNull(personResult);
        assertEquals("test", personResult.getCustomProperty());
        assertNotNull(personResult.getBirthDate());
        logger.info("{}", personResult);
    }
}
