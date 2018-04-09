package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import NineMensMorris.PlayerAgent;
import java.util.List;



public class NewAgent implements PlayerAgent {
    
    public static int JOGADOR = 1;    
    public static int OPONENTE = 2;
    public static int VAZIO = 0;
    

    @Override
    public String setPiece(GameInfo gi) {
        //função que monta arvore
        Arvore arvore = montaArvore(gi);
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String movePiece(GameInfo gi) {
        //função que monta arvore
        Arvore arvore = montaArvore(gi);
        
        montaArvore(gi);
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String removePiece(GameInfo gi) {
        //função que monta arvore
        Arvore arvore = montaArvore(gi);
        
        montaArvore(gi);
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public Arvore montaArvore(GameInfo info){
        //busca qual o jogador da rodada
        int jogador = 1;
        
        //Calculo para determinar qual melhor profundidade, para que seja eficiente e sem usar memoria em excesso 
        int profundidadeMaxima = calculoMelhorProfundidadeArvore(info);
        
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, profundidadeMaxima);
        
        return arvore;
        
    }
    
    
    //Calculo para medir possibilidade de aumentar profundidade sem afetar desempenho do jogo
    public int calculoMelhorProfundidadeArvore(GameInfo info){
        //calculo baseado em possibilidades de movimentos do jogador e oponente e na quantidade de peças, quanto menos peças
        //e mais movimentos possiveis, maior será largura da arvore, portanto não pode ser tão comprida
        int calculo = (lenghtListMoves(info.getAllowedMoves()) * info.getEmptySpots().size()) + (lenghtListMoves(info.getOpponentAllowedMoves()) * info.getEmptySpots().size());
        
        int profundidadeMaxima = 0;
        
        //Escolha da profundidade baseada no calculo
        if (calculo < 500){
            profundidadeMaxima = 5;
        }
        else if(calculo < 5000){
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
