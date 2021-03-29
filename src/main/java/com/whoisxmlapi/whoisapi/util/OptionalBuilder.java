package com.whoisxmlapi.whoisapi.util;

import java.util.Optional;

public class OptionalBuilder {
    public static Optional<String> buildOptionalString(String s) {
        boolean isEmpty = null == s || s.isEmpty();
        return isEmpty ? Optional.empty() : Optional.of(s);
    }

    public static Optional<Integer> buildOptionalInt(Integer i) {
        return Optional.ofNullable(i);
    }
}
