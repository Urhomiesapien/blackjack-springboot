package com.example.blackjack.controller;

import com.example.blackjack.model.Deck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private Deck deck = new Deck();
    private List<String> drawnCards = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Click the button to draw a card");
        model.addAttribute("cards", drawnCards);
        model.addAttribute("deckOver", deck.isEmpty());
        return "index";
    }

    @PostMapping("/draw")
    public String drawCard(Model model) {
        if (!deck.isEmpty()) {
            String card = deck.drawCard();
            drawnCards.add(card);
        }

        model.addAttribute("message", deck.isEmpty() ? "Deck Over" : "Card Drawn!");
        model.addAttribute("cards", drawnCards);
        model.addAttribute("deckOver", deck.isEmpty());
        return "index";
    }

    @PostMapping("/restart")
    public String restartDeck(Model model) {
        deck = new Deck();
        drawnCards.clear();
        model.addAttribute("message", "Deck restarted!");
        model.addAttribute("cards", drawnCards);
        model.addAttribute("deckOver", false);
        return "index";
    }
}
