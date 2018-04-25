package fr.gwombat.cmstest.core.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.service.CmsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CmsManagerDelegate {

    private CmsService               cmsService;
    private CmsResultProcessingChain cmsResultProcessingChain;
    private CallConfigurationChain   callConfigurationChain;
    private CmsConfigurer            cmsConfigurer;

    public <T> T produceAndGetResult(final ConfigurationContext configurationContext, final CmsCallConfigWrapper callConfigWrapper, final Class<T> resultType, final DynamicContext dynamicContext) throws CmsConfigurationException, CmsMappingException {
        final List<CmsPath> calls = prepareAndConfigureCalls(configurationContext, callConfigWrapper);

        final Map<String, String> cmsResults = cmsService.getCmsResults(calls);
        if (cmsResults.isEmpty())
            return null;

        return mapResults(cmsResults, resultType, dynamicContext);
    }

    public List<CmsPath> prepareAndConfigureCalls(final ConfigurationContext configurationContext, final CmsCallConfigWrapper callConfigWrapper) throws CmsConfigurationException {
        final List<CmsPath> calls = new ArrayList<>(0);
        callConfigurationChain.configure(callConfigWrapper, calls, configurationContext);
        return calls;
    }

    @SuppressWarnings("unchecked")
    public <T> T mapResults(final Map<String, String> cmsResults, final Class<T> resultType, final DynamicContext dynamicContext) throws CmsMappingException {
        final String nodeName = cmsConfigurer.getRootNodePrefix();
        final ResultProcessingContext context = new ResultProcessingContext();
        context.setPath(nodeName);
        context.setObjectType(resultType);
        context.setDynamicContext(dynamicContext);

        return (T) cmsResultProcessingChain.process(cmsResults, context);
    }

    public void setCmsService(CmsService cmsService) {
        this.cmsService = cmsService;
    }

    public void setCmsResultProcessingChain(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }

    public void setCmsConfigurer(CmsConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }
}
