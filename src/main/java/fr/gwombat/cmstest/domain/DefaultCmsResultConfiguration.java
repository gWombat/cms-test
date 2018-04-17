package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;
import fr.gwombat.cmstest.registry.ConverterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCmsResultConfiguration implements CmsResultConfigurer {

    @Override
    public String getRootNodePrefix() {
        return "repo/fr/";
    }

    @Override
    public void addConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverter(new PhoneNumberConverter());
    }

    @Override
    public void addPostConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addPostConverter(new PersonPostConverter());
    }
}
