package org.example.Config;

import org.example.loadData.EnvLoader;
import org.example.loadData.InitEnvLoader;
import org.springframework.context.annotation.*;

@Configuration
@Profile("init")
public class InitAppConfig {

    @Bean
    public EnvLoader envLoader() {
        return new InitEnvLoader();
    }
}
