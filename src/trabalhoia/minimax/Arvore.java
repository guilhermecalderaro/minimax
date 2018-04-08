/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.minimax;

/**
 *
 * @author Gustavo
 */
public class Arvore {
    private No no;

    public Arvore(int[][] spots, int jogador, int profundidadeMaxima, int phase) {
        int profundidade = 0;
        no = new No(spots, jogador, profundidadeMaxima, profundidade, phase);
    }
    
    
}
