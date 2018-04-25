package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.core.configurers.CallConfigurationChain;
import fr.gwombat.cmstest.core.configurers.CmsCallConfigurer;
import fr.gwombat.cmstest.core.manager.CmsManager;
import fr.gwombat.cmstest.core.manager.CmsManagerDelegate;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

public class JackrabbitConfigurerSupport {

    private AbstractJackrabbitConfigurer cmsConfigurer;
    private CallConfigurationChain       callConfigurationChain;
    private CmsManagerDelegate           cmsManagerDelegate;

    @Bean
    public List<CmsCallConfigurer> nodesConfigurers() {
        final AbstractJackrabbitCallConfigurer defaultNodesConfigurer = new DefaultNodesConfigurer();
        defaultNodesConfigurer.setCmsConfigurer(cmsConfigurer);

        final AbstractJackrabbitCallConfigurer specificNodesConfigurer = new SpecificNodesConfigurer();
        specificNodesConfigurer.setCmsConfigurer(cmsConfigurer);

        return Arrays.asList(defaultNodesConfigurer, specificNodesConfigurer);
    }

    @Bean
    public CmsManager cmsManager() {
        final JackrabbitManagerImpl jackrabbitManager = new JackrabbitManagerImpl();
        jackrabbitManager.setCmsConfigurer(cmsConfigurer);
        jackrabbitManager.setCallConfigurationChain(callConfigurationChain);
        jackrabbitManager.setCmsManagerDelegate(cmsManagerDelegate);
        return jackrabbitManager;
    }


    @Autowired
    public void setCmsManagerDelegate(CmsManagerDelegate cmsManagerDelegate) {
        this.cmsManagerDelegate = cmsManagerDelegate;
    }

    @Autowired
    public void setCmsConfigurer(AbstractJackrabbitConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

    @Autowired
    public void setCallConfigurationChain(CallConfigurationChain callConfigurationChain) {
        this.callConfigurationChain = callConfigurationChain;
    }

}
