package fr.gwombat.cmstest.mapping.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.DynamicNodesContext;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.exceptions.CmsRuntimeException;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.processor.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.service.CmsService;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;

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
    public <T> T produceComplexObject(final CmsCallWrapper callWrapper, final Class<T> resultType) {
        return produceComplexObject(callWrapper, resultType, null);
    }

    @Override
    public <T> T produceComplexObject(final CmsCallWrapper callWrapper, final Class<T> resultType, final DynamicNodesContext dynamicNodesContext) {
        if (!TypeUtils.isComplexType(resultType))
            throw new CmsRuntimeException("The expected result object must be a complex object (Not String, numbers, boolean, map, collection or temporal elements)");

        return produceResultInternal(callWrapper, resultType, null, dynamicNodesContext);
    }

    @Override
    public <T> T produceSimpleObject(final Class<T> resultType, final String propertyName) {
        if (propertyName == null || "".equals(propertyName))
            throw new CmsRuntimeException("The propertyName parameter must be set");

        return produceResultInternal(null, resultType, propertyName, null);
    }

    private <T> T produceResultInternal(final CmsCallWrapper callWrapper, final Class<T> resultType, final String propertyName, final DynamicNodesContext dynamicNodesContext) {
        final Map<String, String> cmsResults = cmsService.getCmsResults();

        if (cmsResults.isEmpty())
            return null;

        try {
            String nodeName = cmsConfigurer.getRootNodePrefix();
            if (propertyName != null)
                nodeName += propertyName;

            final ResultProcessingContext context = new ResultProcessingContext();
            context.setPath(nodeName);
            context.setObjectType(resultType);
            context.setDynamicNodesContext(dynamicNodesContext);

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
