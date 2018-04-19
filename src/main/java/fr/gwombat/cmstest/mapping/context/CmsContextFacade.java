package fr.gwombat.cmstest.mapping.context;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistryService;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistryService;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public class CmsContextFacade {

    private CmsConfigurer            resultConfigurer;
    private ConverterRegistryService converterRegistryService;
    private TemporalRegistryService  temporalRegistryService;
    private CmsResultProcessingChain processingChain;
    private CallConfigurationChain   callConfigurationChain;

    public CmsContextFacade(CmsConfigurer resultConfigurer, ConverterRegistryService converterRegistryService, TemporalRegistryService temporalRegistryService) {
        this.resultConfigurer = resultConfigurer;
        this.converterRegistryService = converterRegistryService;
        this.temporalRegistryService = temporalRegistryService;
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

    public <T> Converter<T> getConverter(Class<T> clazz) {
        return converterRegistryService.getConverter(clazz);
    }

    public <T> PostConverter<T> getPostConverter(Class<T> clazz) {
        return converterRegistryService.getPostConverters(clazz);
    }

    public void setProcessingChain(CmsResultProcessingChain processingChain) {
        this.processingChain = processingChain;
    }

    public CallConfigurationChain getCallConfigurationChain() {
        return callConfigurationChain;
    }

    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }

    public CmsConfigurer getResultConfigurer() {
        return resultConfigurer;
    }

    public void setResultConfigurer(CmsConfigurer resultConfigurer) {
        this.resultConfigurer = resultConfigurer;
    }

    public ConverterRegistryService getConverterRegistryService() {
        return converterRegistryService;
    }

    public void setConverterRegistryService(ConverterRegistryService converterRegistryService) {
        this.converterRegistryService = converterRegistryService;
    }

    public TemporalRegistryService getTemporalRegistryService() {
        return temporalRegistryService;
    }

    public void setTemporalRegistryService(TemporalRegistryService temporalRegistryService) {
        this.temporalRegistryService = temporalRegistryService;
    }
}
