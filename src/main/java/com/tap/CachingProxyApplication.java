package com.tap;

import com.tap.controller.ProxyController;
import com.tap.model.CacheModel;

public class CachingProxyApplication {

    public static void main(String[] args) {
        // Delegate to a static method in another class to keep main small
        try {
			ProxyController.start(args);
			System.out.println(new CacheModel().getFromCache("http://localhost:3000/posts/1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

