package com.example.selling_cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.selling_cars.entity.ContactMessage;
import com.example.selling_cars.repository.ContactMessageRepository;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Trả về contact.html
    }

    @PostMapping("/contact/submit")
    public String submitContactForm(
            @RequestParam("fullName") String fullName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setFullName(fullName);
        contactMessage.setPhoneNumber(phoneNumber);
        contactMessage.setEmail(email);
        contactMessage.setMessage(message);

        contactMessageRepository.save(contactMessage);

        redirectAttributes.addFlashAttribute("successMessage", "Tin nhắn của bạn đã được gửi thành công!");
        return "redirect:/contact";
    }
}