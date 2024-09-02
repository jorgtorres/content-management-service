package com.unionestate.cm.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public interface Constants {

    String PROPERTY = "property";

    TypeReference<Map<String, Object>> MapStringObjectType = new TypeReference<>() {

    };
}
