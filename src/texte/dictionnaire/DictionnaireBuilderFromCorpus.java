package texte.dictionnaire;

import java.util.HashMap;
import java.util.StringTokenizer;

import texte.Corpus;
import texte.preprocessing.StringProcessor;
import tools.Pair;

public class DictionnaireBuilderFromCorpus {

	private Corpus c;
	private StringProcessor sp;
	private HashMap<String, Pair<Integer, Integer>> dico;

	public DictionnaireBuilderFromCorpus(Corpus c, StringProcessor sp) {
		super();
		this.c = c;
		this.sp = sp;

		dico = new HashMap<String, Pair<Integer, Integer>>(20000);

	}

	public Dictionnaire build() {

		c.reset();

		int cpt = 0;
		System.out.println("Construction du dictionnaire a partir d'un corpus");
		while (c.hasNext()) {

			String doc = c.getCurrentText();
			String docPP = sp.map(doc);

			StringTokenizer st = new StringTokenizer(docPP, " ");

			while (st.hasMoreTokens())
				addWord(st.nextToken());

			cpt++;

//			if (cpt % 1000 == 0)
//				System.out.println(cpt + " docs traités");

			c.next();

		}
		System.out.println("Construction OK");

		return new Dictionnaire(dico, sp);
	}

	private void addWord(String str) {

		Pair<Integer, Integer> idcpt = dico.get(str);
		if (idcpt == null)
			dico.put(str, new Pair<Integer, Integer>(dico.size(), 1));
		else {

			idcpt.snd = new Integer(idcpt.snd + 1);
		}

	}

}
