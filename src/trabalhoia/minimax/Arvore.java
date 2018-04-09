package trabalhoia.minimax;

import NineMensMorris.GameInfo;



public class Arvore {
    private No noInicial;
    
    public Arvore(GameInfo info, int profundidadeMaxima, int movimento) {
        int profundidade = -1;
        
        int[][] spots = info.getSpots();
                
        boolean isLineInThisMove = false; 
        if(movimento == NewAgent.SET){
            isLineInThisMove = false;
        }
        if(movimento == NewAgent.MOVE){
            isLineInThisMove = false;
        }
        if(movimento == NewAgent.REMOVE){
            isLineInThisMove = true;
        }
        
        int jogador = NewAgent.JOGADOR;
 
        noInicial = new No(info, spots, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
    }

    public No getNoInicial() {
        return noInicial;
    }

    public void setNoInicial(No noInicial) {
        this.noInicial = noInicial;
    }
    
    
    
    
}
