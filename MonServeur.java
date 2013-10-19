/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jules
 */
public class MonServeur extends UnicastRemoteObject implements MonServeurInterface {

    private Map<String, String> listId;
    private List<String> listeMessage;

    public MonServeur() throws RemoteException {
        super();
        listId = new HashMap<>();
        listeMessage = new ArrayList<>();
    }

    @Override
    public int connectClient(String identifiant) throws RemoteException {
        try {
            listId.put(getClientHost(), identifiant);
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeMessage.size();
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
        String result;
        Object[] listIdArray = listId.values().toArray();
        if (listIdArray.length == 0) {
            result = "Aucun utilisateur connecté.\n";
        } else {
            result = "Voici la liste des utilisateurs connectés :\n";
            for (Object o : listId.values().toArray()) {
                result = result + o.toString() + "\n";
            }
        }
        return result;
    }

    @Override
    public boolean envoiMessage(String message) throws RemoteException {
        try {
            if (listId.containsKey(getClientHost())) {
                listeMessage.add(listId.get(getClientHost()) + " : " + message + "\n");
                return true;
            }
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String getMessage(int index) throws RemoteException {
        try {
            return listeMessage.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }
}
