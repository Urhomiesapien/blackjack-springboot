package com.example.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<String> cards;

    public Deck() {
        resetDeck();
    }

    public void resetDeck() {
        cards = new ArrayList<>();
        String[] suits = {"H", "D", "C", "S"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};

        for(String s: suits) {
            for(String r: ranks) {
                cards.add(r + s);
            }
        }
        Collections.shuffle(cards);
    }

    public String drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
