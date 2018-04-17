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
    public <T> T produceCmsPageResult(final Class<T> resultType) {

        if (TypeUtils.isMap(resultType)
                || TypeUtils.isCollection(resultType)
                || TypeUtils.isSimpleType(resultType))
            throw new IllegalArgumentException("Root result object must be a complex object!");

        final Map<String, String> cmsResults = cmsService.getCmsResults();

        if (cmsResults.isEmpty())
            return null;

        final String nodeName = cmsResultContextFacade.getRootNodePath(AnnotationDetectorUtils.detectRootNodeName(resultType));
        return (T) cmsResultContextFacade.getProcessingChain().process(resultType, cmsResults, null, nodeName);
    }
}
