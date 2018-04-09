package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.List;



public class No {
    
    private int[][] spots;
    private int avaliacao;
    private List<No> filhos;
    private boolean isLineInThisMove;
    
    
    
    /*Construtor de NÓ's da arvore passando como parametro:
    informaçoes do tabuleiro nos determinados spots
    profundidade maxima que a arvore pode abrangir, foi calculado anteriormente
    controle da profundidade em que o nó está sendo criado/inserido na arvore
    controle para saber se esse spot representa o fechamento de um trio(linha)*/
    public No(GameInfo info, int jogador, int profundidadeMaxima, int profundidade, boolean isLineInThisMove) {
        
        //salva o spot no nó
        this.spots = info.getSpots();
        //salva se é um nó com fechamento de trio
        this.isLineInThisMove = isLineInThisMove;
        //Acrescenta a profundidade, para chegar na profundidade correspondente ao nó
        profundidade++;
        
        //Caso não tenha chegado na profundidade maxima, continua expandindo a arvore
        if (profundidade <= profundidadeMaxima){
            //Função em que gerará e adicionará as jogadas possiveis(lista de NÓ's) derivando do estado atual do tabuleiro
            geraSpotsFilhos(info, jogador, profundidadeMaxima, profundidade);
        }       
        else {
            //Caso ja tenha chegado no limite da arvore, pofundidade maxima, deve ser avaliado os NÓ's
            avaliar(info, jogador, isLineInThisMove);
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

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<No> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<No> filhos) {
        this.filhos = filhos;
    }
    
    
    public void geraSpotsFilhos(GameInfo info, int jogador, int profundidadeMaxima, int profundidade){
        
        //Verifica se ultimo nó fechou um trio(linha), se sim o procimo nó deve ser um de remoção de peça
        if(isLineInThisMove){
            
            //Ternario para verificar qual jogador fechou o trio, se foi jogador então pega peças que são possiveis de ser removidas do oponente, senão vice-versa
            List<String> allowedRemoves = (jogador == EvaluationFunction.JOGADOR) ? info.getAllowedRemoves() : info.getOpponentAllowedRemoves();
            
            //Percorre todas opções de remoção de peça, para que seja criado um nó para cada uma
            for(int i=0; i<allowedRemoves.size(); i++){
                
                //Duplica tabuleiro pai
                int[][] novoSpot = info.getSpots();
                
                //Percorre lista de possibilidades e salva linha e coluna do respectivo indice da lista
                int linha = allowedRemoves.get(i).charAt(0);
                int coluna = allowedRemoves.get(i).charAt(2);
                
                //remove peça do tabuleiro no spot(linha,coluna) determinado acima
                novoSpot[linha][coluna] = 0;
                
                //Gera game info para a partir da nova configuração das peças no tabuleiro(spots)
                GameInfo infoNovo = new GameInfo(novoSpot);
                
                //Verifica de quem é a vez, caso jogador
                if (jogador == EvaluationFunction.JOGADOR){
                    //Verifica se a quantidade de peças do oponente é menor que 3, o que resultaria em um estado de vitória
                    if(infoNovo.getOpponentSpots().size() < 3){
                        /*Verifica se profundidade é numero par ou impar,
                        no caso de impar quer dizer que essa é uma vitoria de Max, jogador que iniciou a arvore ganha
                        no caso de par, quer dizer que essa é uma vitoria de Min, jogadore que iniciou a arvore perde*/
                        if(profundidade%2 == 0){
                            this.avaliacao = -1086;
                        }
                        else{
                            this.avaliacao = 1086;
                        }
                    }
                        
                }
                //Senão, se for oponente
                else if (jogador == EvaluationFunction.OPONENTE){
                    //Verifica se a quantidade de peças do jogador é menor que 3, o que resultaria em um estado de vitória
                    if(infoNovo.getPlayerSpots().size() < 3)
                        /*Verifica se profundidade é numero par ou impar,
                        no caso de impar quer dizer que essa é uma vitoria de Max, jogador que iniciou a arvore ganha
                        no caso de par, quer dizer que essa é uma vitoria de Min, jogadore que iniciou a arvore perde*/
                        if(profundidade%2 == 0){
                            this.avaliacao = -1086;
                        }
                        else{
                            this.avaliacao = 1086;
                        }
                }
                
                //Como foi uma remoção de peça, não foi o fechamento de uma linha
                boolean isLineInThisMove = false;                
                
                //Após a finalização da jogada, deve inverter o jogador que tem a vez de jogar
                jogador = (jogador == EvaluationFunction.JOGADOR) ? EvaluationFunction.OPONENTE : EvaluationFunction.JOGADOR;
                
                //Adição do filho gerado na arvore
                No noFilho = new No(infoNovo, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
                filhos.add(noFilho);
                  
            }
        }
        //Caso não tenha sido uma jogada que gerou fechamento de um trio(linha) prosseguirá arvore normalmente, gerando filhos baseado nos movimentos possiveis
        else{
        
            ////Ternario para verificar de qual jogador deve ser pego os movimentos
            List<String> allowedMoves = jogador == EvaluationFunction.JOGADOR ? info.getAllowedMoves() : info.getOpponentAllowedMoves();

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
                    int[][] novoSpot = info.getSpots();

                    //Pega posição(linha,coluna) atual da peça
                    int linha =  parts[0].charAt(0);
                    int coluna = parts[0].charAt(2);

                    //Zera a posição, informando que a peça está fazendo um movimento(saiu do lugar)
                    novoSpot[linha][coluna] = 0;

                    //Pega posição(linha,coluna) para qual a peça irá ir
                    linha =  parts[j].charAt(0);
                    coluna = parts[j].charAt(2); 

                    //Adiciona a peça com numeração do jogador dono da peça
                    novoSpot[linha][coluna] = jogador;

                    //Gera game info para a nova configuração de spots do tabuleiro
                    GameInfo infoNovo = new GameInfo(novoSpot);
                    
                    //Verifica se a peça movimentada, a nova posição da peça gerou um trio(linha)
                    boolean isLineInThisMove = jogador == EvaluationFunction.JOGADOR ? infoNovo.isPlayerLineOfThree(linha+","+coluna): infoNovo.isOpponentLineOfThree(linha+","+coluna);

                    /*Se não fechou linha, finaliza jogada e passa a vez pro oponente jogar,
                    se fechou linha, a jogada ainda não acabou, tem que remover uma peça, portanto não altera o jogador, ele ainda fará o movimento de remover*/
                    if(!isLineInThisMove){
                        jogador = jogador == EvaluationFunction.JOGADOR ? EvaluationFunction.OPONENTE : EvaluationFunction.JOGADOR;
                    }
                    
                    //Adição do filho gerado na arvore
                    No noFilho = new No(infoNovo, jogador, profundidadeMaxima, profundidade, isLineInThisMove);
                    filhos.add(noFilho);

                } 
            }
        }
    }

    public void avaliar(GameInfo info, int jogador, boolean isLineInThisMove){
        //Instancia a classe de avaliação
        EvaluationFunction avaliacao = new EvaluationFunction(info);
        
        //Verifica de quem é a vez de jogar
        //Caso jogador 1, faz as condiçoes baseadas nas informaçoes das peças dele no tabuleiro
        if (jogador == EvaluationFunction.JOGADOR){
            if (info.getPiecesToPlace() > 0){
                this.avaliacao = avaliacao.phase1(isLineInThisMove, jogador);
            }
            else if(info.getPlayerSpots().size() > 3){
                this.avaliacao = avaliacao.phase2(isLineInThisMove, jogador);
            }
            else{
                this.avaliacao = avaliacao.phase3(isLineInThisMove, jogador);
            }
        }
        //Senão, faz as condiçoes baseadas nas informações das peças do oponente
        else{
            if (info.getOpponentPiecesToPlace() > 0){
                this.avaliacao = avaliacao.phase1(isLineInThisMove, jogador);
            }
            else if(info.getOpponentSpots().size() > 3){
                this.avaliacao = avaliacao.phase2(isLineInThisMove, jogador);
            }
            else{
                this.avaliacao = avaliacao.phase3(isLineInThisMove, jogador);
            }
        }
    }
}
