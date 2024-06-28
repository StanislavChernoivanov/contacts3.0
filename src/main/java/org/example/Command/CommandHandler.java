package org.example.Command;

import org.example.ContactManager.ContactManager;
import org.example.loadData.EnvLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CommandHandler {
    @Value("${app.fullNameRegex.regexp}")
    String fullNameRegex;
    @Value("${app.emailRegex.regexp}")
    String emailRegex;
    @Value("${app.phoneRegex.regexp}")
    String phoneRegex;
    @Value("${app.pathToSave}")
    String pathToSave;
    @Value("${app.fileNameRegex}")
    String fileNameRegex;

    private final String incorrectAttributeMessage;

    private final String incorrectCommandMessage;

    public CommandHandler(ContactManager manager, EnvLoader loader) {
        this.manager = manager;
        this.loader = loader;
        incorrectAttributeMessage = "Не корректно введены атрибуты команды добавления." +
        " Вводите детали контакта в соответствии с инструкцией выше";
        incorrectCommandMessage = "Не корректно введена команда." +
                " Вводите команду в соответствии с инструкцией выше";
    }

    private final ContactManager manager;

    private final EnvLoader loader;

    public void handleCommand(String input) {
        String command;
        String attributes = "";
        String [] separateInput = input.strip().split(" ");
        if(separateInput.length > 2) {
            int partitionIndex = input.indexOf(" ");
            command = input.substring(0, partitionIndex);
            attributes = input.substring(partitionIndex);
        } else if (separateInput.length == 2) {
            command = separateInput[0];
            attributes = separateInput[1];
        } else if (separateInput.length == 1) {
            command = separateInput[0];
        } else {
            System.out.println(incorrectAttributeMessage);
            return;
        }
        switch (command.strip()) {
            case ("SAVE") :
                handleSaveCommands(attributes.strip());
                break;

            case ("ADD") :
                handleAddCommand(attributes.strip());
                break;

            case ("SHOW") :
                handleShowCommand();
                break;

            case("REMOVE") :
                handleRemoveCommand(attributes.strip());
                break;
            default:
                System.out.println(incorrectCommandMessage);
        }
    }

    private void handleAddCommand(String attributes) {
        String[] details = attributes.split(";");
        if (details.length == 3) {
            if (details[0].strip().matches(fullNameRegex)
                    && details[1].strip().matches(phoneRegex)
                    && details[2].strip().matches(emailRegex)) {
                System.out.println(manager.addContact(new org.example.ContactManager.Contact(
                        details[0], details[1], details[2]))
                        ? "Контакт внесен в список"
                        : "Контакт уже находится в списке");
            } else System.out.println(incorrectAttributeMessage);
        } else System.out.println(incorrectAttributeMessage);
    }

    private void handleSaveCommands(String fileName){
        if(fileName.matches(fileNameRegex)) {
            File file = new File(pathToSave + fileName);
            try(FileOutputStream outputStream = new FileOutputStream(file)) {
                manager.saveContacts(outputStream);
                System.out.println("Данные сохраны в файле " + pathToSave + fileName);
            }catch (FileNotFoundException e) {
                System.out.println("Файл не может быть найден или создан");
            } catch (IOException e) {
                System.err.println("Ошибка записи в файл");
            }
        } else System.out.println("Некорректное имя файла");
    }

    private void handleShowCommand() {
        System.out.println("Контакты пользователя:");
        manager.showContacts();
    }

    private void handleRemoveCommand(String email){
        System.out.println(manager.removeContact(email.strip())
                ? "Контакт удален из списка"
                : "Указанный e-mail отсутствует в списке контактов");
    }

    @PostConstruct
    private void loadDataFromFile() {
        loader.load(manager);
    }

}
