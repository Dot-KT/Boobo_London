package com.example.Mail.sender.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "guests")
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    private int id;
    private String name;
    private String email;

    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
