package com.huiyadan.pcr.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 自定义配置载入
 *
 * @author huiyadanli
 */
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    private static final String SECRETS_PATH = "secrets.path";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String path = environment.getProperty(SECRETS_PATH);
        if (StringUtils.isEmpty(path)) {
            System.out.println("错误！配置 secrets.path 为空！");
            return;
        }
        // 优先载入
        Resource secretsResource = new PathResource(path);
        if (!secretsResource.exists()) {
            System.out.println("错误！未获取到 secrets.yml 配置文件, 路径：" +  ((PathResource) secretsResource).getPath());
            secretsResource = new ClassPathResource("secrets.yml");
        }
        PropertySource<?> propertySource = loadYaml(secretsResource);
        environment.getPropertySources().addLast(propertySource);
    }

    private PropertySource<?> loadYaml(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.loader.load("custom-resource", path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load yaml configuration from " + path, ex);
        }
    }
}
