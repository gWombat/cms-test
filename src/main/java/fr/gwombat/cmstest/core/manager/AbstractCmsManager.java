package fr.gwombat.cmstest.core.manager;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.exceptions.CmsRuntimeException;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public abstract class AbstractCmsManager<U extends CmsConfigurer> implements CmsManager {

    private CallConfigurationChain callConfigurationChain;
    private U                      cmsConfigurer;
    private CmsManagerDelegate     cmsManagerDelegate;

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
        try {
            final ConfigurationContext configurationContext = createConfigurationContext(cmsConfigurer, dynamicContext);
            return cmsManagerDelegate.produceAndGetResult(configurationContext, callConfigWrapper, resultType, dynamicContext);
        } catch (CmsMappingException | CmsConfigurationException e) {
            throw new CmsRuntimeException(e);
        }
    }

    public void setCmsConfigurer(U cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }

    public void setCmsManagerDelegate(CmsManagerDelegate cmsManagerDelegate) {
        this.cmsManagerDelegate = cmsManagerDelegate;
    }
}
