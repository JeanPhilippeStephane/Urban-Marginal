package modele;

import java.io.Serializable;

import javax.swing.JLabel;

public class Label implements Serializable{
private static int nbLabel;
private int numLabel;
private JLabel jLabel;






public Label(int numLabel,JLabel jLabel){
	this.numLabel=numLabel;
	this.jLabel=jLabel;
}


/**
 * @return the nLabel
 */
public static int getNbLabel() {
	return nbLabel;
}
/**
 * @param numLabel the numLabel to set
 */
public static void setNbLabel(int nbLabel) {
	Label.nbLabel = nbLabel;
}
/**
 * @return the numLabel
 */
public int getNumLabel() {
	return numLabel;
}

/**
 * @return the jLabel
 */
public JLabel getJLabel() {
	return jLabel;
}


}
