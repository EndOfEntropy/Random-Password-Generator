/**
 * Password generator selecting characters randomly from a character list built using a combination of Strings
 */
package pwgen;

/**
 * @author Mickael Grivolat
 */

public class Characters {
	
	public static final String LOWER_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	public static final String UPPER_ALPHABET  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String DIGITS  = "0123456789";
	public static final String SPECIAL_CHARS = "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";
	private final StringBuilder charString;
	
	/** Return the character list based on user character type selection */
	public Characters(boolean lower, boolean upper, boolean digits, boolean symbols){

		if(lower == false && upper == false && digits == false && symbols == false) {
			throw new IllegalArgumentException("At least one character type must be included to create a password.");
		}
		
		charString = new StringBuilder();
		
		if(lower == true) {
			charString.append(LOWER_ALPHABET);
		}
		if(upper == true) {
			charString.append(UPPER_ALPHABET);
		}
		if(digits == true) {
			charString.append(DIGITS);
		}
		if(symbols == true) {
			charString.append(SPECIAL_CHARS);
		}
	}
	
	public String getCharacters() {
		return charString.toString();
	}
	
}
