package fr.gwombat.cmstest;

import fr.gwombat.cmstest.domain.Person;
import fr.gwombat.cmstest.manager.CmsManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(CmsManagerTest.class);

    @Autowired
    private CmsManager cmsManager;

    @Test
    public void test() {
        final Person personResult = cmsManager.produceCmsPageResult(Person.class);

        assertNotNull(personResult);
        assertEquals("Fabbi", personResult.getName());
        assertEquals(30, personResult.getAge());
        assertEquals("Guillaume", personResult.getFirstname());

        assertNotNull(personResult.getPhones());
        assertEquals("Mobile", personResult.getPhones().get(0).getName());
        assertEquals("06 23 45 67 89", personResult.getPhones().get(0).getNumber());
        logger.info("{}", personResult);

    }
}
