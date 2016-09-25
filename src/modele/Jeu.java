package modele;

import controleur.Controle;
import outils.connexion.Connection;

public abstract class Jeu {
	protected Controle controle;
	public abstract void setConnection(Connection connection);
	public abstract void reception(Connection connection);
	public void envoi(Connection connection){
		connection.envoi(info);
	}
}
