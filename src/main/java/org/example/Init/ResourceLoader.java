package org.example.Init;

import java.net.URL;

public class ResourceLoader {
    public static URL getResource(String resourcePath) {
        return ResourceLoader.class.getClassLoader().getResource(resourcePath);
    }
}