package fr.gwombat.cmstest.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({CmsResultInternalConfiguration.class})
public @interface EnableCms {

}
