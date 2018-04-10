package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.List;



public final class EvaluationFunction {
    
    private int spots[][];
    
    private static int COLUNA_ZERO = 0;
    private static int COLUNA_SETE = 7;
    private static int LINHA_ZERO = 0;
    private static int MAIS_UMA_CASA = 1;
    private static int MENOS_UMA_CASA = -1;
    


    public EvaluationFunction(int[][] spots) {
        this.spots = spots;
    }

    
    public int phase1(boolean isLineInThisMove, int jogador, GameInfo info) {
        return evaluate(18, 26, 1, 9, 10, 7, 0, isLineInThisMove, jogador, info);
    }    
    
    public int phase2(boolean isLineInThisMove, int jogador, GameInfo info) {
        return evaluate(14, 43, 10, 11, 0, 0, 8, isLineInThisMove, jogador, info);
    }    
    
    public int phase3(boolean isLineInThisMove, int jogador, GameInfo info) {
        return evaluate(16, 43, 10, 11, 0, 0, 8, isLineInThisMove, jogador, info);
    }
    
    private int evaluate(int w1, int w2, int w3, int w4, int w5, int w6, int w7, boolean isLineInThisMove, int jogador, GameInfo info ) {        
        
        return w1 * (isLineInThisMove ? (jogador == NewAgent.JOGADOR ? 1 : -1) : 0) +
               w2 * (getTotalLinhasFechadas(NewAgent.JOGADOR, info) - getTotalLinhasFechadas(NewAgent.OPONENTE, info)) +
               w3 * (getTotalPecasAdversariaBloqueadas(NewAgent.OPONENTE, info) - getTotalPecasAdversariaBloqueadas(NewAgent.JOGADOR, info)) +
               w4 * (info.getPlayerSpots(this.spots).size() - info.getOpponentSpots(this.spots).size()) +
               w5 * (0) +
               w6 * (getQuantidadeLTresPecas(NewAgent.JOGADOR, info) - getQuantidadeLTresPecas(NewAgent.OPONENTE, info)) +
               w7 * (getQuantidadeLCincoPecas(NewAgent.JOGADOR, info) - getQuantidadeLCincoPecas(NewAgent.OPONENTE, info));
    }
    
    private int proximaCasa(int numero){
        if(numero == COLUNA_SETE){
            numero = COLUNA_ZERO;
        }
        else{
            numero += MAIS_UMA_CASA;
        }
        
        return numero;
    }
    
    private int casaAnterior(int numero){
        if(numero == COLUNA_ZERO){
            numero = COLUNA_SETE;
        }
        else{
            numero += MENOS_UMA_CASA;
        }
        
        return numero;
    }
    

    /*Retorna total de linhas fechadas pelo jogador*/
    public int getTotalLinhasFechadas(int jogador, GameInfo info){

        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Jogador Atual, Jogador ou Oponente;
        List<String> playerSpots =  jogador == NewAgent.JOGADOR ? info.getPlayerSpots(this.spots) : info.getOpponentSpots(this.spots);
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas de três estão formadas no tabuleiro a favor do Jogador*/
        int count = 0;
                
        //Percorre todas as peças do Jogador Atual, pará verificar se há 3 em sequencia(linha formada)        
        for (int i=0; i<playerSpots.size(); i++){
            
            /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            linha está na posição 0 da String e a coluna na posição 2, pois elas estão separadas por uma Virgula, que está na posição 1*/
            int linha = Integer.parseInt(""+playerSpots.get(i).charAt(0));
            int coluna = Integer.parseInt(""+playerSpots.get(i).charAt(2));
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos, sendo assim não podem
            formar linha no sentido coluna(do centro do tabuleiro para extremidade).*/
            if(NewAgent.isImpar(coluna)){

                /*Verifica se a prixima casa para fechar uma linha contem uma peça do Jogador Atual*/
                if(playerSpots.contains(linha + "," + (proximaCasa(coluna)))){
                    /*Verifica se 2 casas depois para fechar uma linha contem uma peça do Jogador Atual*/
                    if(playerSpots.contains(linha + "," + (proximaCasa(proximaCasa(coluna))))){
                        /*Caso todas verificações forem verdadeiras até aqui, é porque o Jogador tem uma linha
                        formada*/
                        count++;
                    }
                }
            }
            /* Verificação identica para os numero pares(peças que estão no canto) porém com o acrécimo de uma
            verificação do sentido coluna(do centro do tabuleiro para extremidade), para essa verificação
            adicional só é necessário fazer as verificações se tiver uma peça na linha 0, pois um trio fechado
            em sentido coluna, abrangira as 3 linhas do tabuleiro, logo, se não tiver uma peça na linha 0 é
            porque não tem um trio fechado.
            */
            else if(NewAgent.isPar(coluna) && linha == LINHA_ZERO){
                if(playerSpots.contains((proximaCasa(linha)) + "," + coluna)){
                    if(playerSpots.contains((proximaCasa(proximaCasa(linha))) + "," + coluna)){
                        count++;
                    }
                }
            }
        }
        
        //Retorno do numero de trios fechados encontrado no tabuleiro
        return count;
    }
    
    
    /*Retorna total de peças bloqueadas do jogador*/
    public int getTotalPecasAdversariaBloqueadas(int jogador, GameInfo info){

        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Adversário;
        List<String> playerSpots = (jogador == NewAgent.JOGADOR) ? info.getOpponentSpots(this.spots) : info.getPlayerSpots(this.spots);
        
        //Busca todos campos do tabuleiro que estão livres(sem nenhuma peça tanto do Jogador quanto Oponente)
        List<String> emptySpots = info.getEmptySpots(this.spots);
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas de três estão formadas no tabuleiro a favor do Jogador*/
        int count = 0;
                
        //Percorre todas as peças do Adversario       
        for (int i=0; i<playerSpots.size(); i++){
            
             /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            linha está na posição 0 da String e a coluna na posição 2, pois elas estão separadas por uma Virgula, que está na posição 1*/
            int linha = Integer.parseInt(""+playerSpots.get(i).charAt(0));
            int coluna = Integer.parseInt(""+playerSpots.get(i).charAt(2));
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos, sendo assim não podem se
            movimentar no sentido coluna(do centro do tabuleiro para extremidade)*/
            if(NewAgent.isPar(coluna)){
                if(!emptySpots.contains(linha + "," + proximaCasa(coluna))){
                    if(!emptySpots.contains(linha + "," + casaAnterior(coluna))){
                        /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                        peças bloqueadas*/
                        count++;
                    }
                }
            }
            /*Senão, se for impar, necessita de verificações adicionais para verificar disponibilidade de se
            movimentar, no sentido coluna(do centro do tabuleiro para extremidade)*/
            else if(NewAgent.isImpar(coluna)){
                
                /*Verificação do spot com a coluna seguinte(coluna+1)para ver se está livre, se não há peças bloqueando,
                    verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                if(!emptySpots.contains(linha + "," + proximaCasa(coluna))){
                   /*Verificação do spot com a coluna anterior(coluna-1)para ver se está livre, se não há peças bloqueando,
                    verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                    if(!emptySpots.contains(linha + "," + casaAnterior(coluna))){
                        /*Peças de numero impar estão posicionadas no meio, possibilitando movimentação do tipo coluna
                        (do centro do tabuleiro para a extremidade), logo é necessario verificar disponibilidade de 
                        movimentação em tal sentido*/

                        /*No caso de estar na extremidade exterior(linha == 0) do tabuleiro só é necessaria uma verificação,
                        pois só há uma movimentação possivel no sentido coluna*/
                        switch(linha){
                        case 0:
                            if(!emptySpots.contains(proximaCasa(linha) + "," + coluna)){
                                /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                peças bloqueadas*/
                                count++;
                                break;
                            }
                        case 1:
                            if((!emptySpots.contains(proximaCasa(linha) + "," + coluna)) && (!emptySpots.contains(casaAnterior(linha) + "," + coluna))){
                                /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                peças bloqueadas*/
                                count++;
                                break;
                            }
                        case 2:
                            if(!emptySpots.contains(casaAnterior(linha) + ","  + coluna)){
                                /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                peças bloqueadas*/
                                count++;
                                break;
                            }
                        }
                    }
                }
            }
        }
            
        return count;
    }
    
    
    //Quantidade de vezes que o Jogador atual possui de L de três peças
    private int getQuantidadeLTresPecas(int jogador, GameInfo info) {
        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Adversário;
        List<String> playerSpots = (jogador == NewAgent.JOGADOR) ? info.getOpponentSpots(this.spots) : info.getPlayerSpots(this.spots);
        
        //Busca todos campos do tabuleiro que estão livres(sem nenhuma peça tanto do Jogador quanto Oponente)
        List<String> emptySpots = info.getEmptySpots(this.spots);
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas em L de três peças estão formadas no tabuleiro a favor do Jogador Atual*/
        int count = 0;
                
        //Percorre todas as peças do Adversario       
        for (int i=0; i<playerSpots.size(); i++){
            
             /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            linha está na posição 0 da String e a coluna na posição 2, pois elas estão separadas por uma Virgula, que está na posição 1*/
            int linha = Integer.parseInt(""+playerSpots.get(i).charAt(0));
            int coluna = Integer.parseInt(""+playerSpots.get(i).charAt(2));
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos, sendo assim não podem se
            movimentar no sentido coluna(do centro do tabuleiro para extremidade)*/
            if(NewAgent.isPar(coluna)){
                if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(linha + "," + casaAnterior(coluna)))){
                    if((playerSpots.contains(linha + "," + proximaCasa(proximaCasa(coluna)))) && (playerSpots.contains(linha + "," + casaAnterior(casaAnterior(coluna))))){
                        count ++;
                    }
                }
            }
            else if(NewAgent.isImpar(coluna)){
                switch(linha){
                case 0:
                    if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(proximaCasa(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(proximaCasa(proximaCasa(linha)) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(proximaCasa(proximaCasa(linha)) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(proximaCasa(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(proximaCasa(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(proximaCasa(proximaCasa(linha)) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(proximaCasa(proximaCasa(linha)) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(proximaCasa(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    break;
                case 1:
                    if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(casaAnterior(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(proximaCasa(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(proximaCasa(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(casaAnterior(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(casaAnterior(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(proximaCasa(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(proximaCasa(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(casaAnterior(linha) + "," + coluna))){
                            count++;
                        }
                    }
                case 2:
                    if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(casaAnterior(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(casaAnterior(casaAnterior(linha)) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(casaAnterior(casaAnterior(linha)) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + casaAnterior(coluna))) && (emptySpots.contains(casaAnterior(linha) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(casaAnterior(linha) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(casaAnterior(casaAnterior(linha)) + "," + coluna))){
                            count++;
                        }
                    }
                    else if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(casaAnterior(casaAnterior(linha)) + "," + coluna))){
                        if((emptySpots.contains(linha + "," + proximaCasa(coluna))) && (emptySpots.contains(casaAnterior(linha) + "," + coluna))){
                            count++;
                        }
                    }
                }
            }
        }
        
        return count;
                
    }
    
    
    //Quantidade de vezes que o Jogador atual possui de L de cinco peças
    private int getQuantidadeLCincoPecas(int jogador, GameInfo info) {
        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Adversário;
        List<String> playerSpots = (jogador == NewAgent.JOGADOR) ? info.getOpponentSpots(this.spots) : info.getPlayerSpots(this.spots);
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas em L de cindo peças estão formadas no tabuleiro a favor do Jogador Atual*/
        int count = 0;
                
        //Percorre todas as peças do Adversario       
        for (int i=0; i<playerSpots.size(); i++){
            
             /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            linha está na posição 0 da String e a coluna na posição 2, pois elas estão separadas por uma Virgula, que está na posição 1*/
            int linha = Integer.parseInt(""+playerSpots.get(i).charAt(0));
            int coluna = Integer.parseInt(""+playerSpots.get(i).charAt(2));
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos*/
            if(NewAgent.isPar(coluna)){
                if((playerSpots.contains(linha + "," + proximaCasa(coluna))) && (playerSpots.contains(linha + "," + proximaCasa(proximaCasa(coluna))))){
                    if((playerSpots.contains(linha + "," + casaAnterior(coluna))) && (playerSpots.contains(linha + "," + casaAnterior(casaAnterior(coluna))))){
                        count++;
                    }
                }
            }
        }
        
        return count;
    }

}
