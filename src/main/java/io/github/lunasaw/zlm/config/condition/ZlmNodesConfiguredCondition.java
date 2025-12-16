package io.github.lunasaw.zlm.config.condition;

import io.github.lunasaw.zlm.node.ZlmNode;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.Bindable;

import java.util.Map;

public class ZlmNodesConfiguredCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();

        // 绑定 zlm.nodes 为 Map，看是否存在且至少有一个节点 enable 为 true
        Map<String, ZlmNode> nodes = Binder.get(env)
                .bind("zlm.nodes", Bindable.mapOf(String.class, ZlmNode.class))
                .orElse(null);

        boolean match = nodes != null
                && !nodes.isEmpty()
                && nodes.values().stream().anyMatch(ZlmNode::isEnable);

        ConditionMessage.Builder message = ConditionMessage.forCondition("ZLM nodes");

        return match
                ? ConditionOutcome.match(
                message.found("enabled zlm.nodes")
                        .items(nodes.entrySet().stream()
                                .filter(e -> e.getValue().isEnable())
                                .map(Map.Entry::getKey)
                                .toList()
                        )
        )
                : ConditionOutcome.noMatch(
                message.didNotFind("any enabled zlm.nodes").atAll()
        );
    }
}