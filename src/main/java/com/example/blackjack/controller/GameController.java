package com.example.blackjack.controller;

import com.example.blackjack.model.Deck;
import com.example.blackjack.model.Player;
import com.example.blackjack.model.Dealer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {

    private Deck deck = new Deck();
    private Player player = new Player();
    private Dealer dealer = new Dealer();
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private String redirect = "redirect:/game";
    private String result = "";

    @GetMapping("/")
    public String menu() {
        return "menu";
    }

    @GetMapping("/play")
    public String play(Model model) {
    deck = new Deck();
    player.resetHand();
    dealer.resetHand();
    result = "";
    playerTurn = true;
    gameOver = false;

    player.addCard(deck.drawCard());
    player.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());

    return "redirect:/game";
}


    @GetMapping("/game")
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
        model.addAttribute("result", result);

        return "game";
    }
    @GetMapping("/gameover")
    public String gameOverPage(Model model) {
    model.addAttribute("result", result);
    model.addAttribute("playerScore", player.getScore());
    model.addAttribute("dealerScore", dealer.getScore());
    model.addAttribute("playerHand", player.getHand());
    model.addAttribute("dealerHand", dealer.getHand());
    return "gameover";
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

        int playerScore = player.getScore();
        int dealerScore = dealer.getScore();
        boolean gamestatus = true; 

        if (playerScore > 21) {
            gamestatus = false;
            result = "Player busted! Dealer wins.";
        } else if (playerScore == 21) {
            if (dealerScore == 21) {
                result = "It's a draw! Both have Blackjack!";
            } else {
                result = "Blackjack! Player wins!";
            }
        } else if (dealerScore > 21) {
            result = "Dealer busted! Player wins!";
        } else if (playerScore > dealerScore) {
            result = "Player wins with " + playerScore + " against dealer's " + dealerScore + ".";
        } else if (playerScore < dealerScore) {
            gamestatus = false;
            result = "Dealer wins with " + dealerScore + " against player's " + playerScore + ".";
        } else {
            result = "It's a draw!";
        }
        if (gamestatus) {
            return "redirect:/game";
        } else {
            return "redirect:/gameover";        }
        
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
