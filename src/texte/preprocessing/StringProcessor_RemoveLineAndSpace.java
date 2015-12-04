package texte.preprocessing;

/**
 * Removes from the String all space and line when there is more than 2
 * successively
 * 
 * @author guillaumes
 * 
 */
public class StringProcessor_RemoveLineAndSpace implements StringProcessor {

	@Override
	public String map(String from) {

		/**
		 * Remove space and line when there is more than 2 successively
		 */
		from = from.replaceAll("&nbsp;", " ");
		from = from.replaceAll("\\s+", " ");

		return (from);
	}

}
