

package BatalhaNavalRMI;

import java.rmi.Remote;
import java.rmi.RemoteException; 

//interface metodos abstrados (assinatura)
public interface BatalhaNavalServico extends Remote {
    
    public String iniciarJogo(String nome) throws RemoteException;
    
    public String procurandoJogador() throws RemoteException;
    
    public String listaJogadores() throws RemoteException;
    
    public char[][] retornaMatriz(String nome) throws RemoteException;
    
    public char[][] retornaMatrizOponente(String nome) throws RemoteException;
    
    public String ganhador() throws RemoteException;
    
    public boolean vez(String nome) throws RemoteException;
    
    public String atingido(String nome, String pergunta) throws RemoteException;
    
    public String ataque(String[] nome) throws RemoteException;
    
    public boolean disponibilidade() throws RemoteException;
    
    public boolean nomedoJogador() throws RemoteException;
}
