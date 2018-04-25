package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitManagerImpl;
import fr.gwombat.cmstest.mapping.manager.CmsManager;
import fr.gwombat.cmstest.mapping.processor.CmsResultProcessingChain;
import fr.gwombat.cmstest.mapping.service.CmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

public class JackrabbitConfigurerSupport {

    private CmsService                   cmsService;
    private AbstractJackrabbitConfigurer cmsConfigurer;
    private CmsResultProcessingChain     cmsResultProcessingChain;
    private CallConfigurationChain       callConfigurationChain;

    @Bean
    public List<AbstractCallConfigurer<?>> nodesConfigurers() {
        final DefaultNodesConfigurer defaultNodesConfigurer = new DefaultNodesConfigurer();
        defaultNodesConfigurer.setCmsConfigurer(cmsConfigurer);

        final SpecificNodesConfigurer specificNodesConfigurer = new SpecificNodesConfigurer();
        specificNodesConfigurer.setCmsConfigurer(cmsConfigurer);

        return Arrays.asList(defaultNodesConfigurer, specificNodesConfigurer);
    }

    @Bean
    public CmsManager cmsManager() {
        final JackrabbitManagerImpl jackrabbitManager = new JackrabbitManagerImpl();
        jackrabbitManager.setCmsConfigurer(cmsConfigurer);
        jackrabbitManager.setCallConfigurationChain(callConfigurationChain);
        jackrabbitManager.setCmsService(cmsService);
        jackrabbitManager.setCmsResultProcessingChain(cmsResultProcessingChain);
        return jackrabbitManager;
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