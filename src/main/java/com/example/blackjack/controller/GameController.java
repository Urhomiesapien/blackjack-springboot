package com.example.blackjack.controller;

import com.example.blackjack.model.Deck;
import com.example.blackjack.model.Player;
import com.example.blackjack.model.Dealer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class GameController {

    private Deck deck = new Deck();
    private Player player = new Player();
    private Dealer dealer = new Dealer();
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private String redirect = "redirect:/";

    @GetMapping("/")
    public String home(Model model) {
        if(player.getHand().isEmpty() && dealer.getHand().isEmpty()) {
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
        }

        model.addAttribute("playerHand", player.getHand());
        model.addAttribute("dealerHand", dealer.getHand());
        model.addAttribute("playerTurn", playerTurn);
        model.addAttribute("gameOver", gameOver);
        model.addAttribute("deckOver", deck.isEmpty());
        model.addAttribute("playerScore", player.getScore());
        model.addAttribute("dealerScore", dealer.getScore());

        return "index";
    }
    
    @PostMapping("/hit")
    public String hit(Model model) {
        if(playerTurn && !deck.isEmpty() && player.getScore() <= 21) {
            player.addCard(deck.drawCard());
        }
        if(player.getScore() > 21) {
            stand(model);
        }
        return redirect;
    }

    @PostMapping("/stand")
    public String stand(Model model) {
        playerTurn = false;

        while(!deck.isEmpty() && dealer.getScore() < 17) {
            dealer.addCard(deck.drawCard());
        }
        
        gameOver = true;
        return redirect;
    }
    
    @PostMapping("/restart")
    public String restart(Model model) {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        playerTurn = true;
        gameOver = false;
        
        return redirect;
    }
    
    
}
