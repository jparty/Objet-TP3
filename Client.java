/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

/**
 *
 * @author jules
 */
public class Client {
    
    public static int dernierMessageRecu = 0;
    
    public static void printDerniersMessages(MonServeurInterface msi) throws RemoteException {
        System.out.println(msi.getMessage(dernierMessageRecu));
    }
    
    public static void send(MonServeurInterface msi, String message) throws RemoteException {
        msi.envoiMessage(message);
        printDerniersMessages(msi);
    }

    public static void main(String[] args) throws Exception {
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }*/
        String url = "//169.254.68.95/monServeur";
        MonServeurInterface monServeur = (MonServeurInterface) Naming.lookup(url);

        monServeur.connectClient("Jules");
        System.out.print(monServeur.who());
        send(monServeur, "coucou");
        send(monServeur, "ça va ?");
    }
}
