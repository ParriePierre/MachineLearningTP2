package texte.preprocessing;

import java.util.ArrayList;

public class StringPrecessorChain extends ArrayList<StringProcessor> implements
		StringProcessor {

	public StringPrecessorChain() {
		super();
	}

	public StringPrecessorChain(StringProcessor fst, StringProcessor snd) {
		super();

		add(fst);
		add(snd);
	}

	public String map(String str) {
		if (size() == 0)
			return null;

		return recursiveMap(str, size() - 1);
	}

	private String recursiveMap(String str, int i) {
		if (i > 0)
			return recursiveMap(get(i).map(str), i - 1);
		else
			return get(i).map(str);
	}

}
