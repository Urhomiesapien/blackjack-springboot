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

    public int getScore() {
        int score = 0;
        int aceCount = 0;

        for(String card: hand) {
            char rank = card.charAt(0);
            switch(rank) {
                case 'T':
                case 'J':
                case 'K':
                case 'Q':
                    score += 10;
                    break;
                case 'A':
                    score += 11;
                    aceCount++;
                    break;
                default:
                    score += Character.getNumericValue(rank);
                    break;
            }
        }

        while(score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }
}
