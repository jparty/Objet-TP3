/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jules
 */
public interface MonServeurInterface extends Remote {
    
    public int connectClient(String identifiant) throws RemoteException;
    
    public void deconnectClient() throws RemoteException;
    
    public String who() throws RemoteException;
    
    public boolean envoiMessage(String message) throws RemoteException;
    
    public String getMessage(int index) throws RemoteException;
}
