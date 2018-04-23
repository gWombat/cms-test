package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChainImpl;
import fr.gwombat.cmstest.mapping.processor.*;
import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistryService;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistryService;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistryService;
import fr.gwombat.cmstest.mapping.service.CmsService;
import fr.gwombat.cmstest.mapping.service.CmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmsInternalConfiguration {

    private CmsConfigurer cmsConfigurer;

    @Autowired
    public void setCmsConfigurer(CmsConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

    @Bean
    public ConverterRegistryService converterRegistryService() {
        final ConverterRegistryService converterRegistryService = new ConverterRegistryService();

        cmsConfigurer.addConverters(converterRegistryService);

        return converterRegistryService;
    }

    @Bean
    public TemporalRegistryService temporalRegistryService() {
        final TemporalRegistryService temporalRegistryService = new TemporalRegistryService();

        cmsConfigurer.addDateTimeFormatters(temporalRegistryService);

        return temporalRegistryService;
    }

    @Bean
    public CallConfigurerRegistryService callConfigurerRegistryService() {
        final CallConfigurerRegistryService callConfigurerRegistryService = new CallConfigurerRegistryService();

        cmsConfigurer.addCallConfigurers(callConfigurerRegistryService);

        return callConfigurerRegistryService;
    }

    @Bean
    public CmsService cmsService() {
        return new CmsServiceImpl();
    }

    @Bean
    public CmsResultProcessingChain cmsResultProcessingChain() {
        final CmsResultProcessingChainImpl cmsResultProcessingChain = new CmsResultProcessingChainImpl();
        cmsResultProcessingChain.addProcessor(new SimpleTypeProcessor());
        cmsResultProcessingChain.addProcessor(new TemporalProcessor(cmsConfigurer, temporalRegistryService()));
        cmsResultProcessingChain.addProcessor(new CollectionProcessor(cmsConfigurer, cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new MapProcessor(cmsConfigurer, cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new ComplexTypeProcessor(cmsConfigurer, cmsResultProcessingChain, converterRegistryService()));

        return cmsResultProcessingChain;
    }

    @Bean
    public CallConfigurationChain callConfigurationChain(final CallConfigurerRegistryService callConfigurerRegistryService) {
        final CallConfigurationChainImpl callConfigurationChain = new CallConfigurationChainImpl();
        callConfigurationChain.addConfigurers(callConfigurerRegistryService.getConfigurers());

        return callConfigurationChain;
    }

}
