package org.example;
import org.example.Config.DefaultAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);
        context.getBean(App.class).appStart();
    }
}