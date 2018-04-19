package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.configuration.EnableCms;
import fr.gwombat.cmstest.core.CmsCall;
import fr.gwombat.cmstest.custom.jackrabbit.AbstractJackrabbitConfiguration;
import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistry;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistry;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

@EnableCms
@Configuration
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
    public void addDateTimeFormatters(TemporalRegistry temporalRegistry) {
        temporalRegistry
                .addDateTimeFormatter(DateTimeFormatter.ofPattern("dd/Mm/yyyy HH:mm:ss"))
                .addDateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public void addCallConfigurers(CallConfigurerRegistry callConfigurerRegistry) {

    }

    @Bean
    public CmsCall myPageCmsConfiguration() {
        final CmsCall cmsCall = new CmsCall();
        cmsCall.setPath("my-page");

        CmsCall childCall = new CmsCall();
        childCall.setPath("person");

        cmsCall.addCall(childCall);

        return cmsCall;
    }
}
