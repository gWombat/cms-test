package fr.gwombat.cmstest.mapping.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.mapping.context.CmsContextFacade;
import fr.gwombat.cmstest.mapping.service.CmsService;
import fr.gwombat.cmstest.mapping.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public abstract class AbstractCmsManager<U extends CmsConfigurer> implements CmsManager {

    private CmsService       cmsService;
    private CmsContextFacade cmsContextFacade;
    private U                cmsConfigurer;

    @Override
    public List<CmsPath> createCmsCallsTemporary(CmsCallWrapper callWrapper, Long departureCityId) {

        final LocalContext localContext = createLocalContext(cmsConfigurer, departureCityId);
        final List<CmsPath> calls = new ArrayList<>(0);
        cmsContextFacade.getCallConfigurationChain().configure(callWrapper, calls, localContext);
        return calls;
    }

    protected abstract LocalContext createLocalContext(U configurer, Long departureCityId);

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
        if (currentNodeName == null) {
            try {
                currentNodeName = AnnotationDetectorUtils.detectRootNodeName(resultType);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        final String nodeName = cmsContextFacade.getRootNodePrefix() + currentNodeName;

        return (T) cmsContextFacade.getProcessingChain().process(resultType, cmsResults, null, nodeName);
    }

    @Autowired
    public void setCmsService(CmsService cmsService) {
        this.cmsService = cmsService;
    }

    @Autowired
    public void setCmsContextFacade(CmsContextFacade cmsContextFacade) {
        this.cmsContextFacade = cmsContextFacade;
    }

    @Autowired
    public void setCmsConfigurer(U cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }
}
