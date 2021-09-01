package com.example.shambles.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurationPhotoProfil  implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/photoprofil/**").addResourceLocations("/FichierPP/photoprofil/")
         .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		
	}

}
