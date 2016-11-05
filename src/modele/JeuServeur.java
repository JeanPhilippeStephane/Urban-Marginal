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
	private ArrayList<Joueur> lesJoueursDansLordre=new ArrayList<Joueur>();
	public JeuServeur(Controle controle){
		super.controle=controle;
		Label.setNbLabel(0);
	}

	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		lesJoueurs.put(connection,new Joueur(this));
		
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
			}
			lesJoueurs.get(connection).initPerso(infos[1],Integer.parseInt(infos[2]),lesJoueurs,lesMurs);
			lesJoueursDansLordre.add(this.lesJoueurs.get(connection));
			laPhrase=(this.lesJoueurs.get(connection).getPseudo());
			controle.evenementModele(this,"ajout phrase" ,"***"+laPhrase+" est arrivée *** ");
			break;
		case CHAT :
			laPhrase=(this.lesJoueurs.get(connection).getPseudo());
			controle.evenementModele(this,"ajout phrase" ,laPhrase+">"+infos[1]);
			break;
		case ACTION:
			this.lesJoueurs.get(connection).action(Integer.parseInt(infos[1]), lesJoueurs, lesMurs);
			break;
		}
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
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
	
	

}
