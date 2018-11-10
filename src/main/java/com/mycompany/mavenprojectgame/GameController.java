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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameController implements Initializable {
    
    @FXML
    GridPane board;
    
    @FXML
    Label currentPlayerLabel;
    
    public Game game;
    
    private Button createCell() {
        Button button = new Button();
        button.setPrefWidth(90);
        button.setPrefHeight(90);
        button.setOpacity(0.7);
        button.getStyleClass().add("board-cell");
        button.setOnAction(new EventHandler<ActionEvent>() {    
            @Override
            public void handle(ActionEvent event) {
                Button source = (Button) event.getSource();
                int col = GridPane.getColumnIndex(source);
                int row = GridPane.getRowIndex(source);
                if (game.isCellEmpty(row, col)) {
                    game.setStone(row, col);
                
                    source.setStyle("-fx-background-image: url('" + game.getPlayerStone() + "');");
                
                    if (!game.isGameOver(row, col)) {
                        game.nextTurn();
                        setPlayerLabel(game.currentPlayer);
                    } else {
                        game.setWinner();
                        System.out.println("Game over - " + game.getWinner().name + " Win");
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Game Over !!!");
                        alert.setHeaderText(null);
                        alert.setContentText(game.getWinner().name + " Win");

                        alert.showAndWait();
                        
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartGame.fxml"));
                            source.getScene().setRoot(root);
                        } catch (IOException ex) {
                            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        
        return button;
    }
    
    public void createBoard() {
        board.setHgap(5);
        board.setVgap(5);
        for (int row = 0; row < Game.NUM_OF_ROWS; ++row) {
            for (int col = 0; col < Game.NUM_OF_COLS; ++col) {
                Button cell = createCell();
                board.add(cell, col, row);
            }
        }
    }
    
    public void setPlayerLabel(Player player) {
        currentPlayerLabel.setText("Player Turn: " + player.name);
        if (player.id == 1) {
            currentPlayerLabel.setTextFill(Color.RED);
        } else {
            currentPlayerLabel.setTextFill(Color.BLUE);
        }
    }
    
    public void newGame() {
        board.getChildren().removeAll();
        createBoard();
        
        game = Game.getInstance();
        game.start();
        
        setPlayerLabel(game.currentPlayer);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newGame();
    }    
}
