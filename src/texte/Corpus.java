package texte;

import texte.dictionnaire.Dictionnaire;
import texte.processing.SparseDoubleVector;

public interface Corpus {
	public void next(); // passer au document suivat

	public boolean hasNext(); // existe-il un suivant

	public void reset(); // "rembobiner au debut"

	// sur le document courant -> id, txt, vecteur, category, attribut
	public int getCurrentID();

	public String getCurrentText();

	public SparseDoubleVector getCurrentTextNum();

	public String getCategory();

	public String getAttribute(String attribut);

	// taille
	public int size();

	public void go(int i); // deplacement pas propre (mais pratique)

	// ajout d'une representation pour le document courant
	public void addNumericalRepresentation(Dictionnaire dico);
}
