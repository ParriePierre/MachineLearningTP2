package texte.dictionnaire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import tools.Pair;

public class DictionnaireSaverInOctaveFormat {
	private String filename;
	private Dictionnaire dico;

	public DictionnaireSaverInOctaveFormat(String filename, Dictionnaire dico) {
		super();
		this.filename = filename;
		this.dico = dico;
	}

	public void save() {

		try {
			FileOutputStream output = new FileOutputStream(filename);
			PrintStream p = new PrintStream(output);

			p.println("# Created by JAVA");
			p.println("# name: dico");
			p.println("# type: cell");
			p.println("# rows: " + dico.getHashMap().size());
			p.println("# columns: 1");

			for (String mot : dico.getHashMap().keySet()) {
				// Pair<Integer, Integer> idcpt = dico.get(mot);
				p.println("# name: <cell-element>");
				p.println("# type: string");
				p.println("# elements: 1");
				p.println("# length: " + mot.length());

				p.println(mot);
			}
			p.println("");
			p.println("# Created by JAVA");
			p.println("# name: idMots");
			p.println("# type: matrix");
			p.println("# rows: " + dico.getHashMap().size());
			p.println("# columns: 1");

			for (String mot : dico.getHashMap().keySet()) {
				Pair<Integer, Integer> idcpt = dico.getHashMap().get(mot);
				p.println(idcpt.fst);
			}

			output.close();
		} catch (IOException e) {
			System.err.println("Can't open file " + filename
					+ " for writting... Saving aborted");

		}

	}

}
