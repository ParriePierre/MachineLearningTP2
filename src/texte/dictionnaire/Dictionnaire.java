package texte.dictionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import texte.preprocessing.*;
import texte.processing.*;
import tools.Pair;

public class Dictionnaire implements Mapper<String, SparseDoubleVector> {

	public static StringProcessor standardStringProcessor() {

		StringPrecessorChain sp = new StringPrecessorChain();
		sp.add(new StringProcessor_RemoveLineAndSpace());
		sp.add(new FrenchPorterStemmer());
		sp.add(new StringProcessor_RemoveShortWords(4));
		sp.add(new StringProcessor_LowerCase());
		sp.add(new StringProcessor_RemovePunctuation());

		return sp;
	}
	private HashMap<String, Pair<Integer, Integer>> dico;

	private StringProcessor sp;

	public Dictionnaire(HashMap<String, Pair<Integer, Integer>> dico, StringProcessor sp) {
		this.dico = dico;
		this.sp = sp;
	}

	public void filterRareWord(int i) {
		ArrayList<String> toRemove = new ArrayList<String>();

		for (String str : dico.keySet()) {
			if (dico.get(str).snd < i)
				toRemove.add(str);
		}

		for (String str : toRemove)
			dico.remove(str);
	}

	public HashMap<String, Pair<Integer, Integer>> getHashMap() {
		return dico;
	}

	public int getID(String str) {
		Pair<Integer, Integer> cptid = dico.get(str);
		if (cptid == null)
			return -1;
		return cptid.fst;
	}

	public int getOcc(String str) {
		Pair<Integer, Integer> cptid = dico.get(str);
		if (cptid == null)
			return -1;
		return cptid.snd;
	}

	public int getWordCount() {
		return this.dico.size();
	}

	public SparseDoubleVector map(String f) {
		SparseDoubleVector v = new SparseDoubleVector();

		String fPP = sp.map(f);
		StringTokenizer st = new StringTokenizer(fPP);
		while (st.hasMoreTokens()) {
			String mot = st.nextToken();
			int id = getID(mot);
			if (id > -1)
				v.addOne(id);
		}

		return v;

	}
}
