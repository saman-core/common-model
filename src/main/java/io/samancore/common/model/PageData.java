package io.samancore.common.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder(
        setterPrefix = "set",
        toBuilder = true,
        builderMethodName = "newBuilder"
)
public class PageData<T> {
    long count;
    List<T> data;
}
