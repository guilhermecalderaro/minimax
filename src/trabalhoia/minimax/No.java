/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class No {
    
    private int[][] spots;
    private int avaliacao;
    private List<No> filhos;
    private boolean isLineInThisMove;
    
    
    

    public No(int[][] spots, int jogador, int profundidadeMaxima, int profundidade, boolean isLineInThisMove) {
        
        this.spots = spots;
        this.isLineInThisMove = isLineInThisMove;
        profundidade++;

        GameInfo info = new GameInfo(spots);
        
        if (profundidade <= profundidadeMaxima && !isLineInThisMove){
            geraSpotsFilhos(info, jogador, profundidadeMaxima, profundidade);
        }       
        else {
            avaliar(info, jogador);
        }
    }

    public int[][] getSpots() {
        return spots;
    }

    public void setSpots(int[][] spots) {
        this.spots = spots;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<No> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<No> filhos) {
        this.filhos = filhos;
    }
    
    
    public List<int[][]> geraSpotsFilhos(GameInfo info, int jogador, int profundidadeMaxima, int profundidade){
        List<int[][]> novosSpots = new ArrayList<>();
        
        List<String> allowedMoves = info.getAllowedMoves();
        
        for(int i=0; i<allowedMoves.size(); i++){
            String[] parts;
            parts = allowedMoves.get(i).split(";");
            
            for(int j=1; j<parts.length; j++){
                int[][] novoSpot = info.getSpots();
                
                int linha =  parts[0].charAt(0);
                int coluna = parts[0].charAt(2);
                
                novoSpot[linha][coluna] = 0;
                
                linha =  parts[j].charAt(0);
                coluna = parts[j].charAt(2); 
                
                novoSpot[linha][coluna] = jogador;
                
                GameInfo infoNovo = new GameInfo(novoSpot);
                boolean isLineInThisMove = jogador == 1 ? infoNovo.isPlayerLineOfThree(linha+","+coluna): infoNovo.isOpponentLineOfThree(linha+","+coluna);
                
                jogador = jogador == 0 ? 1 : 0;
                
                //Quando for um jogo vitorioso ou profundidade maxima, deve parar de expandir o ramo e avaliar
                No noFilho = new No(novoSpot, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
                filhos.add(noFilho);
                
                
                novosSpots.add(novoSpot);
  
            } 
        }
        
        return novosSpots;
    }

    public void avaliar(GameInfo info, int jogador, boolean isLineInThisMove){
        EvaluationFunction avaliacao = new EvaluationFunction(info);
            
        if (jogador == 1){
            if (info.getPiecesToPlace() > 0){
                this.avaliacao = avaliacao.phase1(isLineInThisMove, jogador);
            }
            else if(info.getPlayerSpots().size() > 3){
                this.avaliacao = avaliacao.phase2(isLineInThisMove, jogador);
            }
            else{
                this.avaliacao = avaliacao.phase3(isLineInThisMove, jogador);
            }
        }
    }
}
