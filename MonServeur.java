/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jules
 */
public class MonServeur extends UnicastRemoteObject implements MonServeurInterface {
    
    private Map<String, String> listId;
    private Map<Integer, String> listeMessage;
    private static int ID = 0;
    
    public MonServeur() throws RemoteException {
        super();
        listId = new HashMap<>();
        listeMessage = new HashMap<>();
    }

    @Override
    public void connectClient(String identifiant) throws RemoteException {
        try {
            listId.put(getClientHost(), identifiant);
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deconnectClient() throws RemoteException {
        try {
            listId.remove(getClientHost());
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String who() throws RemoteException {
        String result = "";
        for (Object o : listId.values().toArray()) {
            result = result + o.toString() + "\n";
        }
        return result;
    }

    @Override
    public void envoiMessage(String message) throws RemoteException {
        try {
            listeMessage.put(ID, listId.get(getClientHost()) + " : " + message);
            ID++;
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getMessage(int index) throws RemoteException {
        String result = "";
        for (int i = index; i<ID;i++) {
            result = result + listeMessage.get(i) + "\n";
        }
        return result;
    }

}
