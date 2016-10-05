package controleur;
import javax.swing.JFrame;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

public class Controle implements Global {
	private EntreeJeu frmEntreeJeu;
	private Jeu leJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Connection connection;
	
	public Controle()  {
		this.frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);
	}
	
	public void evenementVue(JFrame uneFrame ,Object info ){
		if ( uneFrame instanceof EntreeJeu ){
			evenementEntreeJeu(info);
			System.out.println((String)info) ;
		}
		if(uneFrame instanceof ChoixJoueur ){
			evenementChoixJoueur();
			
		}
	}
	
	private void evenementEntreeJeu(Object info) {
		if( (String)info=="serveur"){
			new ServeurSocket(this,PORT);
			leJeu= new JeuServeur(this);
			frmEntreeJeu.dispose();
			frmArene= new Arene();
			frmArene.setVisible(true);
		}
		else{
			if((new ClientSocket( (String)info,PORT,this)).isConnexionOK()){
				leJeu=new JeuClient(this);
				leJeu.setConnection(connection);
				frmArene= new Arene();
				frmChoixJoueur=new ChoixJoueur(this);
				frmChoixJoueur.setVisible(true);
				frmEntreeJeu.dispose();
			}
		}
		
	}
	private void  evenementChoixJoueur(){
		
	}
	public void setConnection(Connection connection){
		this.connection=connection;
	}

	public static void main(String[] args) {
		new Controle();
		

	}

	
}
