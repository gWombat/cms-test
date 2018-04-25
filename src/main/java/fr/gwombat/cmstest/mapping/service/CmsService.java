package fr.gwombat.cmstest.mapping.service;

import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsService {

    Map<String, String> getCmsResults(List<CmsPath> calls);
}
