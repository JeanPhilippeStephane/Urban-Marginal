package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection extends Thread {
	private Object leRecepteur;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public Connection(Socket socket, Object leRecepteur){
		this.leRecepteur=leRecepteur;
		try {
			out= new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur de creation de canal de sortie : "+e);
			System.exit(0);
		}
		try {
			in=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur grave création du canal d'entrée : "+e);
			System.exit(0);
		}
	start();
	((controleur.Controle)this.leRecepteur).setConnection(this) ; 
	}
	
	public void envoi(Object unObjet){
		try {
			out.writeObject(unObjet);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur sur l'objet out : "+e);
		}
	}
	public void run(){
		boolean inOk=true;
		Object reception;
		while(inOk==true){
			try {
				reception=in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("erreur type classe non trouvé : "+e);
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"l'ordinateur est déconnecté");
				inOk=false;
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("erreur  la fermeture du canal s'est mal passée: "+e);
				}
				
			}
		}
	}

}
