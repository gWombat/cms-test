package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.context.CmsResultContextFacade;
import fr.gwombat.cmstest.manager.CmsManager;
import fr.gwombat.cmstest.manager.CmsManagerImpl;
import fr.gwombat.cmstest.processor.*;
import fr.gwombat.cmstest.registry.ConverterRegistryService;
import fr.gwombat.cmstest.service.CmsService;
import fr.gwombat.cmstest.service.CmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmsResultInternalConfiguration {

    private CmsResultConfigurer configuration;

    @Autowired(required = false)
    public void setCmsResultConfiguration(CmsResultConfigurer configuration) {
        this.configuration = configuration;
    }

    @Bean
    public ConverterRegistryService converterRegistryService() {
        final ConverterRegistryService converterRegistryService = new ConverterRegistryService();

        configuration.addConverters(converterRegistryService);
        configuration.addPostConverters(converterRegistryService);

        return converterRegistryService;
    }

    @Bean
    public CmsResultContextFacade cmsResultContext() {
        return new CmsResultContextFacade(configuration, converterRegistryService());
    }

    @Bean
    public CmsManager cmsManager() {
        return new CmsManagerImpl(cmsService(), cmsResultProcessingChain(), cmsResultContext());
    }

    @Bean
    public CmsService cmsService() {
        return new CmsServiceImpl();
    }

    @Bean
    public CmsResultProcessingChain cmsResultProcessingChain() {
        final CmsResultProcessingChainImpl cmsResultProcessingChain = new CmsResultProcessingChainImpl();
        cmsResultProcessingChain.addProcessor(new SimpleTypeProcessor());
        cmsResultProcessingChain.addProcessor(new EnumProcessor());
        cmsResultProcessingChain.addProcessor(new CollectionProcessor(cmsResultContext(), cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new MapProcessor(cmsResultContext(), cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new ComplexTypeProcessor(cmsResultContext(), cmsResultProcessingChain));

        return cmsResultProcessingChain;
    }

}
