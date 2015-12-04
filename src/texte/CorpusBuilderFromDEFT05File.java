package texte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CorpusBuilderFromDEFT05File implements CorpusBuilder {

	private String filename;
	private boolean learning;

	public CorpusBuilderFromDEFT05File(String filename, boolean learning) {
		super();
		this.filename = filename;
		this.learning = learning;
	}

	@Override
	public Corpus build() {

		HashMap<Integer, String> fullID = new HashMap<Integer, String>(10000);
		HashMap<Integer, String> bd = new HashMap<Integer, String>(10000);
		HashMap<Integer, String> label = null;
		if (learning) {
			label = new HashMap<Integer, String>(10000);
		}

		try {

			System.out.println("Loading doc ...");
			FileReader fr = new FileReader(new File(filename));
			BufferedReader in = new BufferedReader(fr);
			int cpt = 0;
			while (true) {
				String buf = in.readLine();

				if (buf == null)
					break;

				int deb = buf.indexOf('<') + 1;
				int fin = buf.indexOf(':');
				int idSpeech = Integer.parseInt(buf.substring(deb, fin));

				deb = buf.indexOf(':') + 1;
				if (learning)
					fin = buf.indexOf(':', deb);
				else
					fin = buf.indexOf('>', deb);
				int idDoc = Integer.parseInt(buf.substring(deb, fin));

				String id = idSpeech + "-" + idDoc;

				fullID.put(cpt, id);

				if (learning) {
					deb = buf.indexOf(':', deb) + 1;
					fin = buf.indexOf('>');
					String lab = buf.substring(deb, fin);
					// System.out.println(lab);

					label.put(cpt, lab);
				}

				deb = buf.indexOf('>') + 1;

				String txt = buf.substring(deb);

				bd.put(cpt, txt); // sauter le debut de ligne

				// System.out.println("loading doc :"+cpt+" "+id+" "+ " "+txt);
				cpt++;
			}

			in.close();

			return new CorpusFromTxtFile(bd, label, fullID);
		} catch (IOException e) {
			System.err.println("Can't open file " + filename + " for reading... Loading aborted");
			return null;
		} catch (NumberFormatException e) {
			System.err.println("Problem reading numbers in: " + filename);
			return null;
		} catch (Exception e) {
			System.err.println("Invalid Format : " + filename + "... Loading aborted");
			return null;
		}
	}

}
