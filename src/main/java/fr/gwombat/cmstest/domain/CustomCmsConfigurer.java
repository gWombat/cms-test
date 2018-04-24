package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.configuration.EnableCms;
import fr.gwombat.cmstest.custom.jackrabbit.AbstractJackrabbitConfigurer;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistry;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistry;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@EnableCms
@Configuration
public class CustomCmsConfigurer extends AbstractJackrabbitConfigurer {

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
        converterRegistry.addPostConverter(new PersonPostConverter());
    }

    @Override
    public void addDateTimeFormatters(TemporalRegistry temporalRegistry) {
        temporalRegistry
                .addDateTimeFormatter(DateTimeFormatter.ofPattern("dd/Mm/yyyy HH:mm:ss"));
    }
}
