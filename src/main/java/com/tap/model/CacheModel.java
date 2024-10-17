package com.tap.model;

import java.util.HashMap;
import java.util.Map;

public class CacheModel {
    private Map<String, String> cache = new HashMap<>();

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    public String getFromCache(String key) {
        return cache.get(key);
    }

    public boolean isInCache(String key) {
        return cache.containsKey(key);
    }

    public void clearCache() {
        cache.clear();
    }
}
