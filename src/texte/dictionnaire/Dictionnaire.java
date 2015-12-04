package texte.dictionnaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import texte.Corpus;
import texte.processing.*;

import texte.preprocessing.FrenchPorterStemmer;
import texte.preprocessing.StringPrecessorChain;
import texte.preprocessing.StringProcessor;
import texte.preprocessing.StringProcessor_LowerCase;
import texte.preprocessing.StringProcessor_RemoveLineAndSpace;
import texte.preprocessing.StringProcessor_RemovePunctuation;
import texte.preprocessing.StringProcessor_RemoveShortWords;
import tools.Pair;

public class Dictionnaire implements Mapper<String, SparseDoubleVector> {

	private HashMap<String, Pair<Integer, Integer>> dico;
	private StringProcessor sp;

	public static StringProcessor standardStringProcessor() {

		StringPrecessorChain sp = new StringPrecessorChain();
		sp.add(new StringProcessor_RemoveLineAndSpace());
		sp.add(new FrenchPorterStemmer());
		sp.add(new StringProcessor_RemoveShortWords(4));
		sp.add(new StringProcessor_LowerCase());
		sp.add(new StringProcessor_RemovePunctuation());

		return sp;
	}

	public Dictionnaire(HashMap<String, Pair<Integer, Integer>> dico,
			StringProcessor sp) {
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

	public HashMap<String, Pair<Integer, Integer>> getHashMap() {
		return dico;
	}

        public int getWordCount(){
            return this.dico.size();
        }
}
