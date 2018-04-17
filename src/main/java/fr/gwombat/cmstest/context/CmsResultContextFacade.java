package fr.gwombat.cmstest.context;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;
import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.PostConverter;
import fr.gwombat.cmstest.registry.ConverterRegistryService;

public class CmsResultContextFacade {

    private final CmsResultConfigurer      cmsConfiguration;
    private final ConverterRegistryService converterRegistryService;

    public CmsResultContextFacade(CmsResultConfigurer cmsConfiguration, ConverterRegistryService converterRegistryService) {
        this.cmsConfiguration = cmsConfiguration;
        this.converterRegistryService = converterRegistryService;
    }

    public String getRootNodePrefix() {
        return cmsConfiguration.getRootNodePrefix();
    }

    public String getRootNodePath(final String nodeName) {
        return cmsConfiguration.getRootNodePath(nodeName);
    }

    public String getPropertySeparator() {
        return cmsConfiguration.getPropertySeparator();
    }


    public <T> Converter<T> getConverter(Class<T> clazz) {
        return converterRegistryService.getConverter(clazz);
    }

    public <T> PostConverter<T> getPostConverter(Class<T> clazz) {
        return converterRegistryService.getPostConverters(clazz);
    }

}
