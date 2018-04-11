package trabalhoia.minimax;

import NineMensMorris.GameInfo;



public class Arvore {
    private No noInicial;
    
    public Arvore(GameInfo info, int movimento) {
        
        int profundidade = -1;
        int[][] spots = info.getSpots();
        int jogador = NewAgent.JOGADOR;
        String pecaAlterar = "";
        int pecasPorColocar = info.getPiecesToPlace() + info.getOpponentPiecesToPlace();
 
        noInicial = new No(info, spots, jogador, movimento, pecaAlterar, profundidade, pecasPorColocar);
    }
    

    public No getNoInicial() {
        return noInicial;
    }

    public void setNoInicial(No noInicial) {
        this.noInicial = noInicial;
    }
    
    
    
    
}
