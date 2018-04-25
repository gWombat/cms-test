package fr.gwombat.cmstest.mapping.invoker;

import fr.gwombat.cmstest.exceptions.CmsMappingException;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

/*
https://dzone.com/articles/hacking-lambda-expressions-in-java
 */
public class SetterInvoker {

    public static void invokeSetter(Object targetInstance, final Method method, Object argument) throws CmsMappingException {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            final MethodHandle unreflect = lookup.unreflect(method);
            final BiConsumer<Object, Object> biConsumer = createSetter(lookup, unreflect);
            biConsumer.accept(targetInstance, argument);

        } catch (IllegalAccessException e) {
            throw new CmsMappingException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static BiConsumer<Object, Object> createSetter(final MethodHandles.Lookup lookup, final MethodHandle setter) throws CmsMappingException {
        try {
            final CallSite site = LambdaMetafactory.metafactory(lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    setter,
                    setter.type());
            return (BiConsumer<Object, Object>) site.getTarget().invokeExact();
        } catch (Throwable throwable) {
            throw new CmsMappingException(throwable);
        }
    }

}
