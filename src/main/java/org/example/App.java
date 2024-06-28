package org.example;
import org.example.Command.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class App {

    private final CommandHandler handler;
    private final String info = "Приложение \"Контакты\" \n" +
        "Здесь вы можете: \n" +
        "\t- добавить новый контакт в ваш список, используя " +
                "команду \"ADD\" (пример - ADD \"Ф. И. О.; номер телефона; email\"); \n" +
        "\t- удалить контакт из списка, используя команду \"REMOVE\" (пример - REMOVE \"ваш email\"); \n" +
        "\t- посмотреть все контакты из списка, используя команду \"SHOW\"; \n" +
        "\t- сохранить контакты в файл, используя команду \"SAVE\" (пример - SAVE \"путь к файлу\")";

    public App(CommandHandler handler) {
        this.handler = handler;
    }

    public void appStart() {
        System.out.println(info.trim());
        while(true) {
            System.out.println("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            handler.handleCommand(input);
        }
    }
}
