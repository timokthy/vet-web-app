package group825.vetapp2.animal;

/**
 * DictionaryLetter class stores the key, value pair used in a Python dictionary data structure
 * 
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 30, 2021
 */

public class DictionaryLetter {
	public char key;
	public int value;
	
	public DictionaryLetter(char key, int value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		String newString = "{ "+key +" : "+value +"}";
		return newString;
	}
	
	
}
