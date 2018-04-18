package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.mapping.annotations.CmsNode;
import fr.gwombat.cmstest.mapping.annotations.CmsPageResult;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@CmsPageResult(rootNode = "my-page")
public class MyCmsPageResultWrapper {

    @CmsNode
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
