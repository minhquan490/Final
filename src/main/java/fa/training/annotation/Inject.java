package fa.training.annotation;

import fa.training.annotation.resolver.InjectType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Inject {
    public InjectType value() default InjectType.ALL;
}
