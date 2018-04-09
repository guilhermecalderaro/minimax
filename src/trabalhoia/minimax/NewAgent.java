package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import NineMensMorris.PlayerAgent;
import java.util.List;



public class NewAgent implements PlayerAgent {
    
    public static int JOGADOR = 1;    
    public static int OPONENTE = 2;
    public static int VAZIO = 0;
    public static int SET = 1;
    public static int MOVE = 2;
    public static int REMOVE = 3;
    
    

    @Override
    public String setPiece(GameInfo info) {
        //Calculo para determinar qual melhor profundidade, para que seja eficiente e sem usar memoria em excesso 
        int profundidadeMaxima = calculoMelhorProfundidadeArvore(info);
        
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, profundidadeMaxima, SET);
        
        
        //return minimax(arvore.getNoInicial());
        
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet.");

    }
    
    public int minimax(No no, int alfa, int beta){
        if (no.getFilhos() != null && !no.getFilhos().isEmpty()){
            for(int i=0; i<no.getFilhos().size(); i++){
                //Verificar se avaliação não é nula
                if(no.getAvaliacao() != null){
                    //Se não for nulo, e for impar, é Max
                    if(isImpar(no.getProfundidade())){
                        //se avaliação do filho for maior que do pai, pai recebe avaliacao filho
                        if(no.getAvaliacao() < no.getFilhos().get(i).getAvaliacao()){
                            no.setAvaliacao(no.getFilhos().get(i).getAvaliacao());
                        }
                        
                        if(no.getAvaliacao() > beta){
                            i = no.getFilhos().size();
                        }
                        if (no.getAvaliacao() > alfa){
                            alfa = no.getAvaliacao();
                        }
                        
                    }
                    //Se for par, é Min
                    else{
                        //se avaliação do filho for menor que do pai, pai recebe avaliacao filho
                        if(no.getAvaliacao() > no.getFilhos().get(i).getAvaliacao()){
                            no.setAvaliacao(no.getFilhos().get(i).getAvaliacao());
                        }
                        
                        if(no.getAvaliacao() < alfa){
                            i = no.getFilhos().size();
                        }
                        if (no.getAvaliacao() < beta){
                            beta = no.getAvaliacao();
                        }
                        
                    }
                }
                else{
                    no.setAvaliacao(minimax(no.getFilhos().get(i), alfa, beta));
                    
                    if(isImpar(no.getProfundidade())){
                        if(no.getAvaliacao() > beta){
                            i = no.getFilhos().size();
                        }
                        if (no.getAvaliacao() > alfa){
                            alfa = no.getAvaliacao();
                        }
                    }
                    else{
                        if(no.getAvaliacao() < alfa){
                            i = no.getFilhos().size();
                        }
                        if (no.getAvaliacao() < beta){
                            beta = no.getAvaliacao();
                        }
                    }    
                        
                }
            }
        }
        
        return no.getAvaliacao();
    }

    @Override
    public String movePiece(GameInfo info) {
        //Calculo para determinar qual melhor profundidade, para que seja eficiente e sem usar memoria em excesso 
        int profundidadeMaxima = calculoMelhorProfundidadeArvore(info);
        
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, profundidadeMaxima, MOVE);
        
        
        
        
        
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String removePiece(GameInfo info) {
        //Calculo para determinar qual melhor profundidade, para que seja eficiente e sem usar memoria em excesso 
        int profundidadeMaxima = calculoMelhorProfundidadeArvore(info);
        
        //instancia nova arvore com os parametros necessaríos
        Arvore arvore = new Arvore(info, profundidadeMaxima, REMOVE);
        
        
        
        
        
        
        
        
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public Arvore montaArvore(GameInfo info){
        
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
    
    public static boolean isImpar(int numero){
        return (numero % 2) == 1 ? true : false;
    }
    
    public static boolean isPar(int numero){
        return (numero % 2) == 1 ? true : false;
    }
    
    private void alpaBetaMinimax (No no, int alfa, int beta){
        
    }

    
       
    
}
