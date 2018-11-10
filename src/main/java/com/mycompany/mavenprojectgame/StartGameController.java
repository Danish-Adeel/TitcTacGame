package com.mycompany.mavenprojectgame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartGameController implements Initializable {
    
    @FXML
    TextField player1;
    
    @FXML
    TextField player2;
    
    @FXML
    Button startButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Game game = Game.getInstance();
        if (game.players == null) {
            player1.setText("Player 1");
            player2.setText("Player 2");
        } else {
            player1.setText(game.players[0].name);
            player2.setText(game.players[1].name);
        }
        
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button source = (Button) event.getSource();
                
                String playerName1 = player1.getText();
                String playerName2 = player2.getText();
                
                Game.getInstance().setPlayers(playerName1, playerName2);
                
                try {
                    Parent root = FXMLLoader.load(StartGameController.class.getResource("/fxml/Scene.fxml"));
                    source.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(StartGameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
}
