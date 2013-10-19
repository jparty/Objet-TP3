/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagerie;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jules
 */
public class Client {

    private static int dernierMessageRecu = 0;
    private static boolean isConnected = false;
    private static Timer timer;
    private static MonServeurInterface monServeur;

    public static void main(String[] args) throws Exception {
        /*if (System.getSecurityManager() == null) {
         System.setSecurityManager(new RMISecurityManager());
         }*/
        String url = "//localhost/monServeur";
        monServeur = (MonServeurInterface) Naming.lookup(url);

        timer = new Timer();
        timer.schedule(new AfficheDerniersMessages(monServeur), 0, 1000);

        lireCommandes();
    }

    private static void lireCommandes() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            String[] lineDivided = line.trim().split(" ", 2);
            String cmd = lineDivided[0];
            String arg;
            if (lineDivided.length == 1) {
                arg = null;
            } else {
                arg = lineDivided[1];
            }
            switch (cmd) {
                case "connect":
                    connect(arg);
                    break;
                case "send":
                    send(arg);
                    break;
                case "bye":
                    bye();
                    break;
                case "who":
                    who();
                    break;
                default:
                    afficheMessageErreur();
                    break;
            }
        }
    }

    private static void connect(String arg) throws RemoteException {
        if (arg == null) {
            System.out.println("Rentrez un identifiant s'il vous plait.");
        } else {
            dernierMessageRecu = monServeur.connectClient(arg);
            isConnected = true;
            System.out.println(arg + " est connecté.");
        }
    }

    private static void send(String arg) throws RemoteException {
        if (arg == null) {
            System.out.println("Rentrez un message s'il vous plait.");
        } else {
            if (!monServeur.envoiMessage(arg)) {
                System.out.println("Vous devez vous connecter pour pouvoir envoyer des messages.");
            }
        }
    }

    private static void bye() throws RemoteException {
        monServeur.deconnectClient();
        isConnected = false;
        System.out.println("Vous avez bien été déconnecté.");
    }

    private static void who() throws RemoteException {
        System.out.print(monServeur.who());
    }

    private static void afficheMessageErreur() {
        System.out.println("La commande n'a pas été reconnue.\nEssayer une des commandes suivantes :\n"
                + "    connect id : Vous connecte au serveur avec l'identifiant id.\n"
                + "    send msg   : Envoie le message msg aux autres utilisateurs connectés.\n"
                + "    bye        : Vous deconnecte du serveur.\n"
                + "    who        : Affiche les identifiants des autres utilisateurs connectés.");
    }

    private static class AfficheDerniersMessages extends TimerTask {

        MonServeurInterface monServeur;

        public AfficheDerniersMessages(MonServeurInterface monServeur) {
            super();
            this.monServeur = monServeur;
        }

        @Override
        public void run() {
            if (isConnected) {
                try {
                    String message = monServeur.getMessage(dernierMessageRecu);
                    while (message != null) {
                        System.out.print(message);
                        dernierMessageRecu++;
                        message = monServeur.getMessage(dernierMessageRecu);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
