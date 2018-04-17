package fr.gwombat.cmstest;

import fr.gwombat.cmstest.domain.Gender;
import fr.gwombat.cmstest.domain.MyCmsPageResultWrapper;
import fr.gwombat.cmstest.domain.Person;
import fr.gwombat.cmstest.manager.CmsManager;
import fr.gwombat.cmstest.service.CmsService;
import org.junit.Before;
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

    @Before
    public void setup() {
        final Map<String, String> results = new HashMap<>(0);

        // simple properties
        results.put("repo/fr/my-page/person/nom", "Fabbi");
        results.put("repo/fr/my-page/person/firstname", "Guillaume");
        results.put("repo/fr/my-page/person/age", "30");

        // lists
        // list complex
        results.put("repo/fr/my-page/person/phones/0/name", "Mobile");
        results.put("repo/fr/my-page/person/phones/0/number", "0623456789");
        results.put("repo/fr/my-page/person/phones/1/name", "Home");
        results.put("repo/fr/my-page/person/phones/1/number", "0123456789");

        // list simple
        results.put("repo/fr/my-page/person/list/1", "1");
        results.put("repo/fr/my-page/person/list/2", "2");
        results.put("repo/fr/my-page/person/list/3", "3");

        // map
        results.put("repo/fr/my-page/person/id_test/id1", "1");
        results.put("repo/fr/my-page/person/id_test/id2", "2");
        results.put("repo/fr/my-page/person/id_test/id3", "3");

        // complex object
        results.put("repo/fr/my-page/person/address/street", "Test street");
        results.put("repo/fr/my-page/person/address/city", "Paris");
        results.put("repo/fr/my-page/person/address/zip", "12345");

        // enum
        results.put("repo/fr/my-page/person/gender", "MALE");

        given(cmsService.getCmsResults()).willReturn(results);
    }

    @Test
    public void test() {
        // 1. test with wrapper
        final MyCmsPageResultWrapper wrapper = cmsManager.produceCmsPageResult(MyCmsPageResultWrapper.class);
        assertNotNull(wrapper);
        final Person personResult = wrapper.getPerson();

        // 2. test without wrapper
//        final Person personResult = cmsManager.produceCmsPageResult(Person.class);

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

        assertNotNull(personResult.getIdTests());
        assertEquals(new Long(1), personResult.getIdTests().get("id1"));
        assertEquals(new Long(3), personResult.getIdTests().get("id3"));

        assertEquals(Gender.MALE, personResult.getGender());

        logger.info("{}", personResult);

    }
}
