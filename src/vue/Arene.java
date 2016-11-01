package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Global;

import javax.swing.JLabel;
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
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Arene() {
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, L_ARENE+3*MARGE, H_ARENE + H_CHAT );
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);
		
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
		
		textSaisie = new JTextField();
		textSaisie.setBounds(0, H_ARENE,L_ARENE,H_SAISIE);
		frame.add(textSaisie);
		textSaisie.setColumns(10);
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		jspChat.setBounds(0,  H_ARENE + H_SAISIE, L_ARENE, H_CHAT - H_SAISIE - 7*MARGE );
		frame.add(jspChat);
		
		 
		
		JTextArea txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);
		
		
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
	}
}
