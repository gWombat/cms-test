package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.processor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
@Configuration
public class CmsConfiguration {

    @Bean
    public CmsResultProcessingChain cmsResultProcessingChain() {
        final CmsResultProcessingChainImpl cmsResultProcessingChain = new CmsResultProcessingChainImpl();
        cmsResultProcessingChain.addProcessor(new SimpleTypeProcessor());
        cmsResultProcessingChain.addProcessor(new CollectionProcessor(cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new MapProcessor(cmsResultProcessingChain));
        cmsResultProcessingChain.addProcessor(new ComplexTypeProcessor(cmsResultProcessingChain));

        return cmsResultProcessingChain;
    }

}
