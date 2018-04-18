package fr.gwombat.cmstest.manager;

import fr.gwombat.cmstest.context.CmsResultContextFacade;
import fr.gwombat.cmstest.service.CmsService;
import fr.gwombat.cmstest.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.utils.TypeUtils;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public class CmsManagerImpl implements CmsManager {

    private final CmsService             cmsService;
    private final CmsResultContextFacade cmsResultContextFacade;

    public CmsManagerImpl(CmsService cmsService, CmsResultContextFacade cmsResultContextFacade) {
        this.cmsService = cmsService;
        this.cmsResultContextFacade = cmsResultContextFacade;
    }

    @Override
    public <T> T produceComplexObject(final Class<T> resultType) {
        return produceSimpleObject(resultType, null);
    }

    @Override
    public <T> T produceSimpleObject(Class<T> resultType, String propertyName) {
        if (!TypeUtils.isComplexType(resultType) && propertyName == null)
            throw new IllegalArgumentException("The target object must be a complex object or the propertyName parameter should be set");

        final Map<String, String> cmsResults = cmsService.getCmsResults();

        if (cmsResults.isEmpty())
            return null;

        String currentNodeName = propertyName;
        if (currentNodeName == null)
            currentNodeName = AnnotationDetectorUtils.detectRootNodeName(resultType);
        final String nodeName = cmsResultContextFacade.getRootNodePrefix() + currentNodeName;

        return (T) cmsResultContextFacade.getProcessingChain().process(resultType, cmsResults, null, nodeName);
    }
}
