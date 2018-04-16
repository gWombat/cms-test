package fr.gwombat.cmstest.annotations;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 13/04/2018
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsProperty {

    String name() default "";
}
