package modele;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

public class JeuServeur extends Jeu implements Global {
	private ArrayList<Mur> lesMurs =new ArrayList<Mur>();
	private Hashtable<Connection,Joueur> lesJoueurs=new Hashtable<Connection,Joueur>();
	private Hashtable<Connection,Joueur> lesBleus=new Hashtable<Connection,Joueur>();
	private Hashtable<Connection,Joueur> lesRouges=new Hashtable<Connection,Joueur>();
	private ArrayList<Joueur> lesJoueursDansLordre=new ArrayList<Joueur>();
	
	private  int nbJoueur=0;
	public JeuServeur(Controle controle){
		super.controle=controle;
		Label.setNbLabel(0);
	}

	
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		System.out.println("nbJoueur:"+nbJoueur);	
		lesJoueurs.put(connection,new Joueur(this));
		if((nbJoueur%2)==0){
			lesRouges.put(connection,new Joueur(this));
		}
		else{
			lesBleus.put(connection,new Joueur(this));
		}
		nbJoueur+=1;
		
	}

	@Override
	public void reception(Connection connection, Object info) {
		System.out.println(info);
		String[] infos;
		String laPhrase;
		infos=((String)info).split(SEPARE);
		
		switch(Integer.parseInt(infos[0])){
		case PSEUDO:
			controle.evenementModele(this, "envoi panel murs",connection);
			for(Joueur unJoueur:lesJoueursDansLordre){
				super.envoi(connection,unJoueur.getLabel());
				super.envoi(connection, unJoueur.getMessage());
				super.envoi(connection, unJoueur.getBoule().getLabel());
			}
			lesJoueurs.get(connection).initPerso(infos[1],Integer.parseInt(infos[2]),lesJoueurs,lesMurs);
			lesJoueursDansLordre.add(this.lesJoueurs.get(connection));
			laPhrase=(this.lesJoueurs.get(connection).getPseudo());
			controle.evenementModele(this,"ajout phrase" ,"***"+laPhrase+" est arrivée *** ");
			break;
		case CHAT :
			if(lesJoueurs.get(connection).estMort()==false){
			laPhrase=(this.lesJoueurs.get(connection).getPseudo());
			controle.evenementModele(this,"ajout phrase" ,laPhrase+">"+infos[1]);
			}
			break;
		case ACTION:
			if(lesJoueurs.get(connection).estMort()==false){
				this.lesJoueurs.get(connection).action(Integer.parseInt(infos[1]), lesJoueurs, lesMurs);
			}
			break;
		}
	}

	
	public void deconnection(Connection connection) {
		lesJoueurs.get(connection).departJoueur();
		lesJoueurs.remove(connection);
		nbJoueur-=1;
	}
	
	public void constructionMurs(){
		for(int i = 0; i<NBMURS;i++){
			lesMurs.add(new Mur());
			controle.evenementModele(this,"ajout mur",lesMurs.get(i).getLabel().getJLabel());
		}
	}
	public void nouveauLabelJeu(Label label){
		controle.evenementModele(this,"ajout joueur",label.getJLabel());
	}

	/* (non-Javadoc)
	 * @see modele.Jeu#envoi(outils.connexion.Connection, java.lang.Object)
	 */
	
	public void envoi(Object info) {
		// TODO Auto-generated method stub
		Set<Connection> lesCles=lesJoueurs.keySet();
		for(Connection uneConnection: lesCles){
			super.envoi(uneConnection, info);
		}
	}
	public int getNbJoueur(){
		return nbJoueur;
	}
	public String dansLequipe(Joueur joueur){
			Set<Connection>lesCles=lesRouges.keySet();
			Set<Connection> lesCles2=lesBleus.keySet();
			for(Connection uneCle:lesCles){
				if(lesJoueurs.get(uneCle)==joueur){
					return "rouge";
				}
			}
			for(Connection uneCle2:lesCles2){
				if(lesJoueurs.get(uneCle2)==joueur){
					return "bleu";
				}
		}
			return null;
	}

}
