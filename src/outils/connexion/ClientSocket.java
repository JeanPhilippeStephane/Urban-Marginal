package outils.connexion;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	private boolean connexionOK;
	
	public ClientSocket(String ip, int port,Object leRecepteur){
		connexionOK=false;
		try {
			Socket socket= new Socket( ip,port);
			System.out.println("connexion au serveur réussi");
			connexionOK=true;
			new Connection(socket,leRecepteur);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the connexionOK
	 */
	public boolean isConnexionOK() {
		return connexionOK;
	}

	/**
	 * @param connexionOK the connexionOK to set
	 */
	public void setConnexionOK(boolean connexionOK) {
		this.connexionOK = connexionOK;
	}
	
	
}
