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
		label=new Label(Label.getNbLabel(),new JLabel());
		Label.setNbLabel(Label.getNbLabel()+1);
		label.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		label.getJLabel().setVerticalAlignment(SwingConstants.CENTER);
		
		message=new Label(Label.getNbLabel(),new JLabel());
		message.setNbLabel(Label.getNbLabel()+1);
		message.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		message.getJLabel().setFont(new Font("Dialog",Font.PLAIN,8)) ;
		premierePosition(lesJoueurs,lesMurs);
		affiche(MARCHE,etape);
		jeuServeur.envoi(label);
		jeuServeur.envoi(message);
		jeuServeur.nouveauLabelJeu(label);
		jeuServeur.nouveauLabelJeu(message);
		
	}
	public Label getMessage(){
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
		label.getJLabel().setBounds(0,0,L_PERSO,H_PERSO);
		do{
			posX=  (int) (Math.round(Math.random()*(L_ARENE-L_PERSO)));
			posY= (int) (Math.round(Math.random()*(H_ARENE-H_PERSO)));
			
		}while((toucheJoueur(lesJoueurs))||(toucheMur(lesMurs)) );
	}
	public void affiche(String etat,int etape){
		
		label.getJLabel().setBounds(posX,posY,L_PERSO,H_PERSO-H_MESSAGE);
		label.getJLabel().setIcon(new ImageIcon(PERSO+numPerso +etat+etape+"d"+orientation+EXTIMAGE));
		message.getJLabel().setBounds(posX-10,posY+H_PERSO,L_PERSO+10,H_MESSAGE);
		message.getJLabel().setText(pseudo+":"+vie);
		
	}
	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	public int deplace(int action,int position,int orientation,int lepas
	,int max,Hashtable<Connection,Joueur> lesJoueurs,ArrayList<Mur> lesMurs){
		this.orientation=orientation;
		 int ancpos=position;
		 position+=lepas;
		 if(position<0){
			 position=0;
		 }
		 if(position>max){
			 position=max;
		 }
		 if(action==GAUCHE || action==DROITE){
			 position=this.posX;
		 }
		 else{
			 position=this.posY;
		 }
		 if(toucheJoueur(lesJoueurs)||toucheMur(lesMurs)){
			 position=ancpos;
		 }
		 etape++;
		 if(etape>NBETATSMARCHE){
			 etape=1;
		 }
		 return position;
		 
	}
	public void action(int action,Hashtable<Connection,Joueur> lesJoueurs,ArrayList<Mur> lesMurs){
		switch(action){
			case GAUCHE:deplace(action,posX,GAUCHE,-LEPAS,L_ARENE-L_PERSO,lesJoueurs,lesMurs);
			jeuServeur.envoi(lesJoueurs);
			jeuServeur.envoi(lesMurs);
			break;
			case DROITE:deplace(action,posX,DROITE,LEPAS,L_ARENE-L_PERSO,lesJoueurs,lesMurs);
			jeuServeur.envoi(lesJoueurs);
			jeuServeur.envoi(lesMurs);
			break;
			case HAUT:deplace(action,posY,orientation,-LEPAS,H_ARENE-H_PERSO,lesJoueurs,lesMurs);
			jeuServeur.envoi(lesJoueurs);
			jeuServeur.envoi(lesMurs);
			break;
			case BAS:deplace(action,posY,orientation,LEPAS,H_ARENE-H_PERSO,lesJoueurs,lesMurs);
			jeuServeur.envoi(lesJoueurs);
			jeuServeur.envoi(lesMurs);
			break;
			case TIRE:;break;
			
		}
		affiche(MARCHE,etape);
	}
}
