package io.github.lunasaw.zlm.config.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ZlmHookEnabledCondition.class)
public @interface ConditionalOnZlmHookEnabled {
}
