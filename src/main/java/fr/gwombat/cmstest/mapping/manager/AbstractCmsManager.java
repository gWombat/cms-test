package fr.gwombat.cmstest.mapping.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.exceptions.CmsRuntimeException;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.processor.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.service.CmsService;
import fr.gwombat.cmstest.mapping.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;

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

    private CmsService               cmsService;
    private CmsResultProcessingChain cmsResultProcessingChain;
    private CallConfigurationChain   callConfigurationChain;
    private U                        cmsConfigurer;

    @Override
    public List<CmsPath> createCmsCallsTemporary(CmsCallWrapper callWrapper, Long departureCityId) {

        final LocalContext localContext = createLocalContext(cmsConfigurer, departureCityId);
        final List<CmsPath> calls = new ArrayList<>(0);
        callConfigurationChain.configure(callWrapper, calls, localContext);
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
                throw new CmsRuntimeException(e);
            }
        }
        final String nodeName = cmsConfigurer.getRootNodePrefix() + currentNodeName;

        try {
            final ResultProcessingContext context = new ResultProcessingContext();
            context.setPath(nodeName);
            context.setObjectType(resultType);

            return (T) cmsResultProcessingChain.process(cmsResults, context);
        } catch (CmsMappingException e) {
            throw new CmsRuntimeException(e);
        }
    }

    public void setCmsService(CmsService cmsService) {
        this.cmsService = cmsService;
    }

    public void setCmsConfigurer(U cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

    public void setCmsResultProcessingChain(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }
}
