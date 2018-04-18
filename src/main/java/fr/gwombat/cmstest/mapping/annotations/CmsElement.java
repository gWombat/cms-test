package fr.gwombat.cmstest.mapping.annotations;

import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.DefaultConverter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;

import java.lang.annotation.*;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CmsElement {

    String nodeName() default "";

    Class<? extends Converter> converter() default DefaultConverter.class;

    Class<? extends PostConverter>[] postConverters() default {};

}
