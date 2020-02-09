package com.groupe5.client;

import java.rmi.*;
import java.rmi.server.*;

/**
 * Class qui représente un com.groupe5.client.Compte en banque
 *
 * @author Dupeyrat Kevin
 */


public class Compte extends UnicastRemoteObject implements InterfaceCompte {

    private String nom;
    private String password;
    private int solde;


    public Compte() throws RemoteException {

    }

    /**
     * Constructeur
     *
     * @param password
     * @param nom
     */

    public Compte(String nom, String password) throws RemoteException {
        this.setSolde(10);
        this.setNom(nom);
        this.setPassword(password);
    }


    /**
     * Constructeur
     *
     * @param solde
     * @param password
     * @param nom
     */

    public Compte(int solde, String nom, String password) throws RemoteException {
        this.setNom(nom);
        this.setSolde(solde);
        this.setPassword(password);
    }


    /*
     * =====================================================
     * = Assesceurs
     * =====================================================
     */


    public int getSolde() throws RemoteException {
        return this.solde;
    }

    public String getNom() throws RemoteException {
        return this.nom;
    }


    public void setNom(String nom) throws RemoteException {
        this.nom = nom;
    }


    public void setSolde(int solde) throws RemoteException {
        this.solde = solde;
    }

    public String getPassword() throws RemoteException {
        return this.password;
    }

    public void setPassword(String password) throws RemoteException {
        this.password = password;
    }

    @Override
    public String toString() {
        try {
            return String.format("%s, %s %s $", getNom(), getPassword(), getSolde());
        } catch (RemoteException e) {
            return "";
        }
    }
}
