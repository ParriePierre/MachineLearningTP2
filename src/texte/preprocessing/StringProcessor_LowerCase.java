package texte.preprocessing;

/**
 * Transforms the String into lowercase
 * 
 * @author guillaumes
 * 
 */
public class StringProcessor_LowerCase implements StringProcessor {

	public String map(String from) {
		/**
		 * All the text is changed into lowercase
		 */
		return (from.toLowerCase());
	}
}
