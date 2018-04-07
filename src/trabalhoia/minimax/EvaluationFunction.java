/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.minimax;

import NineMensMorris.GameInfo;

/**
 *
 * @author heck_
 */
public final class EvaluationFunction {
    
    private GameInfo info;
    
    public int phase1() {
        return evaluate(18, 26, 1, 9, 10, 7, 0, 0, 9, 9, -1);
    }    
    
    public int phase2() {
        return evaluate(14, 43, 10, 11, 0, 0, 8, 1086, 9, 9, -1);
    }    
    
    public int phase3() {
        return evaluate(16, 43, 10, 11, 0, 0, 8, 1086, 9, 9, -1);
    }
    
    private int evaluate(int w1, int w2, int w3, int w4, int w5, int w6,
                         int w7, int w8, int playerPieces, int opponentPieces,
                         int whosWinning) {        
        return w1 * (0) +
               w2 * (getLinesOfThreeForPlayer() - getLinesOfThreeForOpponent()) +
               w3 * (getBlockedPiecesForPlayer() - getBlockedPiecesForOpponent()) +
               w4 * (playerPieces - opponentPieces) +
               w5 * (0) +
               w6 * (getCornersOfThreeForPlayer() - getCornersOfThreeForOpponent()) +
               w7 * (getCornersOfFiveForPlayer() - getCornersOfFiveForOpponent()) +
               w8 * (whosWinning);
    }
    
    private int getLinesOfThreeForPlayer() {
        return 0;
    }
    
    private int getLinesOfThreeForOpponent() {
        return 0;
    }
    
    private int getBlockedPiecesForPlayer() {
        return 0;
    }
    
    private int getBlockedPiecesForOpponent() {
        return 0;
    }
    
    private int getCornersOfThreeForPlayer() {
        return 0;
    }
    
    private int getCornersOfThreeForOpponent() {
        return 0;
    }
    
    private int getCornersOfFiveForPlayer() {
        return 0;
    }
    
    private int getCornersOfFiveForOpponent() {
        return 0;
    }
}
