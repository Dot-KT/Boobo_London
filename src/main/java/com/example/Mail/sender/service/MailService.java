package com.example.Mail.sender.service;

import com.example.Mail.sender.models.Guest;
import com.example.Mail.sender.repositories.MailRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MailService {


    private final JavaMailSender mailSender;
    private final MailRepo mailRepo;

    public MailService(JavaMailSender mailSender, MailRepo mailRepo) {
        this.mailSender = mailSender;
        this.mailRepo = mailRepo;
    }

    public Guest getGuestByEmail(String email) {
        return mailRepo.findByEmail(email).orElseThrow();
    }
    public List<Guest> getGuests() {
        return mailRepo.findAll();
    }
    public String addGuest(Guest guest) {
        return mailRepo.save(guest).toString();
    }
    public String addGuests(List<Guest> guests) {
        mailRepo.saveAll(guests);
        return "success";
    }

    public String sendMailToAllClients(){
        List<Guest> guests = getGuests();
        for (Guest guest : guests) {
            sendNewsletter(guest.getEmail(), guest.getName());
        }
        return "All clients have been sent";
    }

    public String unSubscribeGuest(String email) {
        Guest guest = getGuestByEmail(email);
        mailRepo.delete(guest);
        return guest.getName()+" has been unsubscribed";
    }

    public void sendNewsletter(String to, String guestName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Load the HTML template
            String html = loadTemplate("templates/welcome.html");

            // Replace placeholders
            html = html.replace("{{name}}", guestName);

            helper.setFrom("boobofamilyhomeslimited@gmail.com");
            helper.setTo(to);
            helper.setSubject("We Miss You! 👋 - Boobo London");
            helper.setText(html, true);  // true = HTML

            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String loadTemplate(String path) throws IOException {
        try (InputStream is = new ClassPathResource(path).getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
