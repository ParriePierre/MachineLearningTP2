package texte.preprocessing;

/**
 * Remove all the punctuation and replace all the accented letter with non
 * accented letter from the String
 * 
 * @author guillaumes
 * 
 */
public class StringProcessor_RemovePunctuation implements StringProcessor {

	public String map(String from) {

		from = from.replaceAll("[àâäÂÄ]", "a");
		from = from.replaceAll("[éèêëÊË]", "e");
		from = from.replaceAll("[îïÎÏ]", "i");
		from = from.replaceAll("[ôöÔÖ]", "o");
		from = from.replaceAll("[ùûüÛÜ]", "u");
		from = from.replaceAll("ç", "c");
		from = from.replaceAll("[^A-Za-z0-9]", " ");
		return (from);

	}

}
