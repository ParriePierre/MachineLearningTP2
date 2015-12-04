package texte.dictionnaire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import tools.Pair;

public class DictionnaireSaver {

	private String filename;
	private Dictionnaire dico;

	public DictionnaireSaver(String filename, Dictionnaire dico) {
		super();
		this.filename = filename;
		this.dico = dico;
	}

	private void save(PrintStream p) {
		for (String mot : dico.getHashMap().keySet()) {
			Pair<Integer, Integer> idcpt = dico.getHashMap().get(mot);
			p.println(mot + " " + idcpt.fst + " " + idcpt.snd);
		}
	}

	public void save() {
		try {
			FileOutputStream output = new FileOutputStream(filename);
			PrintStream p = new PrintStream(output);
			save(p);

			output.close();
		} catch (IOException e) {
			System.err.println("Can't open file " + filename
					+ " for writting... Saving aborted");

		}
	}

}
