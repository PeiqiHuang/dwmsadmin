package com.dwms.common.util;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

/**
 * yaml资源加载类
 * @author windy
 * @date 2018-10-26
 */
public class YamlFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null) {
            return super.createPropertySource(name, resource);
        }

        List<PropertySource<?>> ps = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        return ps.isEmpty() ? null : ps.get(0);
    }
}
