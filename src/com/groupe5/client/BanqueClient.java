package com.groupe5.client;

import com.groupe5.ge.CompteException;
import com.groupe5.server.InterfaceBanque;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;


/**
 * Implémentation du Client
 */
public class BanqueClient {


    private InterfaceBanque b;
    private String hostName;
    private String serviceName;
    private Scanner sc;
    private String nom;
    private String password;

    public BanqueClient() {
        hostName = "localhost";
        serviceName = "banque";
        sc = new Scanner(System.in);

        try {
            // Connexion au serveur RMI et instanciation
            // de la classe mis à disposition par le RMI register
            // Utilisation d'une IP diff pour faire des tests : rmi://r20223:1099/MyReverse
            b = (InterfaceBanque) Naming.lookup("rmi://" + hostName + "/" + serviceName);

            // Utilisation de la méthode de reverseString
            // Elle prend en paramètre la chaine de caractère passé
            // en paramètre au moment de l'appelle de cette méthode
            nom = "";
            password = "";

            System.out.println("=============================================");
            System.out.println("Bienvenu sur le site de votre banque en ligne");
            System.out.println("=============================================");

            System.out.println("\nQue voulez vous faire ?");

            System.out.println("1 - Ouvrir un compte");
            System.out.println("2 - Consulter un compte");
            System.out.println("3 - Fermer un compte");

            creer();
            // connecter();
            // fermer();
            //historique();


            if (sc.nextInt() == 1)
                creer();
            if (sc.nextInt() == 2)
                connecter();
            if (sc.nextInt() == 3)
                fermer();
            else
                System.out.println("\nTu ne sais pas compter de 1 à 3 ?");


        } catch (RemoteException | NotBoundException |
                MalformedURLException e) {
            System.out.println("Erreur d'acces a l'objet distant.");
            System.out.println(e.toString());
        }


    }

    private void historique() throws RemoteException {


        System.out.println("\nNous allons fermer votre compte\n");

        System.out.println(b.historique());


    }

    private void fermer() throws RemoteException {

        try {


            System.out.println("\nNous allons fermer votre compte\n");

            System.out.println("Entrez votre nom : ");
            nom = sc.nextLine();

            System.out.println("Entrez votre mot de passe : ");
            password = sc.nextLine();

            System.out.println("Verifaction du compte . . . ");
            b.verifie(nom, password);
            System.out.println("[OK] Compte trouvé,");

            System.out.print("Bonjour " + b.getNom(b.verifie(nom, password).getNom()));
            System.out.println(", Votre compte va etre fermé");
            b.fermerCompte(nom, password);
            System.out.println("[OK] Compte fermé.");

        } catch (CompteException e) {
            System.out.println(e.getMessage());
        }


    }


    private void connecter() throws RemoteException {

        try {


            System.out.println("\nNous allons créer consulter votre compte\n");

            System.out.println("Entrez votre nom : ");
            nom = sc.nextLine();

            System.out.println("Entrez votre mot de passe : ");
            password = sc.nextLine();

            System.out.println("Verifaction du compte . . . ");
            b.verifie(nom, password);
            System.out.println("[OK] Compte trouvé,");

            System.out.println("Bonjour " + b.getNom(b.verifie(nom, password).getNom()));
            System.out.println("\n\nVous pouvez maintenant utiliser votre compte a votre gise :)");

            operations();

        } catch (CompteException e) {
            System.out.println(e.getMessage());
        }


    }

    private void creer() throws RemoteException {

        try {

            System.out.println("\nNous allons créer un compte ensemble\n");

            System.out.println("Entrez votre nom : ");
            nom = sc.nextLine();

            System.out.println("Entrez votre mot de passe : ");
            password = sc.nextLine();

            System.out.println("Création du compte . . . ");
            b.creerCompte(nom, password);
            System.out.println("[OK] Création du compte");

            System.out.println("Bonjour " + b.getNom(b.verifie(nom, password).getNom()));
            System.out.println("\n\nVous pouvez maintenant utiliser votre compte a votre gise :)");

            historique();
            operations();

        } catch (CompteException e) {
            System.out.println(e.getMessage());
        }


    }

    private void operations() throws CompteException, RemoteException {
        while (true) {

            int reponse = 0;
            int argent = 0;

            do {


                System.out.println("\nQue voulez vous faire ?");

                System.out.println("1 - Consulter mon solde");
                System.out.println("2 - Retirer de l'argent");
                System.out.println("3 - Déposer de l'argent");

                reponse = sc.nextInt();


                switch (reponse) {
                    case 1:
                        System.out.println("Solde du compte de " + nom + " : " + b.balance(b.verifie(nom, password)));
                        break;
                    case 2:
                        System.out.println("Combien voulez-vous retirer ?");
                        argent = sc.nextInt();
                        b.retirer(argent, b.verifie(nom, password));
                        System.out.println("Retrait d'argent");
                        System.out.println("Solde du compte de " + nom + " : " + b.balance(b.verifie(nom, password)));
                        break;
                    case 3:
                        System.out.println("Combient voulez-vous déposer ?");
                        argent = sc.nextInt();
                        System.out.println("Vous voulez deposer " + argent + " $");
                        b.deposer(argent, b.verifie(nom, password));
                        System.out.println("Dépôt d'argent");
                        System.out.println("Solde du compte de " + nom + " : " + b.balance(b.verifie(nom, password)));
                        break;
                    default:
                        System.out.println("\nTu ne sais pas compter de 1 à 3 ?");
                }


            } while (reponse < 1 || reponse > 3);

        }
    }


    public static void main(String[] args) {
        new BanqueClient();
    }
}
