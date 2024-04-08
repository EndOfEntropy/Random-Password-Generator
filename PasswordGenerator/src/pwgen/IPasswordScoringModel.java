/**
 * Password scoring interface, calculating the score and complexity of a password
 * Inspired by: https://www.uic.edu/apps/strong-password/
 */
package pwgen;

/**
 * @author Mickael Grivolat
 */

public interface IPasswordScoringModel {
	/** Return the level of complexity associated with a password.
	 * @param score The calculated score
	 * @return a string representing the level of complexity
	 **/
	public String setComplexity(int score);
	
	/** Return the points added to the score of a password */
	public int additionScore(String pw);
	
	/** Return the points deducted to the score of a password */
	public int deductionScore(String pw);
	
	/** Return the total score of a password */
	public int calculateScore(String pw);

}
