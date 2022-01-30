package com.vtr.camera.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlGeneratorImpl implements UrlGenerator {

    private final String urlPrefix;

    public UrlGeneratorImpl(@Value("${camera.url-prefix}") String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    @Override
    public String getImageUrlByName(String name) {
        return this.urlPrefix + name;
    }

}
