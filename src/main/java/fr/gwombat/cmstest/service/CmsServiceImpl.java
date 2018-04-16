package fr.gwombat.cmstest.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Service
public class CmsServiceImpl implements CmsService {

    @Override
    public Map<String, String> getCmsResults() {
        final Map<String, String> results = new HashMap<>(0);

        results.put("repo/fr/person/nom", "Fabbi");
        results.put("repo/fr/person/firstname", "Guillaume");
        results.put("repo/fr/person/age", "30");

        results.put("repo/fr/person/phones/0/name", "Mobile");
        results.put("repo/fr/person/phones/0/number", "0623456789");
        results.put("repo/fr/person/phones/1/name", "Home");
        results.put("repo/fr/person/phones/1/number", "0123456789");

        results.put("repo/fr/person/id_test/id1", "1");
        results.put("repo/fr/person/id_test/id2", "2");
        results.put("repo/fr/person/id_test/id3", "3");

        return results;
    }
}
