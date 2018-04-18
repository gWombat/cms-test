package fr.gwombat.cmstest.context;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;
import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.PostConverter;
import fr.gwombat.cmstest.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.registry.ConverterRegistryService;
import fr.gwombat.cmstest.registry.TemporalRegistryService;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public class CmsResultContextFacade {

    private final CmsResultConfigurer      resultConfigurer;
    private final ConverterRegistryService converterRegistryService;
    private final TemporalRegistryService  temporalRegistryService;
    private       CmsResultProcessingChain processingChain;

    public CmsResultContextFacade(CmsResultConfigurer resultConfigurer, ConverterRegistryService converterRegistryService, TemporalRegistryService temporalRegistryService) {
        this.resultConfigurer = resultConfigurer;
        this.converterRegistryService = converterRegistryService;
        this.temporalRegistryService = temporalRegistryService;
    }

    public String getPropertyPath(final String rootPath, final String propertyName){
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
}
