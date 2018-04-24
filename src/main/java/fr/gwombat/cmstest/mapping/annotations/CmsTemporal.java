package fr.gwombat.cmstest.mapping.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsTemporal {

    String format() default "";

}
