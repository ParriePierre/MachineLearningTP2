package texte.preprocessing;

/**
 * Removes the digits from the String
 * 
 * @author guillaumes
 * 
 */
public class StringProcessor_RemoveDigit implements StringProcessor {

	@Override
	public String map(String from) {
		/**
		 * All the text is changed into lowercase
		 */
		return (from.replaceAll("\\d+", " "));
	}
}
