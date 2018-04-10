package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import NineMensMorris.PlayerAgent;
import java.util.List;
import java.util.Random;



public class NewAgent implements PlayerAgent {
    
    public static int JOGADOR = 1;    
    public static int OPONENTE = 2;
    public static int VAZIO = 0;
    public static int SET = 1;
    public static int MOVE = 2;
    public static int REMOVE = 3;
    public static int PROFUNIDADE_MAXIMA = 3;
    
    

    @Override
    public String setPiece(GameInfo info) {
        
        if(tabuleiroVazio(info)){
            return primeiraPeca(info);
        }
        else{
            
            //instancia nova arvore com os parametros necessaríos
            Arvore arvore = new Arvore(info, SET);
            
            return executeMinimax(arvore);
        }
        
    }
    
    @Override
    public String movePiece(GameInfo info) {
       
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, MOVE);

        return executeMinimax(arvore);

    }

    @Override
    public String removePiece(GameInfo info) {
        
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, REMOVE);

        return executeMinimax(arvore);
        
    }
    
    
    
    
    
    
    public boolean tabuleiroVazio(GameInfo info){
        if (info.getEmptySpots().size() == 24){
            return true;
        }
        
        return false;
    }

    
    public String primeiraPeca(GameInfo info){
        Random random = new Random();
        
        int selected = random.nextInt(24);

        return info.getEmptySpots().get(selected);
    }
    
    
    
    
    
   
       
       
    public String executeMinimax(Arvore arvore){
        
        int selectedValue = getMinimax( arvore.getNoInicial() );
        
        for (No no : arvore.getNoInicial().getFilhos() ){
            if (no.getAvaliacao() == selectedValue ){
                return no.getPecaAlterada();
            }
        }
        
        return "";
        
    }

    
    private int getMinimax(No no ){
        
        int valorMaximo = -999999999;
        int valorMinimo = 999999999;
        
        if ( no.getFilhos().isEmpty() )
        {
            return no.getAvaliacao();
        }
        
        for ( No filho : no.getFilhos() ){
            
            if (no.getJogador() == JOGADOR){
                valorMaximo = filho.getAvaliacao() > valorMaximo ? getMinimax( filho ) : valorMaximo;
            }
            else{
                valorMinimo = filho.getAvaliacao() < valorMinimo ? getMinimax( filho ) : valorMinimo;
            }
        }
                
        no.setAvaliacao(no.getJogador() == JOGADOR ? valorMaximo : valorMinimo);
        
        return no.getAvaliacao();
                
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
    
    
    public static boolean isImpar(int numero){
        return (numero % 2) == 1 ? true : false;
    }
    
    
    public static boolean isPar(int numero){
        return (numero % 2) == 1 ? true : false;
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
        
        return 3;
    }


}
