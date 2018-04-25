package fr.gwombat.cmstest.mapping.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.exceptions.CmsRuntimeException;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
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
    public List<CmsPath> createCmsCallsTemporary(final CmsCallConfigWrapper callConfigWrapper, final DynamicContext dynamicContext) {

        final ConfigurationContext configurationContext = createConfigurationContext(cmsConfigurer, dynamicContext);
        final List<CmsPath> calls = new ArrayList<>(0);
        try {
            callConfigurationChain.configure(callConfigWrapper, calls, configurationContext);
        } catch (CmsConfigurationException e) {
            throw new CmsRuntimeException(e);
        }
        return calls;
    }

    protected abstract ConfigurationContext createConfigurationContext(U configurer, final DynamicContext dynamicContext);

    @Override
    public <T> T produceComplexObject(final CmsCallConfigWrapper callWrapper, final Class<T> resultType) {
        return produceComplexObject(callWrapper, resultType, null);
    }

    @Override
    public <T> T produceComplexObject(final CmsCallConfigWrapper callWrapper, final Class<T> resultType, final DynamicContext dynamicContext) {
        if (!TypeUtils.isComplexType(resultType))
            throw new CmsRuntimeException("The expected result object must be a complex object (Not String, numbers, boolean, map, collection or temporal elements)");

        return produceResultInternal(callWrapper, resultType, dynamicContext);
    }

    private <T> T produceResultInternal(final CmsCallConfigWrapper callConfigWrapper, final Class<T> resultType, final DynamicContext dynamicContext) {
        final Map<String, String> cmsResults = cmsService.getCmsResults();

        if (cmsResults.isEmpty())
            return null;

        try {
            final String nodeName = cmsConfigurer.getRootNodePrefix();
            final ResultProcessingContext context = new ResultProcessingContext();
            context.setPath(nodeName);
            context.setObjectType(resultType);
            context.setDynamicContext(dynamicContext);

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
