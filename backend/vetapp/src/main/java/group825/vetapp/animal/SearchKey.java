package group825.vetapp2.animal;

import java.util.ArrayList;

/**
 * SearchKey class holds the static methods for generating search keys.
 * A searchKey is a string where each part is delimited with "-" and each part is comprised of a letter of the alphabet and 
 * 	an integer representing how often that letter appears in the name
 * 
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 30, 2021
 */
public class SearchKey {
	
	/** Create a search key based on an animals name
	 * @param animalName = name of an animal
	 * @return searchKey which is a string where each part is delimited with "-" and each part is comprised of a letter of the alphabet and 
	 * 	an integer representing how often that letter appears in the name
	 */
	public static String generateSearchKey(String animalName) {
		//create a dictionary for all the letters in the alphabet
		ArrayList<DictionaryLetter> dict = createLetterDictionary();
		
		//update dictionary to count number of each letter in the animalName
		for (int idx=0; idx<animalName.length(); idx++) {
			char letter = animalName.toLowerCase().charAt(idx);
//			System.out.println(letter);
			dict.get((int)letter-(int)'a').value ++;
		}
		
		//create a search key consisting of the letter, the number of times the letter appears in the name, and delimiter '-'
		String newSearchKey = "";
		for (int idx=0; idx<26; idx++) {
			char letter = (char)('a'+idx);
			newSearchKey =newSearchKey + String.valueOf(letter) +  dict.get((int)letter-(int)'a').value + "-";
		}
		newSearchKey = newSearchKey.substring(0, newSearchKey.length()-1);
		System.out.print("newSearchKey: ");
		System.out.println(newSearchKey);
		return newSearchKey;
	}
	

	
	/**
	 * Create a dictionary data structure where all the values are zero for each key which is a letter of the alphabet
	 * @return dictionary data structure for a blank name so all letter keys have a value of zero 
	 */
	public static ArrayList<DictionaryLetter> createLetterDictionary(){
		ArrayList<DictionaryLetter> dict = new ArrayList<DictionaryLetter>();
		for (int idx=0; idx<26; idx++) {
//			System.out.println((char)('a'+idx));
			dict.add(new DictionaryLetter((char)('a'+idx), 0));
		}
		return dict;
	}
}
