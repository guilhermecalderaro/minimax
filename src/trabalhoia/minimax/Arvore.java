package trabalhoia.minimax;

import NineMensMorris.GameInfo;



public class Arvore {
    private No noInicial;

    public Arvore(GameInfo info, int jogador, int profundidadeMaxima) {
        int profundidade = 0;
        boolean isLineInThisMove = false;
        noInicial = new No(info, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
    }
    
    
}
