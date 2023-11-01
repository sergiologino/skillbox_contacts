package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.List;

@Component
public class ContactsApplication {


    private final ContactService contactService;
    private final String contactFilePath;
    private final List<Contact> contacts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    // инжектим свойство пути файла для дальнейшего использования в методах
    @Autowired
    public ContactsApplication(ContactService contactService, @Value("${contacts.file-path}") String contactFilePath) {
        this.contactService = contactService;
        this.contactFilePath = contactFilePath;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ContactsApplication app = context.getBean(ContactsApplication.class);
        app.start();
        context.close();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("1. Show contacts");
            System.out.println("2. Add contact");
            System.out.println("3. Delete contact by email");
            System.out.println("4. Save contacts to file");
            System.out.println("5. Exit");
            System.out.print("Enter option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    showContacts();
                    break;
                case "2":
                    addContact();
                    break;
                case "3":
                    deleteContactByEmail();
                    break;
                case "4":
                    contactService.saveContacts(contacts);
                    System.out.println("Contacts have been saved to "+contactFilePath);
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void showContacts() {
        contacts.forEach(contact ->
                System.out.println(contact.getFullName() + " | " + contact.getPhoneNumber() + " | " + contact.getEmail()));
    }

    private void addContact() {
        System.out.print("Enter full name, phone number, and email (separated by ';'): ");
        String[] parts = scanner.nextLine().split(";");
        if (parts.length == 3) {
            contacts.add(new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim()));
            System.out.println("Contact added.");
        } else {
            System.out.println("Invalid input format.");
        }
    }

    private void deleteContactByEmail() {
        System.out.print("Enter email of the contact to delete: ");
        String email = scanner.nextLine();
        contacts.removeIf(contact -> contact.getEmail().equalsIgnoreCase(email));
        System.out.println("Contact deleted if it existed.");
    }


}