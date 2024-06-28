package org.example.loadData;

import org.example.ContactManager.Contact;
import org.example.ContactManager.ContactManager;
import org.springframework.beans.factory.annotation.Value;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class InitEnvLoader implements EnvLoader {

    Logger logger = LogManager.getLogManager().getLogger("newLog");
    @Value("${app.fullNameRegex.regexp}")
    String fullNameRegex;
    @Value("${app.emailRegex.regexp}")
    String emailRegex;
    @Value("${app.phoneRegex.regexp}")
    String phoneRegex;
    @Value("${app.loadFromPath}")
    String filePath;

    @Override
    public void load(ContactManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] params = line.split(";");
                if (params.length == 3) {
                    if (params[0].strip().matches(fullNameRegex)
                            && params[1].strip().matches(phoneRegex)
                            && params[2].strip().matches(emailRegex)) {
                        manager.addContact(new Contact(
                                params[0], params[1], params[2]));
                    } else logger.info("something do not matches with configure parameters");
                } else logger.info("length != 3");
            }
        } catch (IOException e) {
            System.err.println(MessageFormat.format(
                    "Файл по указанному пути ({0}) не найден", filePath));
        }
    }
}
