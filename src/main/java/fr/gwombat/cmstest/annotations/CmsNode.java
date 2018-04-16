package fr.gwombat.cmstest.annotations;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsNode {

    String name() default "";
}
