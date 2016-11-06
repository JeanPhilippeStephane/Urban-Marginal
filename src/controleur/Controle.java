package controleur;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import modele.Label;
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
			evenementChoixJoueur(info);
			
		}
		if(uneFrame instanceof Arene ){
			evenementArene(info);
		}
	}
	

	private void evenementEntreeJeu(Object info) {
		if( (String)info=="serveur"){
			new ServeurSocket(this,PORT);
			leJeu= new JeuServeur(this);
			frmEntreeJeu.dispose();
			frmArene= new Arene("serveur",this);
			((JeuServeur)leJeu).constructionMurs();
			frmArene.setVisible(true);
		}
		else{
			if((new ClientSocket( (String)info,PORT,this)).isConnexionOK()){
				leJeu=new JeuClient(this);
				leJeu.setConnection(connection);
				frmArene= new Arene("client",this);
				frmChoixJoueur=new ChoixJoueur(this);
				frmChoixJoueur.setVisible(true);
				frmEntreeJeu.dispose();
			}
		}
		
	}
	private void  evenementChoixJoueur(Object info){
		((JeuClient)leJeu).envoi(info);
		frmChoixJoueur.dispose();
		frmArene.setVisible(true);
	}
	public void setConnection(Connection connection){
		this.connection=connection;
		if(leJeu instanceof JeuServeur ){
			leJeu.setConnection(connection);
		}
	}
	
	public void receptionInfo(Connection connection,Object info){
		leJeu.reception(connection,info);
	}
	public void evenementModele(Object unJeu,String ordre,Object info){
		if (unJeu instanceof JeuServeur){
			evenementJeuServeur(ordre,info);
		}
		if (unJeu instanceof JeuClient){
			evenementJeuClient(ordre,info);
		}
	}

	private void evenementJeuClient(String ordre, Object info) {
		// TODO Auto-generated method stub
		if(ordre=="envoi panel murs"){
			frmArene.ajoutPanelMurs((JPanel)info);
		}
		if(ordre=="ajout joueur"){
			System.out.println(((Label)info).getNumLabel());
			frmArene.ajoutModifJoueur(((Label)info).getNumLabel(),((Label)info).getJLabel());
		}
		if(ordre=="remplace chat"){
			frmArene.remplaceChat((String)info);
		}
		if(ordre=="son"){
			frmArene.joueSon((int)info);
		}
	}

	private void evenementJeuServeur(String ordre, Object info) {
		if (ordre=="ajout mur"){
			frmArene.ajoutMur((JLabel)info);
		}
		if(ordre=="envoi panel murs"){
			((JeuServeur)leJeu).envoi((Connection)info,frmArene.getJpnMurs());
		}
		if(ordre=="ajout joueur"){
			frmArene.ajoutJoueur((JLabel)info);
		}
		if(ordre=="ajout phrase"){
			frmArene.ajoutChat((String)info);
			((JeuServeur)leJeu).envoi(frmArene.getTxtChat());
		}
		
	}
	private void evenementArene(Object info) {
		// TODO Auto-generated method stub
		((JeuClient)leJeu).envoi(info);
	}
	public void deconnection(Connection connection){
		leJeu.deconnection(connection);
	}
	public static void main(String[] args) {
		new Controle();
		

	}

	
}
