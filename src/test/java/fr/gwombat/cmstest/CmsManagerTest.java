package fr.gwombat.cmstest;

import fr.gwombat.cmstest.core.CmsCallBuilder;
import fr.gwombat.cmstest.core.CmsCallConfig;
import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.core.manager.CmsManagerDelegate;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallConfigWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitConfigurationContext;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPath;
import fr.gwombat.cmstest.domain.ExtendedPerson;
import fr.gwombat.cmstest.domain.Gender;
import fr.gwombat.cmstest.domain.Person;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.service.CmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
    private CmsManagerDelegate cmsManagerDelegate;

    private CmsCallConfigWrapper initCmsCalls() {
        final JackrabbitCallConfigWrapper cmsCallWrapper = new JackrabbitCallConfigWrapper();
        cmsCallWrapper.setRootNodePath("my-page");
        cmsCallWrapper.setCallDefaultNodes(true);
        cmsCallWrapper.setCallSpecificNodes(true);

        final CmsCallConfig childCall1 = CmsCallBuilder.init("person").build();
        final CmsCallConfig childCall3 = CmsCallBuilder.init("childNode").build();
        final CmsCallConfig childCall2 = CmsCallBuilder.init("otherNode_")
                .appendDynamicVariable("departureCityId")
                .childCalls(Collections.singletonList(childCall3))
                .build();
        final CmsCallConfig childCall4 = CmsCallBuilder.init("a_very_{complex}_")
                .appendPath("path/with_")
                .appendDynamicVariable("argument")
                .build();

        cmsCallWrapper
                .addCall(childCall1)
                .addCall(childCall2)
                .addCall(childCall4);

        return cmsCallWrapper;
    }

    @Test
    public void test_configuration_calls() throws CmsConfigurationException {
        final CmsCallConfigWrapper cmsCallConfigWrapper = initCmsCalls();

        final DynamicContext dynamicContext = new DynamicContext();
        dynamicContext
                .addDynamicPathVariable("departureCityId", String.valueOf(1188L))
                .addDynamicPathVariable("complex", "complex");
//                .addDynamicPathVariable("argument", "argument");

        final JackrabbitConfigurationContext configurationContext = new JackrabbitConfigurationContext();
        configurationContext.setDynamicContext(dynamicContext);
        configurationContext.setLanguage("fr");
        configurationContext.setBrandNode("my-site");
        configurationContext.setBrandNodeSpecific("my-site-specific");

        final List<CmsPath> calls = cmsManagerDelegate.prepareAndConfigureCalls(configurationContext, cmsCallConfigWrapper);
        assertNotNull(calls);
        assertEquals(8, calls.size());
        assertEquals("my-page/person", (calls.get(0)).getPath());
        assertEquals("my-page/person", (calls.get(0)).getResolvedPath());
        assertEquals("fr/my-site/my-page/person", ((JackrabbitPath) calls.get(0)).getFullCmsPath());

        assertEquals("my-page/otherNode_", (calls.get(1)).getPath());
        assertEquals("my-page/otherNode_1188", (calls.get(1)).getResolvedPath());
        assertEquals("fr/my-site/my-page/otherNode_1188", ((JackrabbitPath) calls.get(1)).getFullCmsPath());

        assertEquals("my-page/otherNode_/childNode", (calls.get(2)).getPath());
        assertEquals("my-page/otherNode_1188/childNode", (calls.get(2)).getResolvedPath());
        assertEquals("fr/my-site/my-page/otherNode_1188/childNode", ((JackrabbitPath) calls.get(2)).getFullCmsPath());

        assertEquals("my-page/person", (calls.get(4)).getPath());
        assertEquals("my-page/person", (calls.get(4)).getResolvedPath());
        assertEquals("fr/my-site-specific/my-page/person", ((JackrabbitPath) calls.get(4)).getFullCmsPath());

        assertEquals("my-page/otherNode_", (calls.get(5)).getPath());
        assertEquals("my-page/otherNode_1188", (calls.get(5)).getResolvedPath());
        assertEquals("fr/my-site-specific/my-page/otherNode_1188", ((JackrabbitPath) calls.get(5)).getFullCmsPath());

        assertEquals("my-page/otherNode_/childNode", (calls.get(6)).getPath());
        assertEquals("my-page/otherNode_1188/childNode", (calls.get(6)).getResolvedPath());
        assertEquals("fr/my-site-specific/my-page/otherNode_1188/childNode", ((JackrabbitPath) calls.get(6)).getFullCmsPath());

        assertEquals("my-page/a_very__path/with_", (calls.get(7)).getPath());
        assertEquals("my-page/a_very_complex_path/with_${argument}", (calls.get(7)).getResolvedPath());
    }

//    @Test
//    public void test_simple_object() {
//        final Map<String, String> results = new HashMap<>(0);
//        results.put("fr/my-site/myId", "1000");
//        given(cmsService.getCmsResults()).willReturn(results);
//
//        final Integer intTest = cmsManager.produceSimpleObject(Integer.class, "myId");
//        assertNotNull(intTest);
//        assertEquals(new Integer(1000), intTest);
//    }

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

    private Map<String, String> initResults() {
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
    public void test_complex_object() throws CmsMappingException {
        final Map<String, String> results = initResults();
        given(cmsService.getCmsResults(any())).willReturn(results);

        final DynamicContext dynamicContext = new DynamicContext();
        dynamicContext.addDynamicNodeName(Person.class, "my-page/person");

        final Map<String, String> cmsResults = cmsService.getCmsResults(Collections.emptyList());
        final Person personResult = cmsManagerDelegate.mapResults(cmsResults, Person.class, dynamicContext);

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
    public void test_inheritance() throws CmsMappingException {
        final Map<String, String> results = initResults();
        results.put("fr/my-site/my-page/person/customProperty", "test");
        results.put("fr/my-site/my-page/person/birthDate", "01/02/1985");
        given(cmsService.getCmsResults(any())).willReturn(results);

        final DynamicContext dynamicContext = new DynamicContext();
        dynamicContext.addDynamicNodeName(Person.class, "my-page/person");

        final Map<String, String> cmsResults = cmsService.getCmsResults(Collections.emptyList());
        final ExtendedPerson personResult = cmsManagerDelegate.mapResults(cmsResults, ExtendedPerson.class, dynamicContext);

        assertNotNull(personResult);
        assertEquals("test", personResult.getCustomProperty());
        assertNotNull(personResult.getBirthDate());
        assertNotNull(personResult.getName());
        logger.info("{}", personResult);
    }
}
