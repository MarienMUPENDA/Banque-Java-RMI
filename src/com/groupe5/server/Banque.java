package com.groupe5.server;

import com.groupe5.client.InterfaceCompte;
import com.groupe5.ge.CompteException;

import java.util.HashMap;
import java.util.Map;
import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author Dupeyrat Kevin
 * Cours de Réseau IUT Paris 13
 *
 * Class qui représente Compte
 *
 */


public class Banque extends UnicastRemoteObject implements InterfaceBanque {

	private Map<String, InterfaceCompte> listeComptes;


	/**
	 * Constructeur par défaut
	 */

	public Banque() throws RemoteException {
		super();

		this.listeComptes = new HashMap<String, InterfaceCompte>();

	}

	/**
	 * Méthode qui permet d'ouvrir un nouveau compte
	 * @return
	 * @throws CompteException
	 * @throws RemoteException
	 */
	@Override
	public Map<String, InterfaceCompte> historique() throws  RemoteException {
		return  listeComptes;
	}

	/**
	 *
	 * Méthode qui permet d'ouvrir un nouveau compte
	 *
	 * @param nom
	 * @param password
	 * @throws CompteException
	 */

	public void creerCompte(String nom, String password) throws CompteException, RemoteException {

		if(listeComptes.containsKey(nom))
			throw new CompteException("ERREUR : L'utilisateur a déjà un compte");

		try {

			InterfaceCompte c = (InterfaceCompte)Naming.lookup("rmi://localhost/compte");

			c.setSolde(10);
			c.setNom(nom);
			c.setPassword(password);

			listeComptes.put(nom, c);

		} catch (Exception e) {

			System.out.println("Erreur de liaison de l'objet compte");
			System.out.println(e.toString());
		}

	}


	/**
	 *
	 * Méthode qui vérifie si le compte existe
	 * et si c'est la cas, si le mot de passe est bon
	 *
	 * @param nom
	 * @param password
	 * @return
	 * @throws CompteException
	 */

	public InterfaceCompte verifie(String nom, String password) throws CompteException, RemoteException {

		if(!listeComptes.containsKey(nom))
			throw new CompteException("ERREUR : Ce compte n'existe pas");
		else
			System.out.println("LOGIN [OK]");

		if(!listeComptes.get(nom).getPassword().equals(password))
			throw new CompteException("ERREUR : Le mot de passe est incorrecte");
		else
			System.out.println("PASSWORD [OK]");

		System.out.println("VERIFICATION DU COMPTE [OK]");


		return listeComptes.get(nom);

	}

	/**
	 *
	 * Méthode qui permet de fermer un compte en com.groupe5.server.Banque
	 * et de retourner le solde de ce compte
	 *
	 * @param nom
	 * @param password
	 * @return
	 * @throws CompteException
	 */

	public int fermerCompte(String nom, String password) throws CompteException, RemoteException {

		int solde = this.verifie(nom, password).getSolde();

		this.listeComptes.remove(nom);

		return solde;
	}



	/**
	 *
	 * Méthode qui permet de mettre de l'argent sur le compte
	 *
	 * @param somme
	 * @param c
	 */

	public void deposer(int somme, InterfaceCompte c) throws RemoteException {

		c.setSolde(c.getSolde() + somme);
		// System.out.println('Vous étes dans la methodes deposer ! Solde : ' + c.getSolde());
		System.out.println("DEPOT D'ARGENT [OK]");


	}

	/**
	 *
	 * Méthode qui permet de retirer de l'argent
	 *
	 * @param somme
	 * @param c
	 * @return
	 * @throws CompteException
	 */

	public int retirer(int somme, InterfaceCompte c) throws CompteException, RemoteException {

		if(c.getSolde() < somme)
			throw new CompteException("ERREUR : Vous n'avez pas assé d'agent pour faire ce retrait.  SOLDE : " + c.getSolde());


		c.setSolde(c.getSolde() - somme);

		return somme;

	}


	/**
	 *
	 * Méthode qui permet de retourner le solde du compte
	 *
	 * @param c
	 * @return
	 */

	public int balance(InterfaceCompte c) throws RemoteException {

		return c.getSolde();
	}


	public String getNom(String nom) throws RemoteException {

		return this.listeComptes.get(nom).getNom();
	}


}
