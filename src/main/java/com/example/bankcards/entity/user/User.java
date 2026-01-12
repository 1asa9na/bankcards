package com.example.bankcards.entity.user;

import java.util.List;
import java.util.Set;

import com.example.bankcards.entity.card.Card;

import lombok.Data;

@Data
public class User {

    private long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Card> cards;
    
}
