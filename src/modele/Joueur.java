package modele;

import java.awt.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controleur.Global;
import outils.connexion.Connection;

public class Joueur extends Objet implements Global {
	private String pseudo;
	private int numPerso;
	private Label message;
	private JeuServeur jeuServeur;
	private int vie=10; // vie restante du joueur 
	private int orientation=1;// tourné vers la gauche (0) ou vers la droite (1) 
	private int etape=1;// numéro d'étape dans l'animation 
	public Joueur(JeuServeur jeuServeur){
		this.jeuServeur=jeuServeur;
	}
	public void initPerso(String pseudo,int numPerso,Hashtable<Connection,Joueur>lesJoueurs, ArrayList<Mur> lesMurs){
		this.pseudo=pseudo;
		this.numPerso=numPerso;
		Label  label=new Label(Label.getNbLabel(),new JLabel());
		Label.setNbLabel(Label.getNbLabel());
		label.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		label.getJLabel().setVerticalAlignment(SwingConstants.CENTER);
		jeuServeur.nouveauLabelJeu(label);
		message=new Label(Label.getNbLabel(),new JLabel());
		message.setNbLabel(Label.getNbLabel());
		message.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		message.getJLabel().setFont(new Font("Dialog",Font.PLAIN,8)) ;
		premierePosition(lesJoueurs,lesMurs);
		jeuServeur.nouveauLabelJeu(message);
		affiche(MARCHE,etape);
	}
	public Label getMessage(Label message){
		return message;
	}
	
	 public boolean toucheJoueur( Hashtable<Connection,Joueur> lesJoueurs){
		 Set <Connection> connections=lesJoueurs.keySet();
		 
		 for(Connection uneConnection:connections){
		 Collection <Joueur> joueurs=lesJoueurs.values();
		 
		 	for(Joueur unJoueur:joueurs){
		 		if(unJoueur.equals(this)){}
		 		else{
		 			if(toucheObjet(unJoueur)){
		 				return true;
		 			}
		 		}
		 	}
		 }
		return false;
		 
	 }

	public boolean toucheMur(ArrayList<Mur> lesMurs){
		for(Mur unMur: lesMurs){
			if(toucheObjet(unMur)){
				return true;
			}
		}
		return false;
	}
	
	public void premierePosition(Hashtable<Connection,Joueur>lesJoueurs, ArrayList<Mur> lesMurs){
		label.getJLabel().setBounds(1,1,L_PERSO,H_PERSO-H_MESSAGE);
		do{
			posX=  (int) (Math.round(Math.random()*(L_ARENE-L_PERSO)));
			posY= (int) (Math.round(Math.random()*(H_ARENE-H_PERSO)));
			
		}while(toucheJoueur(lesJoueurs) || toucheMur(lesMurs));
	}
	public void affiche(String etat,int etape){
		label.getJLabel().setBounds(posX,posY,L_PERSO,H_PERSO-H_MESSAGE);
		label.getJLabel().setIcon(new ImageIcon(PERSO+numPerso +etat+etape+"d"+orientation+EXTIMAGE));
		message.getJLabel().setBounds(posX,posY,L_PERSO+10,H_PERSO);
		label.getJLabel().add(message.getJLabel());
		message.getJLabel().setText(pseudo+":"+vie);
	}
}
