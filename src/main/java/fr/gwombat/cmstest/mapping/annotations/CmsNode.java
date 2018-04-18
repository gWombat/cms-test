package fr.gwombat.cmstest.mapping.annotations;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsNode {

    String name() default "";
}
