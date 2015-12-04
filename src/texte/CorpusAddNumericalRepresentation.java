package texte;

import texte.dictionnaire.Dictionnaire;

public class CorpusAddNumericalRepresentation {
	private Corpus c;
	private Dictionnaire dico;

	public CorpusAddNumericalRepresentation(Corpus c, Dictionnaire dico) {
		super();
		this.c = c;
		this.dico = dico;
	}

	public void build() {

		int cpt = 0;
		System.out.println("Calcul des representations numeriques");

		c.reset();
		while (c.hasNext()) {

			c.addNumericalRepresentation(dico);
			c.next();

			// if (cpt % 1000 == 0)
			// System.out.println(cpt + " docs traites");
			cpt++;
		}
		System.out.println("calcul OK");
	}
}
