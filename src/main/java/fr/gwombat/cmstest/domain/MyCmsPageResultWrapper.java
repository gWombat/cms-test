package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.annotations.CmsNode;
import fr.gwombat.cmstest.annotations.CmsPageResult;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@CmsPageResult(rootNode = "my-page")
public class MyCmsPageResultWrapper {

    //  /repo/fr/my-page/person/nom
    //  /repo/fr/my-page/person/firstname
    //  /repo/fr/my-page/person/age

    @CmsNode
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
