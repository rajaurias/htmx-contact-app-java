package com.example.demo.dao;

import com.example.demo.dto.ContactDTO;

import java.util.Set;

public interface ContactDAO {
    Set<ContactDTO> fetchAll();

    Set<ContactDTO> search(String searchTerm);

    boolean add(ContactDTO contactDTO);

    boolean remove(ContactDTO contactDTO);

    boolean isMatch(ContactDTO contact, String term);
}
