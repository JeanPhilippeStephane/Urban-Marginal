package outils.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurSocket extends Thread {
	private Object leRecepteur;
	private ServerSocket serveurSocket;
	public ServeurSocket(Object leRecepteur, int port){
		try {
			serveurSocket=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur grave création socket serveur : "+e);
			System.exit(0);
		}
		this.leRecepteur= leRecepteur;
		start();
	}
	public void run(){
		Socket socket;
		while(true){
			try {
				socket=serveurSocket.accept();
				 System.out.println("le serveur attend");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("erreur grave gestion socket :"+e);
				System.exit(0);
			}
		}
	}
}
