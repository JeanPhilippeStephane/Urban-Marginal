package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

public class JeuClient extends Jeu {
	private Connection connection;
	public JeuClient(Controle controle){
		super.controle=controle;
	}
	
	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		this.connection=connection;
	}

	@Override
	public void reception(Connection connection, Object info) {
		// TODO Auto-generated method stub
		if(info instanceof JPanel){
			controle.evenementModele(this,"envoi panel murs",info);
		}
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}
	public void envoi(Object info){
		super.envoi(connection,info);
	}

}
