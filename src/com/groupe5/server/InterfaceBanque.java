package com.groupe5.server;

import com.groupe5.client.InterfaceCompte;
import com.groupe5.ge.CompteException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface InterfaceBanque extends Remote {

    // Méthode qui sera exposer aux clients avec RMI
    // Définition du service
    Map<String, InterfaceCompte> historique() throws RemoteException;

    void creerCompte(String nom, String password) throws CompteException, RemoteException;

    InterfaceCompte verifie(String nom, String password) throws CompteException, RemoteException;

    int fermerCompte(String nom, String password) throws CompteException, RemoteException;

    void deposer(int somme, InterfaceCompte c) throws RemoteException;

    int retirer(int somme, InterfaceCompte c) throws CompteException, RemoteException;

    int balance(InterfaceCompte c) throws RemoteException;

    String getNom(String nom) throws RemoteException;
}
