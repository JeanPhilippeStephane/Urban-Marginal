package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import controleur.Global;
import outils.connexion.Connection;

public class Attaque extends Thread implements Global {
	private Joueur attaquant;
	private JeuServeur jeuServeur;
	private ArrayList<Mur> lesMurs;
	private Hashtable< Connection,Joueur> lesJoueurs;
	
	public Attaque(Joueur attaquant,JeuServeur jeuServeur,ArrayList<Mur> lesMurs,Hashtable< Connection,Joueur> lesJoueurs){
		this.attaquant=attaquant;
		this.jeuServeur=jeuServeur;
		this.lesMurs=lesMurs;
		 this.lesJoueurs=lesJoueurs;
		start();
	}
	public void run(){
		attaquant.affiche(MARCHE,1);
		Boule laBoule=attaquant.getBoule();
		int orientation=attaquant.getOrientation();
		laBoule.getLabel().getJLabel().setVisible(true);
		pause(10,(10/1000000));
		Joueur victime=null;
		do{
			if(orientation==GAUCHE){
				laBoule.setPosX((laBoule.getPosX())-LEPAS);
			}
			else{
				laBoule.setPosX((laBoule.getPosX())+LEPAS);
			}
			laBoule.getLabel().getJLabel().setBounds(laBoule.getPosX(),laBoule.getPosY(),L_BOULE,H_BOULE);
			jeuServeur.envoi(laBoule.getLabel());
			victime=toucheJoueur(lesJoueurs);
		}while(laBoule.getPosX()>0 && laBoule.getPosX()<L_ARENE && toucheMur()==false && victime==null);
		if(victime!=null  ){
			if(victime.estMort()==false){
				victime.perteVie();
				attaquant.gainVie();
				jeuServeur.envoi(HURT);
				for(int i=1;i<=NBETATSBLESSE;i++){
					victime.affiche(BLESSE, i);
					pause(80,(80/1000000));
				
				}
				if(victime.estMort()){
					jeuServeur.envoi(DEATH);
					for(int i=1;i<=NBETATSBLESSE;i++){
						victime.affiche(MORT, i);
						pause(80,(80/1000000));
					}
				}
				else{
					victime.affiche(MARCHE,1);
				}
				attaquant.affiche(MARCHE,1);
			
			}
		}
		laBoule.getLabel().getJLabel().setVisible(false);
		jeuServeur.envoi(laBoule.getLabel());
		
	}
	
	public void pause(long milli,int nano){
		try {
			Thread.sleep(milli,nano);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("problème pause de l'attaque."+e);
		}
	}
	private boolean toucheMur(){
		for(Mur unMur: this.lesMurs){
			if(attaquant.getBoule().toucheObjet(unMur)){
				return true;
			}
			
		}
		return false;
	}
	public Joueur toucheJoueur( Hashtable<Connection,Joueur> lesJoueurs){
		 Set <Connection> connections=lesJoueurs.keySet();
		 
		 for(Connection uneConnection:connections){
		 Collection <Joueur> joueurs=lesJoueurs.values();
		 
		 	for(Joueur unJoueur:joueurs){
		 			if(attaquant.getBoule().toucheObjet(unJoueur)){
		 				return unJoueur;
		 		}
		 	}
		 }
		return null;
		 
	 }
}
