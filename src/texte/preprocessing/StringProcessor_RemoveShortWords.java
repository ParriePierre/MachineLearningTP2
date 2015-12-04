package texte.preprocessing;

/**
 * Remove from the String all the words shorter than the choosen size
 * 
 * @author guillaumes
 * 
 */

public class StringProcessor_RemoveShortWords implements StringProcessor {

	private int size;

	/**
	 * 
	 * @param size
	 *            : the minimum size of words
	 */
	public StringProcessor_RemoveShortWords(int size) {
		this.size = size;
	}

	@Override
	public String map(String from) {
		StringBuffer sb = new StringBuffer();
		String[] result = from.split("[^a-zA-Z0-9]+");

		for (int i = 0; i < result.length; i++) {
			if (result[i].length() >= size) {
				sb.append(result[i]);
				sb.append(" ");
			}
		}
		return (sb.toString());
	}

}
