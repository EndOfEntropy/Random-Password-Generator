/**
 * Random Password Generator and password scoring system
 * User can type in a password or generate a random password
 * 
 * Password requirements:
 * character type = lower or upper case letters, digits and specials characters
 * length
 * 
 * Includes scoring system and password complexity features
 * 
 */
package pwgen;

import java.util.Random;

/**
 * @author Mickael Grivolat
 */

public class Password implements IPasswordScoringModel{

	private Characters chars;
	private int length;
	private String password;
	
	public Password(String pw) {
		if(pw.length() < 0) {
			throw new IllegalArgumentException("Password length must be positive.");
		}
		length = pw.length();
		chars = null;
		password = pw;
	}
	
	public Password(int len, boolean lower, boolean upper, boolean digits, boolean symbols) {
		if(len < 0) {
			throw new IllegalArgumentException("Password length must be positive.");
		}
		length = len;
		chars = new Characters(lower, upper, digits, symbols);
		password = generatePassword();
	}
	
	// get methods for member variables
	public int getLength() {
		return length;
	}
	public String getPassword() {
		return password;
	}
	public Characters getCharacters() {
		return chars;
	}
	
	// method generating a random password based on length.
	// what if we want to overload the method with a seed parameter? Interface?
	private String generatePassword() {
		
		int uBound = chars.getCharacters().length();
		
		StringBuilder pw = new StringBuilder();
		Random rand = new Random();

		for(int i = 0; i < length; i++) {
			int randNb = rand.nextInt(0, uBound);
			pw.append(chars.getCharacters().charAt(randNb));
		}
		
		return pw.toString();
	}
	
	// Return the level of complexity associated with a password.
	@Override
	public String setComplexity(int score) {
		if(score <= 0) {
			return "Too Short";
		}
		if(score < 20) {
			return "Very Weak";
		}
		if(score < 40) {
			return "Weak";
		}
		if(score < 60) {
			return "Good";
		}
		if(score < 80) {
			return "Strong";
		}
		return "Very Strong";
	}
	// Sum of all additive scores. Score checked
	@Override
	public int additionScore(String pw) {
		return numberOfCharactersScore(pw) + lowerCaseLettersScore(pw) + upperCaseLettersScore(pw) + digitsScore(pw) +
		specialCharactersScore(pw) + middleDigitsOrSpecialsScore(pw) + requirementsScore(pw);
	}
	
	// Sum of all deductive scores. Score checked
	@Override
	public int deductionScore(String pw) {
		return lettersOnlyScore(pw) + digitsOnlyScore(pw) + consecutiveLowerCaseLettersScore(pw) + consecutiveUpperCaseLettersScore(pw) +
				consecutiveDigitsScore(pw);
	}
	// Sum of all points. Returns the total score. Score checked
	@Override
	public int calculateScore(String pw) {
		return additionScore(pw) - deductionScore(pw);
	}
	
	// Character count methods
	// Counts the total number of lower case letters
	private int numberOfLowerCaseLetters(String pw) {
		int count = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isLowerCase(c)) {
				count++;
			}
		}
		
		return count;
	}
	// Counts the total number of upper case letters
	private int numberOfUpperCaseLetters(String pw) {
		int count = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isUpperCase(c)) {
				count++;
			}
		}
		
		return count;
	}
	// Counts the total number of digits
	private int numberOfDigits(String pw) {
		int count = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isDigit(c)) {
				count++;
			}
		}
		
		return count;
	}
	// Counts the total number of special characters
	private int numberOfSpecialCharacters(String pw) {
		int count = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Characters.SPECIAL_CHARS.contains(String.valueOf(c))) {
				count++;
			}
		}
		
		return count;
	}

	
	// Score ADDITIONS
	// Flat. Score for the number of characters. Score checked
	private int numberOfCharactersScore(String pw) {
		return pw.length() * 4;
	}
	// Cond/Incr. Score for the lower case letters. Score checked
	private int lowerCaseLettersScore(String pw) {
		int count = numberOfLowerCaseLetters(pw);
		return count > 0 ? (pw.length() - count) * 2 : 0;
	}
	// Cond/Incr. Score for the upper case letters. Score checked
	private int upperCaseLettersScore(String pw) {
		int count = numberOfUpperCaseLetters(pw);
		return count > 0 ? (pw.length() - count) * 2 : 0;
	}
	// Cond. Score for the digits. Score checked
	private int digitsScore(String pw) {
		int count = numberOfDigits(pw);
		return count < pw.length() ? count * 4 : 0;
	}
	// Flat. Score for the number of special characters. Score checked
	private int specialCharactersScore(String pw) {
		return numberOfSpecialCharacters(pw) * 6;
	}
	// Flat. Score for middle digits or special characters. Score checked
	private int middleDigitsOrSpecialsScore(String pw) {
		if(pw.length() <= 2) {
			return 0;
		}
		String pwSubStr = pw.substring(1, pw.length()-1);
		return (numberOfDigits(pwSubStr) + numberOfSpecialCharacters(pwSubStr)) * 2;
	}
	// Flat. Score for requirements (5 requirements in total, points start at 3). Score checked
	private int requirementsScore(String pw) {
		int count = 0;
		
		if(pw.length() >= 8){
			count++;
		}
		if(numberOfLowerCaseLetters(pw) > 0){
			count++;
		}
		if(numberOfUpperCaseLetters(pw) > 0){
			count++;
		}
		if(numberOfDigits(pw) > 0){
			count++;
		}
		if(numberOfSpecialCharacters(pw) > 0){
			count++;
		}
		
		return count >= 3 ? count * 2 : 0;
	}
	
	// Score DEDUCTIONS
	// Flat. Score if letters only. Score checked
	private int lettersOnlyScore(String pw) {
		if(pw.length() == numberOfLowerCaseLetters(pw) + numberOfUpperCaseLetters(pw)) {
			return pw.length();
		}
		return 0;
	}
	// Flat. Score if digits only. Score checked
	private int digitsOnlyScore(String pw) {
		if(pw.length() == numberOfDigits(pw)) {
			return pw.length();
		}
		return 0;
	}
	// Flat. Score for consecutive lower case letters. Score checked
	private int consecutiveLowerCaseLettersScore(String pw) {
		int count = 0, total = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isLowerCase(c)) {
				if(count > 0) {
					total++;
				}
				count++;
			} else {
				count = 0;
			}
		}
		
		return total * 2;
	}
	// Flat. Score for consecutive upper case letters. Score checked
	private int consecutiveUpperCaseLettersScore(String pw) {
		int count = 0, total = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isUpperCase(c)) {
				if(count > 0) {
					total++;
				}
				count++;
			} else {
				count = 0;
			}
		}
		
		return total * 2;
	}
	// Flat. Score for consecutive digits. Score checked
	private int consecutiveDigitsScore(String pw) {
		int count = 0, total = 0;
		for(int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if(Character.isDigit(c)) {
				if(count > 0) {
					total++;
				}
				count++;
			} else {
				count = 0;
			}
		}
		
		return total * 2;
	}
	
	public void tester() {
		System.out.println("Score: " + setComplexity(calculateScore("lnrqnajpygds")));
		System.out.println("Score: " + setComplexity(calculateScore("UDFBQYBSROJW")));
		System.out.println("Score: " + setComplexity(calculateScore("489276239178")));
		System.out.println("Score: " + setComplexity(calculateScore(")&&#\">#)!'($")));
		System.out.println("Score: " + setComplexity(calculateScore("{_x+lR-ue|/P")));
		System.out.println("Score: " + setComplexity(calculateScore("x<lO>W]z84'Y")));
		System.out.println("Score: " + setComplexity(calculateScore("W#5IZ@!Qe$aR")));
		System.out.println("Score: " + setComplexity(calculateScore("=f|,Rk)PYi@q")));
	}
	
	public static void main(String[] args) {
		Password pw = new Password(12, true, true, true, true); //lower/upper/digit/specials?
		System.out.println("Password length is: " + pw.getPassword().length() + ", pw is: "+ pw.getPassword());
		pw.tester();
	}


	
}
