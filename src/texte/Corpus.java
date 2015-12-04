package texte;

import texte.dictionnaire.Dictionnaire;
import texte.processing.SparseDoubleVector;

public interface Corpus {
	// ajout d'une representation pour le document courant
	public void addNumericalRepresentation(Dictionnaire dico);

	public String getAttribute(String attribut);

	public String getCategory();

	// sur le document courant -> id, txt, vecteur, category, attribut
	public int getCurrentID();

	public String getCurrentText();

	public SparseDoubleVector getCurrentTextNum();

	public void go(int i); // deplacement pas propre (mais pratique)

	public boolean hasNext(); // existe-il un suivant

	public void next(); // passer au document suivat

	public void reset(); // "rembobiner au debut"

	// taille
	public int size();
}
