package com.example.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactFileRepository contactFileRepository;

    @Value("${app.profile}")
    private String appProfile;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Загружаем контакты при инициализации приложения, если профиль "init"
        if ("init".equalsIgnoreCase(appProfile)) {
            loadContactsFromFile();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Show all contacts");
            System.out.println("2. Add new contact");
            System.out.println("3. Delete contact for email");
            System.out.println("4. Save contacts to file");
            System.out.println("5. Exit");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    contactService.printAllContacts();
                    break;
                case "2":
                    System.out.println("Введите контакт (Ф. И. О.;номер телефона;email):");
                    String contactInput = scanner.nextLine();
                    String[] contactData = contactInput.split(";");
                    if (contactData.length == 3) {
                        contactService.addContact(new Contact(contactData[0], contactData[1], contactData[2]));
                    } else {
                        System.out.println("Неверный формат ввода!");
                    }
                    break;
                case "3":
                    System.out.println("Введите email для удаления:");
                    String emailToDelete = scanner.nextLine();
                    contactService.removeContactByEmail(emailToDelete);
                    break;
                case "4":
                    saveContactsToFile();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Неизвестная команда!");
            }
        }
    }

    private void loadContactsFromFile() {
        try {
            contactService.getContacts().addAll(contactFileRepository.loadContacts());
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке контактов из файла.");
        }
    }

    private void saveContactsToFile() {
        try {
            contactFileRepository.saveContacts(contactService.getContacts());
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении контактов в файл.");
        }
    }
}
