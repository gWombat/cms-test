package fr.gwombat.cmstest.mapping.context;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistryService;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistryService;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistryService;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public class CmsResultContextFacade {

    private final CmsResultConfigurer           resultConfigurer;
    private final ConverterRegistryService      converterRegistryService;
    private final TemporalRegistryService       temporalRegistryService;
    private final CallConfigurerRegistryService callConfigurerRegistryService;
    private       CmsResultProcessingChain      processingChain;

    public CmsResultContextFacade(CmsResultConfigurer resultConfigurer, ConverterRegistryService converterRegistryService, TemporalRegistryService temporalRegistryService, CallConfigurerRegistryService callConfigurerRegistryService) {
        this.resultConfigurer = resultConfigurer;
        this.converterRegistryService = converterRegistryService;
        this.temporalRegistryService = temporalRegistryService;
        this.callConfigurerRegistryService = callConfigurerRegistryService;
    }

    public String getPropertyPath(final String rootPath, final String propertyName) {
        return rootPath + getPropertySeparator() + propertyName;
    }

    public String getRootNodePrefix() {
        return resultConfigurer.getRootNodePrefix();
    }

    public String getPropertySeparator() {
        return resultConfigurer.getPropertySeparator();
    }

    public CmsResultProcessingChain getProcessingChain() {
        return processingChain;
    }

    public TemporalAccessor parseDate(Class<? extends Temporal> clazz, final String date) {
        return temporalRegistryService.parse(clazz, date);
    }

    public List<AbstractCallConfigurer> getCallConfigurers() {
        return callConfigurerRegistryService.getConfigurers();
    }

    public <T> Converter<T> getConverter(Class<T> clazz) {
        return converterRegistryService.getConverter(clazz);
    }

    public <T> PostConverter<T> getPostConverter(Class<T> clazz) {
        return converterRegistryService.getPostConverters(clazz);
    }

    public void setProcessingChain(CmsResultProcessingChain processingChain) {
        this.processingChain = processingChain;
    }
}
