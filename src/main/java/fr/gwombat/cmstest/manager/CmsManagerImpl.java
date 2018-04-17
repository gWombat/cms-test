package fr.gwombat.cmstest.manager;

import fr.gwombat.cmstest.configuration.CmsResultConfiguration;
import fr.gwombat.cmstest.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.service.CmsService;
import fr.gwombat.cmstest.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.utils.TypeUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Service
public class CmsManagerImpl implements CmsManager {

    private final CmsService               cmsService;
    private final CmsResultProcessingChain cmsResultProcessingChain;
    private final CmsResultConfiguration   cmsResultConfiguration;

    public CmsManagerImpl(CmsService cmsService, CmsResultProcessingChain cmsResultProcessingChain, CmsResultConfiguration cmsResultConfiguration) {
        this.cmsService = cmsService;
        this.cmsResultProcessingChain = cmsResultProcessingChain;
        this.cmsResultConfiguration = cmsResultConfiguration;
    }

    @Override
    public <T> T produceCmsPageResult(final Class<T> resultType) {

        if (TypeUtils.isMap(resultType)
                || TypeUtils.isCollection(resultType)
                || TypeUtils.isSimpleType(resultType)
                || TypeUtils.isEnum(resultType))
            throw new IllegalArgumentException("Root result object must be a complex object!");

        final Map<String, String> cmsResults = cmsService.getCmsResults();

        if (cmsResults.isEmpty())
            return null;

        final String nodeName = cmsResultConfiguration.getRootNodePath(AnnotationDetectorUtils.detectRootNodeName(resultType));
        return (T) cmsResultProcessingChain.process(resultType, cmsResults, null, nodeName);
    }
}
