package com.unionestate.cm.infrastructure.common.datasource;

import java.util.HashMap;
import java.util.Map;

public enum KnownTenants {
    SYSTEM("system");

    public final String description;

    KnownTenants(String description) {
        this.description = description;
    }

    private static final Map<String, KnownTenants> BY_DESCRIPTION = new HashMap<>();

    static {
        for (KnownTenants e : values()) {
            BY_DESCRIPTION.put(e.description, e);
        }
    }

    public static KnownTenants findByDescription(String description) {
        return BY_DESCRIPTION.get(description);
    }
}