package texte.dictionnaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import tools.Pair;

public class DictionnaireBuilderFromFile {
	private String filename;

	public DictionnaireBuilderFromFile(String filename) {
		super();
		this.filename = filename;
	}

	public Dictionnaire build() {
		try {
			FileReader fr = new FileReader(new File(filename));

			BufferedReader in = new BufferedReader(fr);

			Dictionnaire dico = load(in);
			in.close();

			return dico;
		}catch (FileNotFoundException e) {
			System.err.println("Unable to find "+ filename);
		}
		catch (IOException e) {
			System.err.println("Can't open file " + filename
					+ " for reading... Loading aborted");

		} catch (Exception e) {
			System.err.println("Invalid Format : " + filename
					+ "... Loading aborted");

		}

		return null;
	}

	public Dictionnaire load(BufferedReader in) {
		HashMap<String, Pair<Integer, Integer>> dico = new HashMap<String, Pair<Integer, Integer>>(
				20000); // RAZ
		try {
			while (true) {

				String buf;

				buf = in.readLine();

				if (buf == null)
					break;
				StringTokenizer st = new StringTokenizer(buf);
				dico.put(st.nextToken(), new Pair<Integer, Integer>(Integer
						.parseInt(st.nextToken()), Integer.parseInt(st
						.nextToken())));
			}

			return new Dictionnaire(dico, null);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
