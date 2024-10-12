package com.example.demo.dao;

import com.example.demo.dto.ContactDTO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContactDAOImpl implements ContactDAO {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static Set<ContactDTO> contacts = new HashSet<>();

    public ContactDAOImpl() {
        ContactDTO contact = new ContactDTO("Somdev","Rajaurai","8130652722","somdev.rajauria@gmail.com");
        contacts.add(contact);
        contacts.add(new ContactDTO("Harish","Sharma","91547895","harish.sharma@gmail.com"));
    }

    @Override
    public Set<ContactDTO> fetchAll() {
        return contacts;
    }

    @Override
    public Set<ContactDTO> search(String searchTerm) {
        return contacts.stream().filter(contact -> isMatch(contact, searchTerm)).collect(Collectors.toSet());
    }

    @Override
    public boolean add(ContactDTO contactDTO) {
        if( contactDTO.getId().equalsIgnoreCase("s@gmail.com")) {
            return false;
        }
        return contacts.add(contactDTO);
    }

    @Override
    public boolean remove(ContactDTO contactDTO) {
        return contacts.remove(contactDTO);
    }

    @Override
    public boolean isMatch(ContactDTO contact, String term) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return contact.getFname().contains(term) || contact.getLname().contains(term) || contact.getPhone().contains(term) || contact.getEmail().contains(term);
    }

    public static void loadDb() {
        try (FileReader reader = new FileReader("contacts.json")) {
            List<ContactDTO> contacts = objectMapper.readValue(reader, new TypeReference<List<ContactDTO>>() {});
            //db.clear();
            for (ContactDTO c : contacts) {
                contacts.add( c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
