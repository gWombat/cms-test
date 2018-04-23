package fr.gwombat.cmstest.mapping.annotations;

import java.lang.annotation.*;

/**
 * Annotation marqueur. Non obligatoire!
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicNodeName {

    String key() default "";

}
