package com.groupe5.client;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceCompte extends Remote {

	int getSolde() throws RemoteException;
	String getNom() throws RemoteException;
	void setNom(String nom) throws RemoteException;
	void setSolde(int solde) throws RemoteException;
	String getPassword() throws RemoteException;
	void setPassword(String password) throws RemoteException;
	
}
