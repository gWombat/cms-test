package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.mapping.manager.CmsManager;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.service.CmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JackrabbitBaseConfiguration {

    private CmsService                   cmsService;
    private AbstractJackrabbitConfigurer cmsConfigurer;
    private CmsResultProcessingChain     cmsResultProcessingChain;
    private CallConfigurationChain       callConfigurationChain;

    @Bean
    public CmsManager cmsManager() {
        final JackrabbitManagerImpl jackrabbitManager = new JackrabbitManagerImpl();
        jackrabbitManager.setCmsConfigurer(cmsConfigurer);
        jackrabbitManager.setCallConfigurationChain(callConfigurationChain);
        jackrabbitManager.setCmsService(cmsService);
        jackrabbitManager.setCmsResultProcessingChain(cmsResultProcessingChain);
        return jackrabbitManager;
    }

    @Bean
    public AbstractCallConfigurer<?> defaultNodesConfigurer() {
        final DefaultNodesConfigurer defaultNodesConfigurer = new DefaultNodesConfigurer();
        defaultNodesConfigurer.setCmsConfigurer(cmsConfigurer);
        return defaultNodesConfigurer;
    }

    @Bean
    public AbstractCallConfigurer<?> specificNodesConfigurer() {
        final SpecificNodesConfigurer specificNodesConfigurer = new SpecificNodesConfigurer();
        specificNodesConfigurer.setCmsConfigurer(cmsConfigurer);
        return specificNodesConfigurer;
    }

    @Autowired
    public void setCmsService(CmsService cmsService) {
        this.cmsService = cmsService;
    }

    @Autowired
    public void setCmsConfigurer(AbstractJackrabbitConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

    @Autowired
    public void setCmsResultProcessingChain(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    @Autowired
    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }
}
