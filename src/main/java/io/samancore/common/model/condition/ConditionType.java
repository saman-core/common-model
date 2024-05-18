package io.samancore.common.model.condition;

import lombok.Getter;

@Getter
public enum ConditionType {
    VALUE(0),
    VISIBLE(1),
    DISABLE(2),
    ALERT(3),
    VALIDATE(4),
    OPTIONS(5);

    private final int value;

    ConditionType(int value) {
        this.value = value;
    }
}
