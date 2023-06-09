package com.tuananhdo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ConfigurationResource implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registerResourceHandler(registry,"post-photos");
      registerResourceHandler(registry,"user-photos");
    }
    private void registerResourceHandler(ResourceHandlerRegistry registry,String dirName){
        Path dirPath = Paths.get(dirName).toAbsolutePath();
        registry.addResourceHandler("/" + dirPath.getFileName() + "/**")
                .addResourceLocations("file:" + dirPath + "/");
    }
}
