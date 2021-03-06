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
	private int vie; // vie restante du joueur 
	private int orientation=1;// tourn� vers la gauche (0) ou vers la droite (1) 
	private int etape=1;// num�ro d'�tape dans l'animation
	private Boule boule;
	private static final int  MAXVIE = 10; // vie de d�part pour tous les joueurs
	private static final int GAIN = 1 ; // gain de points lors d'une attaque 
	private static final int PERTE = 2;  // perte de points lors d'une attaque
	private String equipe;// �quipe du joueur
	private Label camps;
	public Joueur(JeuServeur jeuServeur){
		this.jeuServeur=jeuServeur;
		vie=MAXVIE;
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
		
		camps=new Label(Label.getNbLabel(),new JLabel());
		camps.setNbLabel(Label.getNbLabel()+1);
		camps.getJLabel().setHorizontalAlignment( SwingConstants.CENTER);
		
		premierePosition(lesJoueurs,lesMurs);
		affiche(MARCHE,etape);
		jeuServeur.envoi(label);
		jeuServeur.envoi(message);
		
		jeuServeur.nouveauLabelJeu(label);
		jeuServeur.nouveauLabelJeu(message);
		jeuServeur.nouveauLabelJeu(camps);
		boule=new Boule(jeuServeur);
		jeuServeur.envoi(boule.getLabel());
		
		equipe=jeuServeur.dansLequipe(this);// renvoi l'�quipe au joueur
		jeuServeur.envoi(camps);
		System.out.println(equipe);
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
		if (equipe=="rouge"){
			camps.getJLabel().setIcon(new ImageIcon(CHEMINCAMPS+"rouge.gif"+SEPARATOR));
		}
		else{
			camps.getJLabel().setIcon(new ImageIcon(CHEMINCAMPS+"bleue.gif"+SEPARATOR));
		}
		camps.getJLabel().setBounds(posX-10,posY+H_PERSO,H_CAMPS,H_CAMPS);
		jeuServeur.envoi(label);
		jeuServeur.envoi(message);
		jeuServeur.envoi(camps);
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
			 this.posX=position;
		 }
		 else{
			 this.posY=position;
		 }
		 if(toucheJoueur(lesJoueurs)||toucheMur(lesMurs)){
			 position=ancpos;
		 }
		 etape+=1;
		 while(etape>NBETATSMARCHE){
			 etape=1;
		 }
		 
		 return position;
		 
	}
	public void action(int action,Hashtable<Connection,Joueur> lesJoueurs,ArrayList<Mur> lesMurs){
		switch(action){
			case GAUCHE:posX=deplace(action,posX,GAUCHE,-LEPAS,L_ARENE-L_PERSO,lesJoueurs,lesMurs);
			break;
			case DROITE:posX=deplace(action,posX,DROITE,LEPAS,L_ARENE-L_PERSO,lesJoueurs,lesMurs);
			
			break;
			case HAUT:posY=deplace(action,posY,orientation,-LEPAS,H_ARENE-H_PERSO-H_MESSAGE,lesJoueurs,lesMurs);
			
			break;
			case BAS:posY=deplace(action,posY,orientation,LEPAS,H_ARENE-H_PERSO-H_MESSAGE,lesJoueurs,lesMurs);
			break;
			case TIRE:
				if(boule.getLabel().getJLabel().isVisible()==false){
				jeuServeur.envoi(FIGHT);
				boule.tireBoule(this,lesMurs,lesJoueurs);
				}
				;break;
			
		}
		affiche(MARCHE,etape);
	}
	/**
	 * @return the boule
	 */
	public Boule getBoule() {
		return boule;
	}
	/**
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}
	public void gainVie(){
		vie+=GAIN;
	}
	public void perteVie(){
		vie-=PERTE;
		if(vie<=0){
			vie=0;
		}
	}
	public boolean estMort(){
		if(vie==0){
			return true;
		}
		return false;
	}
	public void departJoueur(){
			if(label!=null){
				label.getJLabel().setVisible(false);
				message.getJLabel().setVisible(false);
				boule.getLabel().getJLabel().setVisible(false);
				jeuServeur.envoi(label);
				jeuServeur.envoi(message);
				jeuServeur.envoi(boule.getLabel());
				
			}
			
	}
	public String getEquipe(){
		return equipe;
	}
	public Label getCamps(){
		return camps;
	}
	
}
