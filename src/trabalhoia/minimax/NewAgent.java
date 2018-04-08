/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import NineMensMorris.PlayerAgent;
import java.util.List;

/**
 *
 * @author heck_
 */
public class NewAgent implements PlayerAgent {
    
    public static int PHASE_1 = 1;
    public static int PHASE_2 = 2;
    public static int PHASE_3 = 3;

    @Override
    public String setPiece(GameInfo gi) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String movePiece(GameInfo gi) {
        
       if(gi.getPlayer() == 1){
           gi.getPlayerPieces() == 3;
       }
        montaArvore(gi, PHASE_2);
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String removePiece(GameInfo gi) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public void montaArvore(GameInfo info, int phase){
        int[][] spots = info.getSpots();
        int jogador = info.getPlayer();
        int profundidadeMaxima = calculoMelhorProfundidadeArvore(info);
        
        Arvore arvore = new Arvore(spots, jogador, profundidadeMaxima, phase);
        
    }
    
    
    
    public int calculoMelhorProfundidadeArvore(GameInfo info){
        //Calculo para medir possibilidade de aumentar profundidade sem afetar desempenho do jogo
        int calculo = (lenghtListMoves(info.getAllowedMoves()) * info.getEmptySpots().size()) + (lenghtListMoves(info.getOpponentAllowedMoves()) * info.getEmptySpots().size());
        int profundidadeMaxima = 0;
        if (calculo <100){
            profundidadeMaxima = 5;
        }
        else if(calculo <1000){
            profundidadeMaxima = 4;
        }
        else{
            profundidadeMaxima = 3;
        }
        
        return profundidadeMaxima;
    }
    
    //Função necessaria, pois no caso de getAllowedMoves a função default do List que é size(), não vai retornar o numero total de Moves,
    //pois pode ter mais de um Move na mesma String da List
    public int lenghtListMoves(List<String> listString){
        int count = 0;
        
        if( listString != null && !listString.isEmpty()){
            for (int i=0; i<listString.size(); i++){
                count += (listString.get(i).length() - 3) / 4;
            }
        }
        
        return count;
        
    }
    
}
