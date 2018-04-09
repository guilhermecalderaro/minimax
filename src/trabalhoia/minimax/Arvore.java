package trabalhoia.minimax;

import NineMensMorris.GameInfo;



public class Arvore {
    private No noInicial;

//    public Arvore(GameInfo info, int[][] spots, int profundidadeMaxima) {
//        int profundidade = 0;
//        boolean isLineInThisMove = false;
//        noInicial = new No(info, spots, profundidadeMaxima, profundidade, isLineInThisMove);
//    }
    
    public Arvore(GameInfo info, int profundidadeMaxima, int movimento) {
        int profundidade = 0;
        
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
 
        noInicial = new No(info, spots, profundidadeMaxima, profundidade, isLineInThisMove);
    }

    public No getNoInicial() {
        return noInicial;
    }

    public void setNoInicial(No noInicial) {
        this.noInicial = noInicial;
    }
    
    
    
    
}
