package com.example.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    protected List<String> hand;

    public User() {
        this.hand = new ArrayList<>();
    }

    public void addCard(String card) {
        hand.add(card);
    }

    public List<String> getHand() {
        return hand;
    }

    public void resetHand() {
        hand.clear();
    }
}
