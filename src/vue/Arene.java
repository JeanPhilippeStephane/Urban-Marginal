package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Arene extends JFrame implements Global {

	private JPanel frame;
	private JTextField textSaisie;
	private JPanel jpnMurs;
	private JPanel jpnJeu;
	private boolean client;
	private Controle controle;
	private JTextArea txtChat;
	private Son[] lesSons = new Son[SON.length] ; 
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Arene(String typeJeu,Controle controle) {
		this.controle=controle;
		if(typeJeu=="client"){
			this.client=true;
		}
		else{
			this.client=false;
		}
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, L_ARENE+3*MARGE, H_ARENE + H_CHAT );
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);
		if(client){
		frame.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent arg0) {
				frame_keyPressed(arg0) ;
			}

			
		});
		
		}
		 jpnJeu = new JPanel();
		jpnJeu.setOpaque(false);
		jpnJeu.setBounds(0, 0, L_ARENE, H_ARENE );
		frame.add(jpnJeu);
		jpnJeu.setLayout(null);
		
		 jpnMurs = new JPanel();
		jpnMurs.setOpaque(false);
		jpnMurs.setBounds(0, 0, L_ARENE, H_ARENE );
		frame.add(jpnMurs);
		jpnMurs.setLayout(null);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0,  L_ARENE, H_ARENE);
		lblFond.setIcon(new ImageIcon(FONDARENE));
		frame.add(lblFond);
		if(typeJeu=="client"){
			textSaisie = new JTextField();
			textSaisie.setBounds(0, H_ARENE,L_ARENE,H_SAISIE);
			frame.add(textSaisie);
			textSaisie.setColumns(10);
			textSaisie.addKeyListener(new KeyAdapter() {
				
				public void keyPressed(KeyEvent arg0) {
					txtSaisie_keyPressed(arg0) ;
				}
			});
		}
			JScrollPane jspChat = new JScrollPane();
			jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
			jspChat.setBounds(0,  H_ARENE + H_SAISIE, L_ARENE, H_CHAT - H_SAISIE - 7*MARGE );
			frame.add(jspChat);
		
		 
		
			txtChat = new JTextArea();
			jspChat.setViewportView(txtChat);
		if(client){
			(new Son(SONAMBIANCE)).playContinue() ;
			for (int i=0;i<lesSons.length;i++){
				lesSons[i]=new Son(CHEMINSONS+SON[i]);
			}
		}
		
	}
	public void ajoutMur(JLabel objet){
		jpnMurs.add(objet);
		jpnMurs.repaint();
	}
	public void ajoutPanelMurs(JPanel objet){
		jpnMurs.add(objet);
		jpnMurs.repaint();
		frame.requestFocus();
	}
	public JPanel getJpnMurs() {
		// TODO Auto-generated method stub
		return jpnMurs ;
	}
	public void ajoutJoueur(JLabel objet){
		jpnJeu.add(objet);
		jpnJeu.repaint();
	}
	public void ajoutModifJoueur(int num,JLabel unLabel){
		try {
			jpnJeu.remove(num);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
		}
		jpnJeu.add(unLabel,num);
		jpnJeu.repaint();
	}
	public void txtSaisie_keyPressed(KeyEvent arg0){
		if(arg0.getKeyCode()== KeyEvent.VK_ENTER){
			if(textSaisie !=null){
				
				controle.evenementVue (this,CHAT+SEPARE+textSaisie.getText());
				System.out.println(textSaisie.getText());
				textSaisie.setText("");
				frame.requestFocus();
			}
		}
	}
	public void ajoutChat(String unePhrase){
		System.out.println(unePhrase);
		txtChat.setText(unePhrase+"\r\n"+txtChat.getText());
	}
	/**
	 * @return the txtChat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}
	public void  remplaceChat(String txtChat){
		this.txtChat.setText(txtChat);
	}
	private void frame_keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int valeur=-1;
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_SPACE:
				valeur=TIRE;
			break;
			case KeyEvent.VK_UP:
				valeur=HAUT;
			break;
			case KeyEvent.VK_DOWN:
				valeur=BAS;
			break;
			case KeyEvent.VK_RIGHT:
				valeur=DROITE;
			break;
			case KeyEvent.VK_LEFT:
				valeur=GAUCHE;
			break;
		}
		if(valeur!=-1){
			controle.evenementVue(this,ACTION+SEPARE+valeur);
		}
		
	}
	
	public void joueSon(int numSon){
		lesSons[numSon].play();
	}
	
}
