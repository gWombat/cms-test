package fr.gwombat.cmstest.context;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;
import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.PostConverter;
import fr.gwombat.cmstest.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.registry.ConverterRegistryService;

public class CmsResultContextFacade {

    private final CmsResultConfigurer      resultConfigurer;
    private final ConverterRegistryService converterRegistryService;
    private       CmsResultProcessingChain processingChain;

    public CmsResultContextFacade(CmsResultConfigurer resultConfigurer, ConverterRegistryService converterRegistryService) {
        this.resultConfigurer = resultConfigurer;
        this.converterRegistryService = converterRegistryService;
    }

    public String getRootNodePrefix() {
        return resultConfigurer.getRootNodePrefix();
    }

    public String getRootNodePath(final String nodeName) {
        return resultConfigurer.getRootNodePath(nodeName);
    }

    public String getPropertySeparator() {
        return resultConfigurer.getPropertySeparator();
    }

    public CmsResultProcessingChain getProcessingChain() {
        return processingChain;
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
