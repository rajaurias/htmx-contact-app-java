package com.example.demo.controller;

import com.example.demo.dao.ContactDAO;
import com.example.demo.dto.Archiver;
import com.example.demo.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ContactController {

    @Autowired
    private ContactDAO contactDAO;

    public ContactController() {
        System.out.println("ContactController");
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/contacts";
    }

    @GetMapping({"/contacts"})
    public String contacts(@RequestParam(required = false) String q, Model model) {
        Set<ContactDTO> retVal;
        String view = null;
        if(StringUtils.hasLength(q)) {
            System.out.println("q is not blank");
            retVal = contactDAO.search(q);
            view = "fragments/rows :: rows";
        } else {
            retVal = contactDAO.fetchAll();
            view = "contacts";
        }
        model.addAttribute("contacts", retVal);
        model.addAttribute("archiver", Archiver.get());
        return view;
    }

    @GetMapping({"/search"})
    public String search(@RequestParam(required = false) String q, Model model) {
        Set<ContactDTO> retVal = new HashSet<>();
        String view = null;
        if(StringUtils.hasLength(q)) {
            System.out.println("q is not blank");
            retVal = contactDAO.search(q);
            view = "fragments/rows :: rows";
        }
        model.addAttribute("contacts", retVal);
        model.addAttribute("archiver", Archiver.get());
        return view;
    }

    @GetMapping("/contacts/{contactId}/edit")
    public String editContact(@PathVariable(required = false) String contactId, Model model) {
        System.out.println("Edit contact id "+contactId);
        model.addAttribute("contact", contactDAO.search(contactId).stream().findFirst().get() );
        return "edit";
    }

    @GetMapping("/contacts/new")
    public String createNewContactForm( Model model) {
        model.addAttribute("contact", new ContactDTO() );
        return "new";
    }

    @PostMapping("/contacts/new")
    public String createNewContact( @ModelAttribute("contact") ContactDTO contact, Model model) {
        boolean isCreated = contactDAO.add(contact);
        if( isCreated ) {
            return "redirect:/contacts";
        }
        return "new";
    }

    @GetMapping("/contacts/{contactId}")
    public String displayContact( @PathVariable("contactId") String contactId, Model model) {
        model.addAttribute("contact", contactDAO.search(contactId).stream().findFirst().get() );
        return "show";
    }

}
