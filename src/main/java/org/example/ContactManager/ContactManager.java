package org.example.ContactManager;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Getter
@Component
public class ContactManager {

    private final Map<String, Contact> contacts;

    public ContactManager(){
        contacts = new HashMap<>();
    }

    public boolean addContact(Contact contact) {
        if (contacts.keySet().stream().anyMatch(k -> k.equals(contact.getEmail()))) return false;
        else {
            contacts.put(contact.getEmail(), contact);
            return true;
        }
    }

    public void showContacts() {
        StringBuilder builder = new StringBuilder();
        contacts.values().forEach(c -> builder.append(String.format(
                "%s | %s | %s\n", c.getFullName(), c.getPhone(), c.getEmail())));
        System.out.println(builder);
    }

    public void saveContacts(FileOutputStream outputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        contacts.values().forEach(contact -> builder.append(String.format("%s; %s; %s\n",
                contact.getFullName(), contact.getPhone(), contact.getEmail())));
        outputStream.write(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    public boolean removeContact(String email) {
        if (contacts.containsKey(email.strip())) {
            contacts.remove(email);
            return true;
        } else return false;
    }



}
