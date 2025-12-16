package io.github.lunasaw.zlm.config.condition;

import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.boot.context.properties.bind.Binder;

public class ZlmHookEnabledCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean enabled = Binder.get(context.getEnvironment())
                .bind("zlm.hook.enable", Boolean.class)
                .orElse(false);

        return enabled ? ConditionOutcome.match() : ConditionOutcome.noMatch("zlm.hook.enable is false");
    }
}
