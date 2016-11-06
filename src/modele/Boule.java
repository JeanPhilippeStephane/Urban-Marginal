package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
import outils.connexion.Connection;

public class Boule extends Objet implements Global {
	private JeuServeur jeuServeur;
	public Boule(JeuServeur jeuServeur){
		this.jeuServeur=jeuServeur;
		label=new Label(Label.getNbLabel(),new JLabel());
		Label.setNbLabel(Label.getNbLabel()+1);
		label.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		label.getJLabel().setVerticalAlignment(SwingConstants.CENTER);
		label.getJLabel().setBounds(0,0,L_BOULE,H_BOULE);
		label.getJLabel().setIcon(new ImageIcon(BOULE));
		label.getJLabel().setVisible(false);
		jeuServeur.nouveauLabelJeu(label);
	}
	public void tireBoule(Joueur attaquant,ArrayList<Mur>lesMurs,Hashtable< Connection,Joueur> lesJoueurs){
		if(attaquant.getOrientation()==GAUCHE){
			setPosX(attaquant.posX-L_BOULE-1);
		}
		else{
			setPosX(attaquant.posX+L_PERSO+1);
		}
		setPosY(attaquant.posY+(H_PERSO/2));
		Attaque attaque=new Attaque(attaquant,jeuServeur,lesMurs,lesJoueurs);
	}
}
