package BatalhaNavalRMI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class VisualClientBatalhaNaval{

    Registry registry = null;
    static BatalhaNavalServico server;
    
    public VisualClientBatalhaNaval() {
        initCliente();
    }

    private void initCliente() {
        try {

            Registry registry = LocateRegistry.getRegistry("localhost", 1147);

            server = (BatalhaNavalServico) registry.lookup("BatalhaNavalServico");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static int tamanhoLinhaColuna = 5;
    public static void main(String args[]) throws RemoteException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisualClientBatalhaNaval();
            }
        });
        
        System.out.println("= BEM VINDO A BATALHA NAVAL =");
        System.out.println("========= INSTRUÇÕES ========");
        System.out.println("As posições vão de 1 a 25.\nExemplo");
        int auxPosicao = 1;
        for(int linha = 0; linha<tamanhoLinhaColuna; linha++){
            for(int coluna = 0; coluna<tamanhoLinhaColuna; coluna++){
                if(auxPosicao<10)
                    System.out.print(auxPosicao+"  ");
                else
                System.out.print(auxPosicao+" ");
                auxPosicao++;
            }
            System.out.println("");
        }
        System.out.println("======== ATAQUES ========");
        System.out.println("E --- não acertou o navio");
        System.out.println("X ------- Navio destruído");
        System.out.println("=========================");
        System.out.println("Digite seu NickName: ");
        Scanner ler = new Scanner(System.in);
        String nome = ler.next();
        if(server.disponibilidade()==false){
            System.out.println("Já existem dois jogadores na partida. Por favor, aguarde!");
            System.exit(0);
        }
        System.out.println("=========================");
        System.out.println(server.iniciarJogo(nome));
        while(server.nomedoJogador()!=true){
            System.out.println("Já existem um jogador com esse nome. Tente novamente!");
            System.out.println("Digite seu NickName: ");
            nome = ler.next();
            System.out.println("=========================");
            System.out.println(server.iniciarJogo(nome));
        }
        int cont = 0;
        while(true){
            if(server.procurandoJogador().equals("Vamos começar!")){
                break;
            }
            if(cont==0){
                System.out.println("Esperando Oponente, por favor aguarde...");
                cont++;
            }
        }
        
        System.out.println(server.listaJogadores());
        System.out.println(server.procurandoJogador());
        
        char[][] matriz1;
        char[][] matriz2;
        
        String[] tentativa = {nome,"xx"};
        int valor;
        boolean valida = true;
        while(server.ganhador().isEmpty()){
            if(server.vez(nome)){
                System.out.println("====================");
                System.out.println("=== Minha matriz ===");
                matriz1 = server.retornaMatriz(nome);
                montaTabuleiro(matriz1);
                System.out.println("= Matriz do Oponente =");
                matriz2 = server.retornaMatrizOponente(nome); 
                montaTabuleiro(matriz2);
                if(server.atingido(nome,"Acabou").equals("Acabou"))
                    break;
                else
                System.out.println(server.atingido(nome,"xxx"));
                System.out.print("Entre com o valor de ataque (1 a 25): ");
                valor = (ler.nextInt())-1;
                tentativa[1] = Integer.toString(valor);
                System.out.println(server.ataque(tentativa));
                valida = true;
            }else{
                if(valida){
                    System.out.println("=== Minha matriz ===");
                    matriz1 = server.retornaMatriz(nome);
                    montaTabuleiro(matriz1);
                    System.out.println("= Matriz do Oponente =");
                    matriz2 = server.retornaMatrizOponente(nome); 
                    montaTabuleiro(matriz2);
                    System.out.println("Aguarde sua vez, pois outro jogador está jogando!");
                    valida = false;
                }
            }            
        }
        if(server.ganhador().equals(nome))
            System.out.println("Parabéns! Você ganhou a partida.");
        else
            System.out.println("Poxa! Você perdeu a partida!");
        
        System.exit(0);
        
    }
    public static void montaTabuleiro(char[][] tabuleiro){
        for(int linha = 0; linha<tamanhoLinhaColuna; linha++){
            for(int coluna = 0; coluna<tamanhoLinhaColuna; coluna++){
                System.out.print(tabuleiro[linha][coluna]+" ");
            }
            System.out.println("");
        }
    } 
    
}

