package com.tap.service;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.tap.model.CacheModel;

public class ProxyService {
    private final CacheModel cache;

    public ProxyService(CacheModel cache) {
        this.cache = cache;
    }

    public String fetchFromOrigin(String url) throws IOException {
        if (cache.isInCache(url)) {
            return "X-Cache: HIT\n" + cache.getFromCache(url);
        }

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            cache.addToCache(url, responseBody);
            return "X-Cache: MISS\n" + responseBody;
        }
    }

    public void clearCache() {
        cache.clearCache();
    }
}

