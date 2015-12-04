package texte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import texte.dictionnaire.Dictionnaire;
import texte.processing.SparseDoubleVector;

public class CorpusFromTxtFile extends CorpusHashMap {

	private HashMap<Integer, String> fullID;
	private HashMap<Integer, String> bd;
	// private HashMap<Integer, String> labels;
	public HashMap<Integer, SparseDoubleVector> bdNum;

	public CorpusFromTxtFile(HashMap<Integer, String> bd,
			HashMap<Integer, String> labels, HashMap<Integer, String> fullID) {
		super(bd.size());
		this.fullID = fullID;
		this.bd = bd;
		bdNum = new HashMap<Integer, SparseDoubleVector>(10000);
		setCategories(labels);
	}


        public CorpusFromTxtFile(ArrayList<Integer> ids, HashMap<Integer, String> bd,
			HashMap<Integer, String> labels, HashMap<Integer, String> fullID) {
                super(ids);
		this.fullID = fullID;
		this.bd = bd;
		bdNum = new HashMap<Integer, SparseDoubleVector>(10000);
		setCategories(labels);
	}

	public SparseDoubleVector getCurrentTextNum() {
		if (bdNum == null)
			return null;

		return bdNum.get(getCurrentID());

	}

	public String getCurrentText() {
		return bd.get(getCurrentID());
	}

	public String getAttribute(String attribut) {
		if (!attribut.equals("id"))
			return null;

		return fullID.get(getCurrentID());
	}

	public void addNumericalRepresentation(Dictionnaire dico) {
		int key = getCurrentID();
		bdNum.put(key, dico.map(bd.get(key)));

	}

}
