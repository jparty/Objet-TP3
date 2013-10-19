/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author jules
 */
public class MonServeurMain {

    public static void main(String[] args) {

        /*try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        try {
            // create a RMI registry
            Registry r = LocateRegistry.createRegistry(1099);

            // create and publish car factory server object
            MonServeur serveur1 = new MonServeur();
            r.rebind("monServeur", serveur1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("MonServeur is running.");

    }
    
}
