package controleur;
import javax.swing.JFrame;

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
		
		
	}

	public static void main(String[] args) {
		new Controle();
		

	}

	
}
