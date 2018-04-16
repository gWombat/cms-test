package fr.gwombat.cmstest.annotations;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsPageResult {

    String rootNode() default "";
}
