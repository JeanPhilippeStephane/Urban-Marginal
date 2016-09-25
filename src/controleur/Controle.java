package controleur;
import javax.swing.JFrame;

import outils.connexion.ClientSocket;
import outils.connexion.ServeurSocket;
import vue.EntreeJeu;

public class Controle {
	private EntreeJeu frmEntreeJeu;
	
	public Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);
	}
	
	public void evenementVue(JFrame uneFrame ,Object info ){
		if ( uneFrame instanceof EntreeJeu ){
			evenementEntreeJeu(info);
			System.out.println((String)info) ;
		}
	}
	
	private void evenementEntreeJeu(Object info) {
		if( (String)info=="serveur"){
			new ServeurSocket(this,6666);
		}
		else{
			if((new ClientSocket( (String)info,6666,this)).isConnexionOK()){
				
			}
		}
		
	}

	public static void main(String[] args) {
		new Controle();
		

	}

	
}
