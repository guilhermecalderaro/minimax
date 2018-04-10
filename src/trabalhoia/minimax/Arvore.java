package trabalhoia.minimax;

import NineMensMorris.GameInfo;



public class Arvore {
    private No noInicial;
    
    public Arvore(GameInfo info, int profundidadeMaxima, int movimento) {
        
        int profundidade = 0;
        int[][] spots = info.getSpots();
        int jogador = NewAgent.JOGADOR;
        String pecaAlterada = "";
 
        noInicial = new No(info, spots, jogador, profundidadeMaxima, profundidade, pecaAlterada);
    }
    
    public Arvore(GameInfo info, int movimento) {
        
        int profundidade = 0;
        int[][] spots = info.getSpots();
        int jogador = NewAgent.JOGADOR;
        String pecaAlterada = "";
        if(movimento == NewAgent.REMOVE){
            pecaAlterada = "remover";
        }

 
        noInicial = new No(info, spots, jogador, profundidade, pecaAlterada);
    }

    public No getNoInicial() {
        return noInicial;
    }

    public void setNoInicial(No noInicial) {
        this.noInicial = noInicial;
    }
    
    
    
    
}
