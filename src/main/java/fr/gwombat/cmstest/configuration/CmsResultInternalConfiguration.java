package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.context.CmsResultContextFacade;
import fr.gwombat.cmstest.manager.CmsManager;
import fr.gwombat.cmstest.manager.CmsManagerImpl;
import fr.gwombat.cmstest.processor.*;
import fr.gwombat.cmstest.registry.ConverterRegistryService;
import fr.gwombat.cmstest.registry.TemporalRegistryService;
import fr.gwombat.cmstest.service.CmsService;
import fr.gwombat.cmstest.service.CmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmsResultInternalConfiguration {

    private CmsResultConfigurer resultConfigurer;

    @Autowired
    public void setCmsResultConfiguration(CmsResultConfigurer resultConfigurer) {
        this.resultConfigurer = resultConfigurer;
    }

    @Bean
    public ConverterRegistryService converterRegistryService() {
        final ConverterRegistryService converterRegistryService = new ConverterRegistryService();

        resultConfigurer.addConverters(converterRegistryService);
        resultConfigurer.addPostConverters(converterRegistryService);

        return converterRegistryService;
    }

    @Bean
    public TemporalRegistryService temporalRegistryService() {
        final TemporalRegistryService temporalRegistryService = new TemporalRegistryService();

        resultConfigurer.addDateTimeFormatter(temporalRegistryService);

        return temporalRegistryService;
    }

    @Bean
    public CmsResultContextFacade cmsResultContext() {
        CmsResultContextFacade cmsResultContextFacade = new CmsResultContextFacade(resultConfigurer, converterRegistryService(), temporalRegistryService());
        cmsResultContextFacade.setProcessingChain(cmsResultProcessingChain(cmsResultContextFacade));
        return cmsResultContextFacade;
    }

    @Bean
    public CmsManager cmsManager() {
        return new CmsManagerImpl(cmsService(), cmsResultContext());
    }

    @Bean
    public CmsService cmsService() {
        return new CmsServiceImpl();
    }

    @Bean
    public CmsResultProcessingChain cmsResultProcessingChain(final CmsResultContextFacade cmsResultContext) {
        final CmsResultProcessingChainImpl cmsResultProcessingChain = new CmsResultProcessingChainImpl();
        cmsResultProcessingChain.addProcessor(new SimpleTypeProcessor());
        cmsResultProcessingChain.addProcessor(new TemporalProcessor(cmsResultContext));
        cmsResultProcessingChain.addProcessor(new CollectionProcessor(cmsResultContext));
        cmsResultProcessingChain.addProcessor(new MapProcessor(cmsResultContext));
        cmsResultProcessingChain.addProcessor(new ComplexTypeProcessor(cmsResultContext));

        return cmsResultProcessingChain;
    }

}
