package BatalhaNavalRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServidorBatalhaNaval {

    public static void main(String args[]) {
        try {
            
            BatalhaNavalServidor servidor = new BatalhaNavalServidor();            
            BatalhaNavalServico stub = (BatalhaNavalServico) UnicastRemoteObject.exportObject((BatalhaNavalServico) servidor, 0);
            Registry registry = LocateRegistry.createRegistry(1147);
            registry.rebind("BatalhaNavalServico", stub);
            
        } catch (Exception e) {
                e.printStackTrace();
        }

    }

}

