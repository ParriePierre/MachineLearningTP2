package texte.preprocessing;

import net.sf.snowball.ext.frenchStemmer;

public class FrenchPorterStemmer implements StringProcessor {

	protected frenchStemmer stemmer;

	public FrenchPorterStemmer() {

		stemmer = new frenchStemmer();

	}

	@Override
	public String map(String from) {
		StringBuffer sb = new StringBuffer();

		String[] result = from.split("[^a-zA-Z0-9]+");

		for (int i = 0; i < result.length; i++)

		{

			if ((result[i].length() < 50) || (result[i].length() > 2))

			{

				stemmer.setCurrent(result[i]);

				stemmer.stem();

				sb.append(stemmer.getCurrent());

				sb.append(" ");

			}

			else

				sb.append(result[i]);

			sb.append(" ");

		}

		return (sb.toString());

	}

}
