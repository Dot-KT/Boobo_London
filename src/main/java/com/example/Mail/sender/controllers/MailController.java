package com.example.Mail.sender.controllers;

import com.example.Mail.sender.models.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Mail.sender.service.MailService;

import java.util.List;

@RestController
@RequestMapping("mails")
public class MailController {

    private final MailService emailService;

    public MailController(MailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("newsletter")
    public ResponseEntity<String> sendNewsletter(@RequestParam String to, @RequestParam String name) {
        emailService.sendNewsletter(to, name);
        return ResponseEntity.ok("Newsletter sent!");
    }
    @PostMapping("newsletters")
    public ResponseEntity<String> sendNewsletterToAllGuests() {
        return ResponseEntity.ok(emailService.sendMailToAllClients());
    }

    @PostMapping("add_guest")
    public ResponseEntity<String> addGuest(@RequestBody Guest guest) {
        return new ResponseEntity<>(emailService.addGuest(guest), HttpStatus.CREATED);
    }

    @PostMapping("add_guests")
    public ResponseEntity<String> addGuests(@RequestBody List<Guest> guests) {
        return new ResponseEntity<>(emailService.addGuests(guests), HttpStatus.CREATED);
    }

    @GetMapping("all_guests")
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(emailService.getGuests());
    }

    @GetMapping("get_guest/{email}")
    public ResponseEntity<Guest> getGuest(@PathVariable String email) {
        return ResponseEntity.ok(emailService.getGuestByEmail(email));
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<String> unsubscribeGuest(@PathVariable String email) {
        return ResponseEntity.ok(emailService.unSubscribeGuest(email));
    }
}