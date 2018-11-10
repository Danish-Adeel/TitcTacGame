package com.mycompany.mavenprojectgame;

import java.util.Date;

public class Game {
    public static int NUM_OF_PLAYERS = 2;
    public static int NUM_OF_ROWS = 5;
    public static int NUM_OF_COLS = 5;
    
    public Player currentPlayer;
    private Player winner = null;
    
    public Player[] players;
    
    public int turns;
    
    public Date startTime;
    
    public int[][] grid;
    
    public int numRows;
    public int numCols;
    
    private static Game instance;
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(NUM_OF_ROWS, NUM_OF_COLS);
        }
        return instance;
    }
    
    private void setGrid() {
        grid = new int[numRows][numCols];
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private Game(int rows, int cols) {
        numRows = rows;
        numCols = cols;
        
        setGrid();
    }
    
    public void setPlayers(String ...playerNames) {
        players = new Player[NUM_OF_PLAYERS];
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players[i] = new Player();
            players[i].id = i+1;
            players[i].name = playerNames[i];//"Player " + players[i].num;
        }
    }
    
    public void start() {
        setGrid();
        currentPlayer = players[0];
        turns = 0;
        startTime = new Date();
    }
    
    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == 0;
    }
    
    public void setStone(int row, int col) {
        grid[row][col] = currentPlayer.id;
    }
    
    public void nextTurn() {
        currentPlayer.turns++;
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
        turns++;
    }
    
    public String getPlayerStone() {
        if (currentPlayer == players[0]) {
            return "/images/red-stone.png";
        } else {
            return "/images/blue-stone.png";
        }
    }
    
    public boolean checkColumn(int col) {
        int count = 0;
        for (int row = 0; row < numRows; row++) {
            if (grid[row][col] == currentPlayer.id) {
                count++;
            }
        }
        
        return count >= 3;
    }
    
    public boolean checkRow(int row) {
        int count = 0;
        for (int col = 0; col < numCols; col++) {
            if (grid[row][col] == currentPlayer.id) {
                count++;
            }
        }
        
        return count >= 3;
    }
    
    public boolean checkDiagonals(int row, int col) {
        return checkDiagonal(row, col) || checkAntiDiagonal(numRows - 1 - row, col);
    }
    
    public boolean checkDiagonal(int row, int col) {
        int count = 0;
        int x = 0, y = 0;
        if (row > col) {
            x = row - col;
            y = 0;
        } else if (col > row) {
            x = 0;
            y = col - row;
        }
        
        while (x < numRows && y < numCols) {
            if (grid[x][y] == currentPlayer.id) {
                count++;
            }
            x++;
            y++;
        }
        
        return count >= 3;
    }
    
    public boolean checkAntiDiagonal(int row, int col) {
        int count = 0;
        int x = 0, y = 0;
        if (row > col) {
            x = row - col;
            y = 0;
        } else if (col > row) {
            x = 0;
            y = col - row;
        }
        
        while (x < numRows && y < numCols) {
            if (grid[numRows - 1 - x][y] == currentPlayer.id) {
                count++;
            }
            x++;
            y++;
        }
        
        return count >= 3;
    }
    
    public boolean isGameOver(int row, int col) {
        return checkColumn(col) || checkRow(row) || checkDiagonals(row, col);
    }
    
    public void setWinner() {
        if (currentPlayer == players[0])
            winner = players[1];
        else
            winner = players[0];
    }
    
    public Player getWinner() {
        return winner;
    }
}
