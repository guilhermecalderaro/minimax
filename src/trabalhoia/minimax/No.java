package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class No {
    
    private int[][] spots;
    private Integer avaliacao;
    private List<No> filhos;
    private int profundidade;
    private int jogador;
    private String pecaAlterada;
    private int pecasPorColocar;
    private int proximoMovimento;
    
    
    
    public No(GameInfo info, int[][] spotPai, int jogadorAnterior, int tipoMovimentacao, String pecaAlterar, int profundidade, int pecasPorColocar){
        
        //Verifica se ultimo nó fechou um trio(linha), se sim o procimo nó deve ser um de remoção de peça
        if(tipoMovimentacao == NewAgent.REMOVE){
            
            this.profundidade = profundidade + 1;
            
            this.jogador = jogadorAnterior;
            //this.jogador = jogadorAnterior == NewAgent.JOGADOR ? NewAgent.OPONENTE : NewAgent.JOGADOR;
                
            this.spots = cloneStado(spotPai);

            int linha = Integer.parseInt(""+pecaAlterar.charAt(0));
            int coluna = Integer.parseInt(""+pecaAlterar.charAt(2));

            //remove peça do tabuleiro no spot(linha,coluna) determinado acima
            this.spots[linha][coluna] = NewAgent.VAZIO;
            
            this.pecasPorColocar = pecasPorColocar;

            int novoMovimento = (this.pecasPorColocar == 0) ? NewAgent.MOVE : NewAgent.SET;
            
            this.filhos = new ArrayList();


            if((info.getOpponentSpots(this.spots).size() < 3) || (info.getOpponentSpots(this.spots).size() < 3)){

                EvaluationFunction avaliador = new EvaluationFunction(info, this.spots);

                setAvaliacao(avaliador.vitoria(this.jogador));

            }

            //Senão continua a expandir a arvore normalmente
            else if( this.profundidade < NewAgent.PROFUNIDADE_MAXIMA){
                
                List<String> novasJogadas = novoMovimento == NewAgent.MOVE ? opcoesMovimentarPeca(info) : opcoesAdicionarPeca(info);
                
                for(String novaJogada : novasJogadas){
                    No noFilho = new No(info, this.spots, this.jogador, novoMovimento, novaJogada, this.profundidade, this.pecasPorColocar);
                    filhos.add(noFilho);
                }

            }
            else{
                    avaliar(info);
            }
            
        }
        else if(tipoMovimentacao == NewAgent.SET){
            this.profundidade = profundidade + 1;
            
            if(this.profundidade == 0){
                
                this.jogador = jogadorAnterior == NewAgent.JOGADOR ? NewAgent.OPONENTE : NewAgent.JOGADOR;
                
                this.spots = cloneStado(spotPai);
                
                this.pecaAlterada = pecaAlterar;

                this.pecasPorColocar = pecasPorColocar;
                
                int novoMovimento = NewAgent.SET;
                
                List<String> novasJogadas = opcoesAdicionarPeca(info);
                 
                this.filhos = new ArrayList();

                for(String novaJogada : novasJogadas){
                    No noFilho = new No(info, this.spots, this.jogador, novoMovimento, novaJogada, this.profundidade, this.pecasPorColocar);
                    filhos.add(noFilho);
                }
            }
            else{
                
                this.jogador = jogadorAnterior == NewAgent.JOGADOR ? NewAgent.OPONENTE : NewAgent.JOGADOR;

                this.spots = cloneStado(spotPai);

                int linha = Integer.parseInt(""+pecaAlterar.charAt(0));
                int coluna = Integer.parseInt(""+pecaAlterar.charAt(2));

                //remove peça do tabuleiro no spot(linha,coluna) determinado acima
                this.spots[linha][coluna] = this.jogador;

                this.pecaAlterada = pecaAlterar;

                this.pecasPorColocar = pecasPorColocar - 1;
                
                this.filhos = new ArrayList();
                
                if( this.profundidade < NewAgent.PROFUNIDADE_MAXIMA){

                    boolean fechouLinha = this.jogador == NewAgent.JOGADOR ? info.isPlayerLineOfThree(this.pecaAlterada, this.spots) : info.isOpponentLineOfThree(this.pecaAlterada, this.spots);
                    int novoMovimento = fechouLinha ? NewAgent.REMOVE :(this.pecasPorColocar == 0 ? NewAgent.MOVE : NewAgent.SET);

                    List<String> novasJogadas = novoMovimento == NewAgent.MOVE ? opcoesMovimentarPeca(info) : (novoMovimento == NewAgent.REMOVE ? opcoesRemoverPeca(info) : opcoesAdicionarPeca(info));
                    
                    for(String novaJogada : novasJogadas){
                        No noFilho = new No(info, this.spots, this.jogador, novoMovimento, novaJogada, this.profundidade, this.pecasPorColocar);
                        filhos.add(noFilho);
                    }
                }
                else{
                        avaliar(info);
                }
            }
        }
        //Caso não tenha sido uma jogada que gerou fechamento de um trio(linha) prosseguirá arvore normalmente, gerando filhos baseado nos movimentos possiveis
        else if(tipoMovimentacao == NewAgent.MOVE){
        
            this.profundidade = profundidade + 1;

            this.jogador = jogadorAnterior == NewAgent.JOGADOR ? NewAgent.OPONENTE : NewAgent.JOGADOR;

            this.spots = cloneStado(spotPai);

            this.pecasPorColocar = pecasPorColocar - 1;

            //Pega posição(linha,coluna) atual da peça
            int linha =  Integer.parseInt(""+pecaAlterar.charAt(0));
            int coluna = Integer.parseInt(""+pecaAlterar.charAt(2));

            //Zera a posição, informando que a peça está fazendo um proximoMovimento(saiu do lugar)
            this.spots[linha][coluna] = NewAgent.VAZIO;

            //Pega posição(linha,coluna) para qual a peça irá ir
            linha =  Integer.parseInt(""+pecaAlterar.charAt(4));
            coluna = Integer.parseInt(""+pecaAlterar.charAt(6)); 

            //Adiciona a peça com numeração do jogador dono da peça
            this.spots[linha][coluna] = this.jogador;
            
            this.filhos = new ArrayList();

            this.pecaAlterada = pecaAlterar;    

            if( this.profundidade < NewAgent.PROFUNIDADE_MAXIMA){
                boolean fechouLinha = this.jogador == NewAgent.JOGADOR ? info.isPlayerLineOfThree(this.pecaAlterada, this.spots) : info.isOpponentLineOfThree(this.pecaAlterada, this.spots);
                int novoMovimento = fechouLinha ? NewAgent.REMOVE : NewAgent.MOVE;

                List<String> novasJogadas = novoMovimento == NewAgent.MOVE ? opcoesMovimentarPeca(info) :  opcoesRemoverPeca(info);

                for(String novaJogada : novasJogadas){
                    No noFilho = new No(info, this.spots, this.jogador, novoMovimento, novaJogada, this.profundidade, this.pecasPorColocar);
                    filhos.add(noFilho);
                }
            }
            else{
                avaliar(info);
            }

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

    public String getPecaAlterada() {
        return pecaAlterada;
    }

    public void setPecaAlterada(String pecaAlterada) {
        this.pecaAlterada = pecaAlterada;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }
    
    public int getJogador() {
        return jogador;
    }

    public void setJogador(int jogador) {
        this.jogador = jogador;
    }
    
    public int getPecasPorColocar() {
        return pecasPorColocar;
    }

    public void setPecasPorColocar(int pecasPorColocar) {
        this.pecasPorColocar = pecasPorColocar;
    }

    public int getProximoMovimento() {
        return proximoMovimento;
    }

    public void setProximoMovimento(int movimento) {
        this.proximoMovimento = movimento;
    }
    
    
    

    
    
    
    
    
    
    
    private List<String> opcoesRemoverPeca(GameInfo info){

        /*Ternario para verificar qual jogador fechou o trio,
        se foi jogador então retorna peças que são possiveis de ser removidas do oponente,
        se foi oponente então retorna as peças que são possiveis de ser removidas do jogador*/
        return (getJogador() == NewAgent.JOGADOR) ? info.getOpponentAllowedRemoves(getSpots()) : info.getAllowedRemoves(getSpots());
    }
    
    
    
    private List<String> opcoesMovimentarPeca(GameInfo info){
        List<String> movimentos = (getJogador() == NewAgent.JOGADOR) ? info.getOpponentAllowedMoves(getSpots()) : info.getAllowedMoves(getSpots());
        List<String> movimentosRemastered = new ArrayList();
        
        for(String movimento : movimentos){
            
            String parts[];
            parts = movimento.split(";");
            
            for(int i=1; i<parts.length; i++){
                movimentosRemastered.add(parts[0] + ";" + parts[i]);
            }
            
        }
        
        
        return movimentosRemastered;
    }
    
    
    
    private List<String> opcoesAdicionarPeca(GameInfo info){
            
        return info.getEmptySpots(getSpots());
        
    }
    

    
    
    
    
    

    
    
    
    
    
   
    
    
    public void avaliar(GameInfo info){
        
        //Instancia a classe de avaliação
        EvaluationFunction avaliacao = new EvaluationFunction(info, getSpots());
        
        //Verifica de quem é a vez de jogar
        //Caso jogador 1, faz as condiçoes baseadas nas informaçoes das peças dele no tabuleiro
        if (getJogador() == NewAgent.JOGADOR){
            
            //1 para jogador fechou linha e 0 se não fechou
            int jogadorQueFechouLinha = info.isPlayerLineOfThree(getPecaAlterada(), getSpots()) ? 1 : 0;
            
            if (getPecasPorColocar() > 0){
                this.avaliacao = avaliacao.phase1(jogadorQueFechouLinha);
            }
            else if(info.getPlayerSpots(this.spots).size() > 3){
                this.avaliacao = avaliacao.phase2(jogadorQueFechouLinha);
            }
            else{
                this.avaliacao = avaliacao.phase3(jogadorQueFechouLinha);
            }
        }
        //Senão, faz as condiçoes baseadas nas informações das peças do oponente
        else{
            
            //-1 para oponente fechou linha e 0 se não fechou
            int jogadorQueFechouLinha = info.isPlayerLineOfThree(getPecaAlterada(), getSpots()) ? -1 : 0;
            
            if ((info.getOpponentPiecesToPlace() - getProfundidade()) > 0){
                this.avaliacao = avaliacao.phase1(jogadorQueFechouLinha);
            }
            else if(info.getOpponentSpots(this.spots).size() > 3){
                this.avaliacao = avaliacao.phase2(jogadorQueFechouLinha);
            }
            else{
                this.avaliacao = avaliacao.phase3(jogadorQueFechouLinha);
            }
        }
    }
    
    private int[][] cloneStado(int[][] state){
        int[][] clone = new int[state.length][];
        
        for(int i=0; i< state.length; i++){
            clone[i] = Arrays.copyOf(state[i], state[i].length);
        }
        
        return clone;
    }


    



}
