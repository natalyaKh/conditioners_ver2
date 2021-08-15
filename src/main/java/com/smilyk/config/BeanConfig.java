package com.smilyk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * class that creates Spring Beans
 */
@Component
public class BeanConfig {
    /**
     * bean that create BCryptPasswordEncoder Bean
     *
     * @return new {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * method that return SpringApplicationContext with properties
     * @return {@link SpringApplicationContext}
     */
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    /**
     * @return {@link AppProperties}
     */
    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
}
