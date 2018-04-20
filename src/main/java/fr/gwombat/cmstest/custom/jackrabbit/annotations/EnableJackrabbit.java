package fr.gwombat.cmstest.custom.jackrabbit.annotations;

import fr.gwombat.cmstest.configuration.EnableCms;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitBaseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 19/04/2018
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableCms
@Import(JackrabbitBaseConfiguration.class)
public @interface EnableJackrabbit {
}
