package trabalhoia.minimax;

import NineMensMorris.GameInfo;
import java.util.List;



public final class EvaluationFunction {
    
    private GameInfo info;
    


    public EvaluationFunction(GameInfo info) {
        this.info = info;
    }

    
    public int phase1(boolean isLineInThisMove, int jogador) {
        return evaluate(18, 26, 1, 9, 10, 7, 0, isLineInThisMove, jogador);
    }    
    
    public int phase2(boolean isLineInThisMove, int jogador) {
        return evaluate(14, 43, 10, 11, 0, 0, 8, isLineInThisMove, jogador);
    }    
    
    public int phase3(boolean isLineInThisMove, int jogador) {
        return evaluate(16, 43, 10, 11, 0, 0, 8, isLineInThisMove, jogador);
    }
    
    private int evaluate(int w1, int w2, int w3, int w4, int w5, int w6, int w7, boolean isLineInThisMove, int jogador ) {        
        
        return w1 * (isLineInThisMove ? (jogador == NewAgent.JOGADOR ? 1 : -1) : 0) +
               w2 * (getTotalLinhasFechadas(NewAgent.JOGADOR) - getTotalLinhasFechadas(NewAgent.OPONENTE)) +
               w3 * (getTotalPecasBloqueadas(NewAgent.OPONENTE) - getTotalPecasBloqueadas(NewAgent.JOGADOR)) +
               w4 * (info.getPlayerSpots().size() - info.getOpponentSpots().size()) +
               w5 * (0) +
               w6 * (getCornersOfThreeForPlayer() - getCornersOfThreeForOpponent()) +
               w7 * (getCornersOfFiveForPlayer() - getCornersOfFiveForOpponent());
    }
    

    /*Retorna total de linhas fechadas pelo jogador*/
    public int getTotalLinhasFechadas(int jogador){

        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Jogador Atual;
        List<String> playerSpots =  jogador==1 ? this.info.getPlayerSpots() : this.info.getOpponentSpots();
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas de três estão formadas no tabuleiro a favor do Jogador*/
        int count = 0;
                
        //Percorre todas as peças do Jogador, pará verificar se há 3 em sequencia(linha formada)        
        for (int i=0; i<playerSpots.size(); i++){
            
            /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            uso do split para separar a linha e a coluna, de forma a facilitar verificações*/
            String part[] = playerSpots.get(i).split(",");
            
            //Passagem das partes da string(linha,coluna) para variaveis do tipo Inteiro, para facilitar verificações
            int posicaoUm = Integer.parseInt(part[0]);
            int posicaoDois = Integer.parseInt(part[1]);
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos, sendo assim não podem
            formar linha no sentido coluna(do centro do tabuleiro para extremidade).*/
            if(posicaoDois % 2 == 0){
                /*Caso a coluna esteja na posição 6, o trio para fechar a linha seria '6','7' e '0' em vez de
                '6','7' e '8', por isso precisa de um tratamento especial.*/
                if(posicaoDois == 6){
                    /*Verifica se a prixima casa para fechar uma linha contem uma peça do Jogador*/
                    if(playerSpots.contains(posicaoUm+","+(posicaoDois + 1))){
                        /*Verifica se 2 casas depois para fechar uma linha contem uma peça do Jogador*/
                        if(playerSpots.contains(posicaoUm+","+(0))){
                            /*Caso todas verificações forem verdadeiras até aqui, é porque o Jogador tem uma linha
                            formada*/
                            count++;
                        }
                    }
                }
                /*Caso não seja a coluna 6, o tratamento é igual, verifica se a casa atual e as duas posteriores
                (do tabuleiro) possuem uma peça do jogador(se estão em getJogadorSpots)*/
                else{
                    if(playerSpots.contains(posicaoUm+","+(posicaoDois + 1))){
                        if(playerSpots.contains(posicaoUm+","+(posicaoDois + 2))){
                            /*Caso todas verificações forem verdadeiras até aqui, é porque o Jogador tem uma linha
                            formada*/
                            count++;
                        }
                    }
                }
            }
            /* Verificação identica para os numero pares(peças que estão no canto) porém com o acrécimo de uma
            verificação do sentido coluna(do centro do tabuleiro para extremidade), para essa verificação
            adicional só é necessário fazer as verificações se tiver uma peça na linha 0, pois um trio fechado
            em sentido coluna, abrangira as 3 linhas do tabuleiro, logo, se não tiver uma peça na linha 0 é
            porque não tem um trio fechado.
            */
            else if(posicaoDois%2 == 1 && posicaoUm == 0){
                if(playerSpots.contains((posicaoUm + 1)+","+posicaoDois)){
                    if(playerSpots.contains((posicaoUm + 2)+","+posicaoDois)){
                        count++;
                    }
                }
            }
        }
        
        //Retorno do numero de trios fechados encontrado no tabuleiro
        return count;
    }
    
    /*Retorna total de peças bloqueadas do jogador*/
    public int getTotalPecasBloqueadas(int jogador){

        //Busca todos campos do tabuleiro que estão ocupados por uma peça do Jogador Atual;
        List<String> playerSpots = jogador==1 ? this.info.getPlayerSpots() : this.info.getOpponentSpots();
        
        //Busca todos campos do tabuleiro que estão livres(sem nenhuma peça tanto do Jogador quanto Oponente)
        List<String> emptySpots = this.info.getEmptySpots();
        
        /*Inicialização da variavel count(contadora) que irá contar, e retornar no final do método quantas
        linhas de três estão formadas no tabuleiro a favor do Jogador*/
        int count = 0;
                
        //Percorre todas as peças do Jogador, pará verificar se há 3 em sequencia(linha formada)        
        for (int i=0; i<playerSpots.size(); i++){
            
             /*PlayerSpots retorna as posições das peças do Jogador, a posição consiste em linha e coluna (L,C)
            uso do split para separar a linha e a coluna, de forma a facilitar verificações*/
            String part[] = playerSpots.get(i).split(",");
            
            //Passagem das partes da string(linha,coluna) para variaveis do tipo Inteiro, para facilitar verificações
            int posicaoUm = Integer.parseInt(part[0]);
            int posicaoDois = Integer.parseInt(part[1]);
            
            /*Verificação se a coluna é um numero par, todos numeros pares ficam em cantos, sendo assim não podem se
            movimentar no sentido coluna(do centro do tabuleiro para extremidade)*/
            if(posicaoDois % 2 == 0){
                if(posicaoDois == 0){
                    if(!emptySpots.contains(posicaoUm+","+(posicaoDois + 1))){
                        if(!emptySpots.contains(posicaoUm+","+(7))){
                            /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                            peças bloqueadas*/
                            count++;
                        }
                    }
                }
                else{
                    if(!emptySpots.contains(posicaoUm+","+(posicaoDois + 1))){
                        if(!emptySpots.contains(posicaoUm+","+(posicaoDois - 1))){
                            /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                            peças bloqueadas*/
                            count++;
                        }
                    }
                }
            }
            /*Senão, se for impar, necessita de verificações adicionais para verificar disponibilidade de se
            movimentar, no sentido coluna(do centro do tabuleiro para extremidade)*/
            else if(posicaoDois%2 == 1){
                /*Caso a coluna seja igual a 7, a proxima casa(7+1) será a de coluna 0 e não 8, por isso precisa
                de um tratamendo especial*/
                if(posicaoDois == 7){
                    /*Verificação de forma estatica da coluna(7+1) 0 para ver se está livre, se não há peças bloqueando,
                    verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                    if(!emptySpots.contains(posicaoUm+","+(0))){
                        /*Verificação da coluna(7-1) 6 para ver se está livre, se não há peças bloqueando,
                        verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                        if(!emptySpots.contains(posicaoUm+","+(posicaoDois - 1))){
                            /*Peças de numero impar estão posicionadas no meio, possibilitando movimentação do tipo coluna
                            (do centro do tabuleiro para a extremidade), logo é necessario verificar disponibilidade de 
                            movimentação em tal sentido*/
                            
                            /*No caso de estar na extremidade exterior(posicaoUm == 0) do tabuleiro só é necessaria uma verificação,
                            pois só há uma movimentação possivel no sentido coluna*/
                            if(posicaoUm == 0){
                                if(!emptySpots.contains((posicaoUm + 1)+","+posicaoDois)){
                                    /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                    peças bloqueadas*/
                                    count++;
                                }
                            }
                            /*No caso de estar no meio, entre as 2 extremidades(posicaoUm == 1), é necessaria duas verificações,
                            pois há como se mover tanto para extremidade inferior ou exterior*/
                            else if(posicaoUm == 1){
                                if(!emptySpots.contains((posicaoUm + 1)+","+posicaoDois)){
                                    if(!emptySpots.contains((posicaoUm - 1)+","+posicaoDois)){
                                        /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                    peças bloqueadas*/
                                        count++;
                                    }
                                }
                            }
                            /*No caso de estar na extremidade inferior(posicaoUm == 2) do tabuleiro só é necessaria uma verificação,
                            pois só há uma movimentação possivel no sentido coluna*/
                            else if(posicaoUm == 2){
                                if(!emptySpots.contains((posicaoUm - 1)+","+posicaoDois)){
                                    /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                    peças bloqueadas*/
                                    count++;
                                }
                            }
                        }
                    }
                
                }
                /*Se a coluna não for igual a 7, faz as verificações de modo normal*/
                else{
                    /*Verificação do spot com a coluna seguinte(posicaoDois+1)para ver se está livre, se não há peças bloqueando,
                        verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                    if(!emptySpots.contains(posicaoUm+","+(posicaoDois + 1))){
                       /*Verificação do spot com a coluna anterior(posicaoDois-1)para ver se está livre, se não há peças bloqueando,
                        verificação feita: se a peça não estiver na lista de spots livres(vazios) é porque há peça*/
                        if(!emptySpots.contains(posicaoUm+","+(posicaoDois - 1))){
                            /*Peças de numero impar estão posicionadas no meio, possibilitando movimentação do tipo coluna
                            (do centro do tabuleiro para a extremidade), logo é necessario verificar disponibilidade de 
                            movimentação em tal sentido*/
                            
                            /*No caso de estar na extremidade exterior(posicaoUm == 0) do tabuleiro só é necessaria uma verificação,
                            pois só há uma movimentação possivel no sentido coluna*/
                            if(posicaoUm == 0){
                                if(!emptySpots.contains((posicaoUm + 1)+","+posicaoDois)){
                                    /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                    peças bloqueadas*/
                                    count++;
                                }
                            }
                            /*No caso de estar no meio, entre as 2 extremidades(posicaoUm == 1), é necessaria duas verificações,
                            pois há como se mover tanto para extremidade inferior ou exterior*/
                            else if(posicaoUm == 1){
                                if(!emptySpots.contains((posicaoUm + 1)+","+posicaoDois)){
                                    if(!emptySpots.contains((posicaoUm - 1)+","+posicaoDois)){
                                        /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                        peças bloqueadas*/
                                        count++;
                                    }
                                }
                            }
                            /*No caso de estar na extremidade inferior(posicaoUm == 2) do tabuleiro só é necessaria uma verificação,
                            pois só há uma movimentação possivel no sentido coluna*/
                            else if(posicaoUm == 2){
                                if(!emptySpots.contains((posicaoUm - 1)+","+posicaoDois)){
                                    /*Se todas verificações forem verdadeiras até aqui, incrementa o count(contador de numeros de
                                    peças bloqueadas*/
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
            
        return count;
    }
    
    private int getCornersOfThreeForPlayer() {
        return 0;
    }
    
    private int getCornersOfThreeForOpponent() {
        return 0;
    }
    
    private int getCornersOfFiveForPlayer() {
        return 0;
    }
    
    private int getCornersOfFiveForOpponent() {
        return 0;
    }
}
