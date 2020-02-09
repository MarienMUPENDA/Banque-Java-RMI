package com.groupe5.server;

import com.groupe5.client.Compte;
import com.groupe5.server.Banque;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;


/**
 * Implémentation du Serveur
 * pour l'utilisation du compte
 */
public class BanqueServer {

    public static void main(String[] args) {
        String hostName = "localhost";
        String serviceName1 = "banque";
        String serviceName2 = "compte";

        startRMIRegistry();


        try {
            //LocateRegistry.createRegistry(109);
            //System.out.println("Serveur : Construction de l'implementation ");

            // Implémentation de la classe mis à dispositon
            // sur le serveur
            Banque b = new Banque();
            System.out.println("Objet Banque lie dans le RMIregistry");

            // Mis à disposition de la méthode sur l'adresse rmi://localhost:1099/MyReverse

            Naming.rebind("rmi://" + hostName + "/" + serviceName1, b);
            System.out.println("Attente des invocations des clients :");

            Compte c = new Compte();
            Naming.rebind("rmi://" + hostName + "/" + serviceName2, c);

        } catch (Exception e) {

            System.out.println("Erreur de liaison de l'objet Banque");
            System.out.println(e.toString());
        }
    }

    public static void startRMIRegistry() {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("Serveur : Construction de l'implementation ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
} 
