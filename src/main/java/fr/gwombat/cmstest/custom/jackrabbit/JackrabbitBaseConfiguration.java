package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.mapping.manager.CmsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JackrabbitBaseConfiguration {

    @Bean
    public CmsManager cmsManager() {
        return new JackrabbitManagerImpl();
    }

    @Bean
    public AbstractCallConfigurer<?> defaultNodesConfigurer() {
        return new DefaultNodesConfigurer();
    }

    @Bean
    public AbstractCallConfigurer<?> specificNodesConfigurer() {
        return new SpecificNodesConfigurer();
    }

}
