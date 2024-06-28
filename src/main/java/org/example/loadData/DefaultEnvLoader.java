package org.example.loadData;

import org.example.ContactManager.ContactManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class DefaultEnvLoader implements EnvLoader{
    @Override
    public void load(ContactManager manager) {

    }
}
