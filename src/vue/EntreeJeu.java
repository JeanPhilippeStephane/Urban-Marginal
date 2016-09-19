package vue;

import java.awt.EventQueue;

import controleur.Controle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter; //Indispensable
import java.awt.event.MouseEvent;

public class EntreeJeu extends JFrame {

	private JPanel contentPane;
	private JLabel lblSelectAServer;
	private JLabel lblConnectAnExisting;
	private JLabel lblIpServer;
	private JTextField txtIp;
	private Controle controle;

	/**
	* Lancement du serveur en cliquant sur Start
	*/
	private void btnStart_clic() {
		 controle.evenementVue( this,"serveur") ;
	}
	
	private void btnConnect(){
		controle.evenementVue(this,txtIp.getText());
	}
	
	private void btnExit_clic() {
		System.exit(0);
	}
	
	/**
	 * Chargement de l'application
	 */
	

	/**
	 * Création de la fenêtre
	 * @param controle 
	 */
	public EntreeJeu(Controle controle) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/**
		 * Création des boutons, Label, TextField
		 */
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Lancement du serveur en cliquant sur start
			 */
			public void mouseClicked(MouseEvent e) {
				btnStart_clic();
			}
		});
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnStart.setBounds(279, 75, 89, 23); //Bounds : Coordonnée d'un élément (x, y, width, height)
		contentPane.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnConnect();
			}
		});
		btnConnect.setBounds(279, 123, 89, 23);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Fermeture de la fenêtre
			 */
			public void mouseClicked(MouseEvent e) {
				btnExit_clic();
			}
		});
		btnExit.setBounds(279, 158, 89, 23);
		contentPane.add(btnExit);
		
		lblSelectAServer = new JLabel("Select a server :");
		lblSelectAServer.setBounds(101, 79, 141, 14);
		contentPane.add(lblSelectAServer);
		
		lblConnectAnExisting = new JLabel("Connect an existing server :");
		lblConnectAnExisting.setBounds(101, 104, 194, 14);
		contentPane.add(lblConnectAnExisting);
		
		lblIpServer = new JLabel("IP server :");
		lblIpServer.setForeground(Color.BLACK);
		lblIpServer.setBounds(101, 129, 86, 14);
		contentPane.add(lblIpServer);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(173, 126, 86, 20);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		this.controle=controle;
	}
}

