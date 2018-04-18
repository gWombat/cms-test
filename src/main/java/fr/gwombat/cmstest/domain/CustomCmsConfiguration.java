package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.configuration.EnableCms;
import fr.gwombat.cmstest.configuration.adapters.AbstractJackrabbitConfiguration;
import fr.gwombat.cmstest.registry.ConverterRegistry;
import fr.gwombat.cmstest.registry.TemporalRegistry;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableCms
public class CustomCmsConfiguration extends AbstractJackrabbitConfiguration {

    @Override
    public String getLanguage() {
        return "fr";
    }

    @Override
    public String getBrandNode() {
        return "my-site";
    }

    @Override
    public String getBrandNodeSpecific() {
        return "my-site-specific";
    }

    @Override
    public String getWorkspace() {
        return null;
    }

    @Override
    public void addConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverter(new PhoneNumberConverter());
    }

    @Override
    public void addPostConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addPostConverter(new PersonPostConverter());
    }

    @Override
    public void addDateTimeFormatter(TemporalRegistry temporalRegistry) {
        temporalRegistry.addDateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
