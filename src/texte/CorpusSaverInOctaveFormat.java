package texte;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import texte.processing.SparseDoubleVector;

public class CorpusSaverInOctaveFormat {

	private Corpus c;
	private String filename;
	private int maxIndex;

	public CorpusSaverInOctaveFormat(String filename, Corpus c, int maxIndex) {
		super();
		this.c = c;
		this.filename = filename;
		this.maxIndex = maxIndex;
	}

	public CorpusSaverInOctaveFormat(String filename, Corpus c) {
		super();
		this.c = c;
		this.filename = filename;
		maxIndex = 1000000;// = pas de limite
	}

	public void save() {
		try {
			FileOutputStream output = new FileOutputStream(filename);
			PrintStream p = new PrintStream(output);

			p.println("# Created by JAVA");
			p.println("# name: data");
			p.println("# type: cell");
			p.println("# rows: " + Math.min(c.size(), maxIndex));
			p.println("# columns: 1");

			c.reset();
			int cpt = 0;
			while (c.hasNext()) {

				SparseDoubleVector v = c.getCurrentTextNum();

				p.println("# name: <cell-element>");
				p.println("# type: matrix");
				p.println("# rows: " + v.size());
				p.println("# columns: 2");

				v.save(p);

				if (maxIndex < cpt)
					break;
				cpt++;
				c.next();
			}

			// outputC.close();
			p.println("");
			p.println("# Created by JAVA");
			p.println("# name: id");
			p.println("# type: matrix");
			p.println("# rows: " + Math.min(c.size(), maxIndex));
			c.reset();
			boolean category = false;
			if (c.getCategory() != null) {
				p.println("# columns: 2");
				category = true;
			} else
				p.println("# columns: 1");

			HashMap<String, Integer> catNum = new HashMap<String, Integer>();

			c.reset();
			cpt = 0;
			while (c.hasNext()) {

				if (category) {
					// transforme catergoy en nombre
					Integer catAsInt = catNum.get(c.getCategory());
					if (catAsInt == null) {
						catAsInt = catNum.size();
						catNum.put(c.getCategory(), catAsInt);

						System.out
								.println("Nouvelle cat: " + c.getCategory()
										+ " " + catAsInt + "("
										+ c.getCurrentID() + ")");
					}

					p.println(c.getCurrentID() + " " + catAsInt);
				} else
					p.println(c.getCurrentID());

				if (maxIndex < cpt)
					break;
				cpt++;
				c.next();
			}

			output.close();

		} catch (IOException e) {
			System.err.println("Can't open file " + filename
					+ " for writting... Saving aborted");

		}
	}

}
