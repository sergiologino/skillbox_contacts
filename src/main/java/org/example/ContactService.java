// Сервис, реализующий бизнес-логику работы с каонтактами: в нашем случсае это запись и чтение контактов

package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private final String filePath;
    private final List<Contact> contacts;

//    здесь убрал аннотацию  Autowired т.к. конструктор один и спринг инжектит зависимости автоматически
    public ContactService(@Value("${contacts.file-path}") String filePath) {
        this.filePath = filePath;
        this.contacts = new ArrayList<>();
        loadContacts();
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
        }
    }

    public void saveContacts(List<Contact> contacts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                writer.println(String.format("%s;%s;%s", contact.getFullName(), contact.getPhoneNumber(), contact.getEmail()));
            }
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }
}

