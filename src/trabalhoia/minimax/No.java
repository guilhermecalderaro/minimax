package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.List;



public class No {
    
    private int[][] spots;
    private Integer avaliacao;
    private List<No> filhos;
    private boolean isLineInThisMove;
    private int profundidade;
    private int jogador;
    
    
    
    /*Construtor de NÓ's da arvore passando como parametro:
    informaçoes do tabuleiro nos determinados spots
    profundidade maxima que a arvore pode abrangir, foi calculado anteriormente
    controle da profundidade em que o nó está sendo criado/inserido na arvore
    controle para saber se esse spot representa o fechamento de um trio(linha)*/
    public No(GameInfo info, int[][] spots, int jogador, int profundidadeMaxima, int profundidade, boolean isLineInThisMove) {
        
        //salva o spot no nó
        this.spots = spots;
        
        //salva se é um nó com fechamento de trio
        this.isLineInThisMove = isLineInThisMove;
        
        //Acrescenta a profundidade, para chegar na profundidade correspondente e salva no nó
        this.profundidade = profundidade++;
        
        //Caso não tenha chegado na profundidade maxima, continua expandindo a arvore
        if (profundidade <= profundidadeMaxima){
            //Função em que gerará e adicionará as jogadas possiveis(lista de NÓ's) derivando do estado atual do tabuleiro
            geraSpotsFilhos(profundidadeMaxima, info);
        }       
        else {
            //Caso ja tenha chegado no limite da arvore, pofundidade maxima, deve ser avaliado os NÓ's
            avaliar(isLineInThisMove, info);
        }
    }
    
    
    /*
    /       Getters and Setter
    */

    public int[][] getSpots() {
        return spots;
    }

    public void setSpots(int[][] spots) {
        this.spots = spots;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<No> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<No> filhos) {
        this.filhos = filhos;
    }

    public boolean isIsLineInThisMove() {
        return isLineInThisMove;
    }

    public void setIsLineInThisMove(boolean isLineInThisMove) {
        this.isLineInThisMove = isLineInThisMove;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }
    
    
    
    

    
    /*Se profundidade for impar então é o MAX, o MAX sempre é o JOGADOR
      Se profundidade for par então é o MIN, o MIN sempre é o OPONENTE*/
//    private int getJogador(){
//        return (NewAgent.isImpar(this.profundidade)) ? NewAgent.JOGADOR : NewAgent.OPONENTE;
//    }
    
    private List<String> opcoesRemocao(GameInfo info){

        /*Ternario para verificar qual jogador fechou o trio,
        se foi jogador então retorna peças que são possiveis de ser removidas do oponente,
        se foi oponente então retorna as peças que são possiveis de ser removidas do jogador*/
        return (getJogador() == NewAgent.JOGADOR) ? info.getAllowedRemoves(this.spots) : info.getOpponentAllowedRemoves(this.spots);
    }
    
    private List<String> opcoesMovimento(GameInfo info){
            
        /*Ternario para verificar qual jogador fechou o trio,
        se foi jogador então retorna peças que são possiveis de ser removidas do oponente,
        se foi oponente então retorna as peças que são possiveis de ser removidas do jogador*/
        return (getJogador() == NewAgent.JOGADOR) ? info.getAllowedMoves(this.spots): info.getOpponentAllowedMoves(this.spots);
    }
    
    private List<String> opcoesAdicionarPeca(GameInfo info){
            
        if(((info.getPiecesToPlace() - getProfundidade()) > 0) || ((info.getOpponentPiecesToPlace() - getProfundidade()) > 0)){
            return info.getEmptySpots(this.spots);
        }
        
        return null;
        
    }
    
    
    public void geraSpotsFilhos(int profundidadeMaxima, GameInfo info){
        
        //Verifica se ultimo nó fechou um trio(linha), se sim o procimo nó deve ser um de remoção de peça
        if(isLineInThisMove){
            
            //Ternario para verificar qual jogador fechou o trio, se foi jogador então pega peças que são possiveis de ser removidas do oponente, senão vice-versa
            List<String> allowedRemoves = opcoesRemocao(info);
            
            //Percorre todas opções de remoção de peça, para que seja criado um nó para cada uma
            for(int i=0; i<allowedRemoves.size(); i++){
                
                //Duplica tabuleiro pai
                int[][] novoSpot = this.spots;
                
                //Percorre lista de possibilidades e salva linha e coluna do respectivo indice da lista
                int linha = allowedRemoves.get(i).charAt(0);
                int coluna = allowedRemoves.get(i).charAt(2);
                
                //remove peça do tabuleiro no spot(linha,coluna) determinado acima
                novoSpot[linha][coluna] = NewAgent.VAZIO;
                
                //Verifica de quem é a vez, caso jogador
                if (getJogador() == NewAgent.JOGADOR){
                    //Verifica se a quantidade de peças do oponente é menor que 3, o que resultaria em um estado de vitória
                    if(info.getOpponentSpots(novoSpot).size() < 3){
                        this.avaliacao = 5000;
                    }
                    //Senão continua a expandir a arvore normalmente
                    else{
                        //Como acabou de fazer remoção de uma peça, essa jogada n foi de movimento, portanto não pode ter fecado um trio(linha)
                        boolean isLineInThisMove = false;  
                        

                        //Adição do filho gerado na arvore
                        No noFilho = new No(info, novoSpot, NewAgent.JOGADOR, profundidadeMaxima, this.profundidade, isLineInThisMove);
                        filhos.add(noFilho);
                    }
                        
                }
                //Senão, se for oponente
                else{
                    //Verifica se a quantidade de peças do jogador é menor que 3, o que resultaria em um estado de vitória
                    if(info.getPlayerSpots(novoSpot).size() < 3){
                        this.avaliacao = -5000;
                    }
                    //Senão continua a expandir a arvore normalmente
                    else{
                        //Como acabou de fazer remoção de uma peça, essa jogada n foi de movimento, portanto não pode ter fecado um trio(linha)
                        boolean isLineInThisMove = false;                

                        //Adição do filho gerado na arvore
                        No noFilho = new No(info, novoSpot, NewAgent.OPONENTE, profundidadeMaxima, this.profundidade, isLineInThisMove);
                        filhos.add(noFilho);
                    }
                }      
            }
        }
        else if(opcoesAdicionarPeca(info) != null && !opcoesAdicionarPeca(info).isEmpty()){
            ////Ternario para verificar de qual jogador deve ser pego os movimentos
            List<String> allowedSets = opcoesAdicionarPeca(info);

            //Percorre todas opções de remoção de peça, para que seja criado um nó para cada uma
            for(int i=0; i<allowedSets.size(); i++){
                
                //Duplica tabuleiro pai
                int[][] novoSpot = this.spots;
                
                //Percorre lista de possibilidades e salva linha e coluna do respectivo indice da lista
                int linha = allowedSets.get(i).charAt(0);
                int coluna = allowedSets.get(i).charAt(2);
                
                //remove peça do tabuleiro no spot(linha,coluna) determinado acima
                novoSpot[linha][coluna] = getJogador();
                
                //Inverte jogador, se for Max agora, será Min na proxima, e vice-versa
                int jogador = (this.jogador == NewAgent.JOGADOR) ? NewAgent.OPONENTE : NewAgent.JOGADOR;

                //Verifica se a peça movimentada, a nova posição da peça gerou um trio(linha)
                boolean isLineInThisMove = (getJogador() == NewAgent.JOGADOR) ? info.isPlayerLineOfThree((linha + "," + coluna), novoSpot): info.isOpponentLineOfThree((linha + "," + coluna), novoSpot);

                //Adição do filho gerado na arvore
                No noFilho = new No(info, novoSpot, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
                filhos.add(noFilho);
                
                      
            }
        }
        //Caso não tenha sido uma jogada que gerou fechamento de um trio(linha) prosseguirá arvore normalmente, gerando filhos baseado nos movimentos possiveis
        else{
        
            ////Ternario para verificar de qual jogador deve ser pego os movimentos
            List<String> allowedMoves = opcoesMovimento(info);

            //Percorre lista de movimentos possiveis
            for(int i=0; i<allowedMoves.size(); i++){
                //Vetor para armazenar parts da string, caso uma mesma peça possa originar mais de uma jogada
                String[] parts;
                
                /*Part 0 fica com as cordenadas que a peça possui atualmente, enquanto as outras partes(deve haver pelo menos a parte 0 e 1),
                terão movimentações posseis a partir da cordenada atual*/
                parts = allowedMoves.get(i).split(";");
                
                /*Percorre então 'parts' para adiciar nó para cada jogada derivada da posição atual da peça escolhida na lista de movimentos possivei(allowed moves).
                Inicia em 1, pois a posição 0 não é um movimento possivel, e sim a posição atual da peça*/
                for(int j=1; j<parts.length; j++){
                    
                    //Duplica spots pai
                    int[][] novoSpot = this.spots;

                    //Pega posição(linha,coluna) atual da peça
                    int linha =  parts[0].charAt(0);
                    int coluna = parts[0].charAt(2);

                    //Zera a posição, informando que a peça está fazendo um movimento(saiu do lugar)
                    novoSpot[linha][coluna] = NewAgent.VAZIO;

                    //Pega posição(linha,coluna) para qual a peça irá ir
                    linha =  parts[j].charAt(0);
                    coluna = parts[j].charAt(2); 

                    //Adiciona a peça com numeração do jogador dono da peça
                    novoSpot[linha][coluna] = getJogador();
                    
                    //Inverte jogador, se for Max agora, será Min na proxima, e vice-versa
                    int jogador = (this.jogador == NewAgent.JOGADOR) ? NewAgent.OPONENTE : NewAgent.JOGADOR;
                    
                    //Verifica se a peça movimentada, a nova posição da peça gerou um trio(linha)
                    boolean isLineInThisMove = (getJogador() == NewAgent.JOGADOR) ? info.isPlayerLineOfThree((linha + "," + coluna), novoSpot): info.isOpponentLineOfThree((linha + "," + coluna), novoSpot);
                    
                    //Adição do filho gerado na arvore
                    No noFilho = new No(info, novoSpot, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
                    filhos.add(noFilho);

                } 
            }
        }
    }

    public void avaliar(boolean isLineInThisMove, GameInfo info){
        //Instancia um Game Info
        //GameInfo info = new GameInfo(this.spots);
        
        //Instancia a classe de avaliação
        EvaluationFunction avaliacao = new EvaluationFunction(this.spots);
        
        //Verifica de quem é a vez de jogar
        //Caso jogador 1, faz as condiçoes baseadas nas informaçoes das peças dele no tabuleiro
        if (getJogador() == NewAgent.JOGADOR){
            if ((info.getPiecesToPlace() - profundidade) > 0){
                this.avaliacao = avaliacao.phase1(isLineInThisMove, NewAgent.JOGADOR, info);
            }
            else if(info.getPlayerSpots(this.spots).size() > 3){
                this.avaliacao = avaliacao.phase2(isLineInThisMove, NewAgent.JOGADOR, info);
            }
            else{
                this.avaliacao = avaliacao.phase3(isLineInThisMove, NewAgent.JOGADOR, info);
            }
        }
        //Senão, faz as condiçoes baseadas nas informações das peças do oponente
        else{
            if ((info.getOpponentPiecesToPlace() - profundidade) > 0){
                this.avaliacao = avaliacao.phase1(isLineInThisMove, NewAgent.OPONENTE, info);
            }
            else if(info.getOpponentSpots(this.spots).size() > 3){
                this.avaliacao = avaliacao.phase2(isLineInThisMove, NewAgent.OPONENTE, info);
            }
            else{
                this.avaliacao = avaliacao.phase3(isLineInThisMove, NewAgent.OPONENTE, info);
            }
        }
    }

    public int getJogador() {
        return jogador;
    }

    public void setJogador(int jogador) {
        this.jogador = jogador;
    }
}
