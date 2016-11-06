package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

public class ChoixJoueur  extends JFrame implements Global  {

	private JPanel frame;
	private JTextField textPseudo;
	private  int numPerso;
	private JLabel lblPersonnage;
	private Controle controle;
	private Son suivant;
	private Son precedent;
	private Son go;
	private Son welcome;
	private void lblPrecedent_clic(){
		if (numPerso>1 ){
			numPerso=numPerso-1;
			lblPersonnage.setIcon(new ImageIcon(PERSO+numPerso +MARCHE+"1d0"+EXTIMAGE));
			precedent.play();
		}
	}
	private void lblSuivant_clic(){
		if(numPerso%NBPERSOS!=0){
			numPerso=numPerso+1;
			lblPersonnage.setIcon(new ImageIcon(PERSO+numPerso +MARCHE+"1d0"+EXTIMAGE));
			suivant.play();
		}
		
	}
	private void lblGo_clic(){
		String texte;
		texte=textPseudo.getText();
		if(texte.equals("")){
			JOptionPane.showMessageDialog(null,"le pseudo est obligatoire");
		}
		else{
			controle.evenementVue(this, PSEUDO+SEPARE+texte+SEPARE+numPerso);
		}
		go.play();
	}
	
	private void souris_normale(){
		
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) ;
	}
	
	private void souris_doigt(){
		frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	private void affichePerso(){
		lblPersonnage.setIcon(new ImageIcon(PERSO+numPerso +MARCHE+"1d0"+EXTIMAGE));
	}
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		this.controle=controle;
		setTitle("Choix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 313);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);
		
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		lblPrecedent.setBounds(57, 146, 46, 45);
		frame.add(lblPrecedent);
		
		JLabel labelSuivant = new JLabel("");
		labelSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		labelSuivant.setBounds(288, 146, 46, 45);
		frame.add(labelSuivant);
		
		JLabel labelGo = new JLabel("");
		labelGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		labelGo.setBounds(309, 192, 68, 71);
		frame.add(labelGo);
		
		textPseudo = new JTextField();
		textPseudo.setBounds(141, 245, 125, 25);
		frame.add(textPseudo);
		textPseudo.setColumns(10);
		textPseudo.requestFocus();
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(141, 113, 123, 123);
		lblPersonnage.setIcon(new ImageIcon(FONDARENE));
		frame.add(lblPersonnage);
		
		JLabel lblEquipe = new JLabel();
		
		if((controle.getNbJoueur())%2==0){
			
			lblEquipe.setText("Equipe Rouge");
		}
		else{
			lblEquipe.setText("Equipe bleue");
		}
		lblEquipe.setBounds(161, 113, 78, 25);
		frame.add(lblEquipe);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		lblFond.setIcon(new ImageIcon(FONDCHOIX));
		frame.add(lblFond);
		
		numPerso=1;
		affichePerso();
		suivant=new Son(SONSUIVANT);
		precedent=new Son(SONPRECEDENT);
		go=new Son(SONGO);
		welcome=new Son(SONWELCOME);
		welcome.play();
	}
}
