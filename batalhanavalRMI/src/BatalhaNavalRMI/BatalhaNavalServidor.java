package BatalhaNavalRMI;

import java.rmi.RemoteException;
import java.util.Random;

public class BatalhaNavalServidor implements BatalhaNavalServico {

    public String jogador1 = "";
    public String jogador2 = "";
    public boolean qtdJogadores=true;
    public boolean mesmoNome=true;
    public String vez = "";

    public char posicaoJogador1[][];
    public char posicaoJogador2[][];

    public int tamanhoLinhaColuna;

    //Tamanho da matriz
    public void tamanhoMatriz(int valor) {
        tamanhoLinhaColuna = valor;
    }

    //Método para atribuir nome dos jogadores
    public String iniciarJogo(String nome) {
        if (jogador1.isEmpty()) {
            System.out.println("==== Log do Servidor: ====");
            System.out.print("jogador 1: ");
            System.out.println(nome);
            jogador1 = nome;
        } else if (jogador2.isEmpty()) {
            if(nome.equals(jogador1)){
                mesmoNome=false;
            }else{
                System.out.print("jogador 2: ");
                System.out.println(nome);
                jogador2 = nome;
                qtdJogadores=false;
                mesmoNome=true;
                criaMatriz();
            }
        }
        return "";
    }

    //Envia uma lista para o cliente com o nome dos dois jogadores.
    public String listaJogadores() {
        return "Jogador 1: " + jogador1 + "\nJogador 2: " + jogador2;
    }
    
    //verifica disponibilidade de usuários
    public boolean disponibilidade(){
        return qtdJogadores;
    }
    //verifica disponibilidade de usuários
    public boolean nomedoJogador(){
        return mesmoNome;
    }
    //Verifica se já existe o segundo jogador
    public String procurandoJogador() {
        String msg = "";
        if (jogador2.isEmpty()) {
            msg = "";
        }else{
            String primerojogador = jogador1;
            vez = primerojogador;
            msg = "Vamos começar!";
        }
        return msg;
    }

    public void criaMatriz() {
        //chama o método que adiciona o tamanho da matriz
        tamanhoMatriz(5);

        //cria a matriz
        char matriz1[][] = new char[tamanhoLinhaColuna][tamanhoLinhaColuna];
        char matriz2[][] = new char[tamanhoLinhaColuna][tamanhoLinhaColuna];

        //vetores com a posicao dos navios
        int vetorPosicao1[] = new int[(int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))];
        int vetorPosicao2[] = new int[(int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))];

        vetorPosicao1 = geraPosicoesNavios();
        vetorPosicao2 = geraPosicoesNavios();

        /*for (int i = 0; i < vetorPosicao1.length; i++) {
            System.out.print("| " + vetorPosicao1[i] + " |");
        }
        System.out.println("");
        for (int i = 0; i < vetorPosicao1.length; i++) {
            System.out.print("| " + vetorPosicao2[i] + " |");
        }
        System.out.println("");*/

        int tamanho = 0, colunaVetor1 = 0, colunaVetor2 = 0;

        for (int linha = 0; linha < tamanhoLinhaColuna; linha++) {
            for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                if (tamanho == vetorPosicao1[colunaVetor1]) {
                    if (tamanho == vetorPosicao1[vetorPosicao1.length - 1]) {

                    } else {
                        colunaVetor1++;
                    }
                    matriz1[linha][coluna] = 'N';
                } else {
                    matriz1[linha][coluna] = 30;
                }
                if (tamanho == vetorPosicao2[colunaVetor2]) {
                    if (tamanho == vetorPosicao2[vetorPosicao2.length - 1]) {

                    } else {
                        colunaVetor2++;
                    }
                    matriz2[linha][coluna] = 'N';
                } else {
                    matriz2[linha][coluna] = 30;
                }
                tamanho++;
            }
        }
        posicaoJogador1 = matriz1;
        posicaoJogador2 = matriz2;

        /*tamanho = 0;
        System.out.println("Jogador 1 - " + jogador1 + ":");
        for (int linha = 0; linha < tamanhoLinhaColuna; linha++) {
            for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                System.out.print(posicaoJogador1[linha][coluna] + " ");
            }
            System.out.println("");
        }
        tamanho = 0;
        System.out.println("Jogador 2 - " + jogador2 + ":");
        for (int linha = 0; linha < tamanhoLinhaColuna; linha++) {
            for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                System.out.print(posicaoJogador2[linha][coluna] + " ");
            }
            System.out.println("");
        }*/
    }

    public int[] geraPosicoesNavios() {
        int vetorPosicao[] = new int[(int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))];
        Random gerador = new Random();
        //atribui os valores randomicos
        for (int i = 0; i < vetorPosicao.length; i++) {
            vetorPosicao[i] = gerador.nextInt((tamanhoLinhaColuna * tamanhoLinhaColuna));
            if (i > 0) {
                for (int cont = 0; cont < i; cont++) {
                    if (vetorPosicao[cont] == vetorPosicao[i]) {
                        i--;
                        break;
                    }
                }
            }
        }

        int aux = 0;
        int i = 0;

        //Ordenação usando a técnica de Bubble Sort
        for (i = 0; i < vetorPosicao.length; i++) {
            for (int j = 0; j < vetorPosicao.length - 1; j++) {
                if (vetorPosicao[j] > vetorPosicao[j + 1]) {
                    aux = vetorPosicao[j];
                    vetorPosicao[j] = vetorPosicao[j + 1];
                    vetorPosicao[j + 1] = aux;
                }
            }
        }

        return vetorPosicao;
    }

    public char[][] retornaMatriz(String nome) {
        System.out.println("enviou matriz do: " + nome);
        char[][] matriz = posicaoJogador1;
        if (nome.equals(jogador2)) {
            matriz = posicaoJogador2;
        }
        return matriz;
    }

    public char[][] retornaMatrizOponente(String nome) {
        char[][] matriz = new char[tamanhoLinhaColuna][tamanhoLinhaColuna];
        if ((nome.equals(jogador1))) {
            for (int linha = 0; linha < tamanhoLinhaColuna; linha++) {
                for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                    matriz[linha][coluna] = 30;
                    if (posicaoJogador2[linha][coluna] == 'X') {
                        matriz[linha][coluna] = 'X';
                    } else if (posicaoJogador2[linha][coluna] == 'E') {
                        matriz[linha][coluna] = 'E';
                    }
                }
            }
        } else {
            for (int linha = 0; linha < tamanhoLinhaColuna; linha++) {
                for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                    matriz[linha][coluna] = 30;
                    if (posicaoJogador1[linha][coluna] == 'X') {
                        matriz[linha][coluna] = 'X';
                    }else if (posicaoJogador1[linha][coluna] == 'E') {
                        matriz[linha][coluna] = 'E';
                    }
                }
            }
        }
        return matriz;
    }

    public int naviosDestruidos1 = 0;
    public int naviosDestruidos2 = 0;

    public String ganhador() {
        String msg = "";
        if (naviosDestruidos1 == (int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))) {
            msg = jogador2;
        } else if (naviosDestruidos2 == (int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))) {
            msg = jogador1;
        }
        return msg;
    }

    public boolean vez(String nome) {
        boolean verifica = true;
        if (vez.equals(nome)) {
            verifica = true;
        } else {
            verifica = false;
        }
        return verifica;
    }
    public boolean verifAtingido = false;
    public String ataque(String[] tentativa) {

        String msg = " ";
        int tamanho = 0;
        System.out.println("valor da tentativa ("+tentativa[0]+"): " + (Integer.parseInt(tentativa[1])));

        if (jogador1.equals(tentativa[0])) {
            int linha;
            for (linha = 0; linha < tamanhoLinhaColuna; linha++) {
                for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                    if (tamanho == Integer.parseInt(tentativa[1])) {
                        if (posicaoJogador2[linha][coluna] == 'N') {
                            posicaoJogador2[linha][coluna] = 'X';
                            naviosDestruidos2++;
                            linha = tamanhoLinhaColuna;
                            System.out.println(jogador2+" foi atingido!");
                            verifAtingido = true;
                            msg = "Você acertou um navio do " + jogador2 + "!";
                            break;
                        } else {
                            posicaoJogador2[linha][coluna] = 'E';
                            msg = "Você não acertou um navio do " + jogador2 + "!";
                        }
                    }
                    tamanho++;
                }
            }
            vez = jogador2;
        } else {
            int linha;
            for (linha = 0; linha < tamanhoLinhaColuna; linha++) {
                for (int coluna = 0; coluna < tamanhoLinhaColuna; coluna++) {
                    if (tamanho == Integer.parseInt(tentativa[1])) {
                        if (posicaoJogador1[linha][coluna] == 'N') {
                            posicaoJogador1[linha][coluna] = 'X';
                            naviosDestruidos1++;
                            linha = tamanhoLinhaColuna;
                            System.out.println(jogador1+" foi atingido!");
                            verifAtingido = true;
                            msg = "Você acertou um navio do " + jogador1 + "!";
                            break;
                        } else {
                            posicaoJogador1[linha][coluna] = 'E';
                            msg = "Você não acertou um navio do " + jogador1 + "!";
                        }
                    }
                    tamanho++;
                }
            }
            vez = jogador1;
        }
        return msg;
    }
    public int auxAtingido=0;
    public String atingido(String nome, String pergunta){
        String msg="";
        if(auxAtingido<=1){
            msg = "";
            auxAtingido++;
        }
        else if(verifAtingido && !pergunta.equals("Acabou")){
            msg = "Você foi atingido";
            verifAtingido = false;
        }else if((naviosDestruidos1 == (int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33))) || (naviosDestruidos2 == (int) ((tamanhoLinhaColuna * tamanhoLinhaColuna) * (0.33)))){
            msg = "Acabou";
        }else if(verifAtingido==false && !pergunta.equals("Acabou")){
            msg = "Você não foi atingido";
        }
        
        return msg;
    }
}
