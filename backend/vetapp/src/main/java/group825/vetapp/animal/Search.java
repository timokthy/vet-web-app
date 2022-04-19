package group825.vetapp2.animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import group825.vetapp2.database.*;

import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * Class holds the methods to query the database and process the results to return search results.
 * @author Jimmy Zhuj
 * @version 2.0
 * @since December 13, 2021
 */
public class Search {

	/**
	 * List to store all the species currently on the database 
	 */
	ArrayList<String> listSpecies;
	
	/**
	 * String data members to hold the database query and the database table name
	 */
	String query, tableName;
	
	
	/**
	 * Number of results that should be returned
	 */
	int desiredNumberOfResults;
	
	/**
	 * Connector to the database
	 */
	Connection dao2;

	
	
	/**
	 * Constructor initializes database connection, table name, and list of species
	 * @param desiredNumberOfResults = number of results to return from seach
	 */
	public Search(int desiredNumberOfResults) {
		this.dao2 = DatabaseConnection.getConnection();
		this.tableName = "ANIMAL";
		this.desiredNumberOfResults = desiredNumberOfResults;
		this.listSpecies = new ArrayList<String>();
		
		//update the list of species to match what is on the database
		get_all_species();
		
	}
	
	/**
	 * Get all animal species currently on the database
	 * @throws Exception
	 */
	private void get_all_species(){		
		// Execute SQL query to retrieve all animals
		try {
			PreparedStatement statement = this.dao2.prepareStatement("SELECT * FROM "+this.tableName);
			ResultSet results = statement.executeQuery();
			
			while (results.next()) {
				this.listSpecies.add(results.getString("Species").toLowerCase());
			}
			Set uniqueSpecies = new HashSet(listSpecies);
			listSpecies = new ArrayList(uniqueSpecies);
			statement.close();
//			System.out.println("listSpecies: " + listSpecies);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error gettting the species currently on database");
		}
			
	}
	
	
	/**
	 * Can be used by Administrator who runs the system to update the List of Species recorded inside the database
	 * @throws SQLException
	 */
	public void updateListOfSpecies() throws Exception { 
		get_all_species();
	}
	
	
	/**
	 * Searches database for results that match the name exactly or returns results with similar names
	 * @param animalName = search name entered by user
	 * @param searchSpecies = species name that the user may or may not enter
	 * @param onlyAvailableAnimals = boolean to determine whether to only return results where the animal is available
	 * @return
	 */
	public ArrayList<Animal> searchForName(String animalName, boolean onlyAvailableAnimals) { //sample: "Bobby horse" or "horse Bobby"
		String searchSpecies = null;
		//check if user entered a species beside the name
		
		//only handle inputs of two words where one might be species type
		if (animalName.split(" ").length <= 2) {
			boolean breakOuter = false;
			int idx_animalSearchName = 1;
			for (String searchWord: animalName.split(" ")) {
				for (String species: this.listSpecies) {
					if (species.equals(searchWord.toLowerCase())) {
						searchSpecies = searchWord.toLowerCase();
						animalName = animalName.split(" ")[idx_animalSearchName];
						breakOuter = true;
						break;
					}
				}
				idx_animalSearchName--;
				if (breakOuter) {break;}
			}
		}
		
		
		
		
		//PART ONE - EXACT NAME MATCHES --------------------------------------------------------------------------------------------
		String extra ="";
		if (onlyAvailableAnimals) {extra = " AND A.Availability_Status='Available'";}
		
		
		if(searchSpecies != null) {
			query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Animal_Name=?  AND A.Species=? " + extra + " ORDER BY A.Animal_ID;";
		}
		else {
			query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Animal_Name=?" + extra + " ORDER BY A.Animal_ID;";
		}
//		System.out.println("query = "+query);
		
		try {
			PreparedStatement statement = this.dao2.prepareStatement(query);
			statement.setString(1, animalName);
			if(searchSpecies != null) {statement.setString(2, searchSpecies);}
			
			ResultSet results = statement.executeQuery();
			
			ArrayList<Animal> exactMatches = new ArrayList<Animal>();
			while (results.next()) {
				exactMatches.add(new Animal(results.getInt("Animal_ID"), results.getString("Animal_Name"),
						results.getString("Species"), results.getString("Breed"), results.getInt("Tattoo_Num"),
						results.getString("City_Tattoo"), LocalDate.parse(results.getString("Birth_Date")),
						results.getString("Sex").charAt(0), results.getString("RFID"), results.getString("Microchip"),
						results.getString("Health_Status"), results.getBoolean("Availability_Status"),
						results.getString("Colour"), results.getString("Additional_Info"), 
						results.getInt("Length_Name"), results.getString("SearchKey_Name")));
			}
			
//			if (exactMatches.size() > 0) {
//				return exactMatches;
//			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error searching for exact match");

		}
	
		
		//PART TWO - SEARCH FOR SIMILAR NAME MATCHES ------------------------------------------------------------------------------------------
//		System.out.println("\t>>> Did not get exact matches from query\n");
//		System.out.println("\n\n >>> Proceed to part two to search for names that are similar");

		
		//Get an arraylist of Animals whose names are of similar length to the entered name
		ArrayList<Animal> minMatches =  getMinNumOfResults((searchSpecies != null), searchSpecies, animalName);
		
		//if nothing was found at all, return blank ArrayList
		if(minMatches.size() == 0) {return new ArrayList<Animal>();}
		
		//Otherwise, organize findings based on most similar search key
//		System.out.println("allFindingsSearchKeys:");
		ArrayList<String> allFindingsSearchKeys = new ArrayList<String>();
		for (Animal currAnimal: minMatches){ 
			allFindingsSearchKeys.add(currAnimal.getSearchKeyName());
		}

//		System.out.println("animalName = '" + animalName+"'");
		String newSearchKey = generateSearchKey(animalName);
		ArrayList<Integer> list_differences = new ArrayList<Integer>();
		for (String dbSearchKey: allFindingsSearchKeys) {
			list_differences.add(calcDifference(newSearchKey, dbSearchKey));
		}	
//		System.out.println("\nlist_differences = "+list_differences);

		
		// PART THREE - organize Database results based on closest similarity-----------------------------------------------------------------------
//		System.out.println("\n\n >>> Proceed to part three to return the results based on names which are the most similar");
		int numResultsReturned = 0;
		ArrayList<Animal> organizedResults = new ArrayList<Animal>();
		
		while(numResultsReturned < this.desiredNumberOfResults && list_differences.size()!=0) {
			int idxMin = getIndexOfMin(list_differences);
			organizedResults.add(minMatches.get(idxMin));
			minMatches.remove(idxMin);
			list_differences.remove(idxMin);
			numResultsReturned++;
		}		
		
//		System.out.println("organizedResults: ");
//		for(Animal currAnimal: organizedResults) {
//			System.out.println("\t -> "+currAnimal.getName());
//		}
//		System.out.println("remaining of allResultsFromDb: ");
//		for(Animal currAnimal:  minMatches) {
//			System.out.println("\t -> "+currAnimal.getName());
//		}
//		System.out.println(" ----");
		
		return organizedResults;
		
	}
	
	/**
	 * Get the index of the element in arraylist which holds the lowest value in the arraylist.
	 * This is used to identify the position of the result in the arraylist whose name is most similar to the search query's animal name 
	 * @param list_differences
	 * @return
	 */
	private int getIndexOfMin(ArrayList<Integer> list_differences) {
		int minIdx =0;
		Integer minValue =  list_differences.get(minIdx);
		for (int idx=0; idx<list_differences.size(); idx++) {
			if ( list_differences.get(idx) < minValue) {
				minValue = list_differences.get(idx);
				minIdx = idx;
			}
		}
//		System.out.println("\t ==> Min value found was: "+minValue);
		return minIdx;
	}
	
	/**
	 * Get an an ArrayList of animals which will have at least the desired number of results entered in the constructor
	 * This arrayList is populated by animals whose names are of similar length.
	 * Longer and shorter names are added until at least the minimum number of desired results is achieved.
	 * 
	 * getMinOfResults will return empty arraylist if the database has no names of the same length and will stop incrementing and decrementing
	 * the name length when no results is found in either situations.
	 * 
	 * @param includesAnimalType = boolean to determine whether or not to include the animal's species in the search query
	 * @param searchSpecies = species which may or may not have been entered by user
	 * @param animalName = name of the animal entered by the user
	 * @return ArrayList<Animal> containing the minimum number of results whose names are of similar length to the search query
	 * @throws SQLException
	 */
	private ArrayList<Animal> getMinNumOfResults(boolean includesAnimalType, String searchSpecies, String animalName){	
		
		//get results for names of the same length
		ArrayList<Animal> minMatches = new ArrayList<Animal>();
		
		if(includesAnimalType) {
			query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=? AND A.Species=?;";
		}
		else {
			query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=?;";
		}
//		System.out.println("query = "+query);
		
		try{
			PreparedStatement statement = this.dao2.prepareStatement(query);
			statement.setInt(1, animalName.length());
			if(includesAnimalType) {statement.setString(2, searchSpecies);}
			
			ResultSet results = statement.executeQuery();
			
			minMatches = createListAnimal(results, minMatches);
//			System.out.println("\t>>> Got response from Server - Grabbed names of the same length");
			statement.close();
			
		}catch(Exception e) {
			System.out.println("Error grabbing names of the same length");
		}
		
			
		//get results for names of longer and/or shorter length 
		boolean noMoreSmallerNames = false; 
		boolean noMoreBiggerNames = false;
		int lengthDeviationFromOriginalName = 1;
		

		//while loop ends when the number of desired search queries is found 
		//or when no more names can be found that are greater or less than the size of the animalName that is in the search input
		while(minMatches.size() < this.desiredNumberOfResults && (!noMoreSmallerNames || !noMoreBiggerNames)) {	
			try {
				if (!noMoreSmallerNames) {
//					System.out.println("------query for names of SMALLER size");
					ArrayList<Animal> smallerNames = new ArrayList<Animal>();
					
					if(includesAnimalType) {
						query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=? AND A.Species=?";
					}
					else {
						query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=?";
					}
					
					PreparedStatement statement = this.dao2.prepareStatement(query);
					statement.setInt(1, animalName.length()-lengthDeviationFromOriginalName);
					if(includesAnimalType) {statement.setString(2, searchSpecies);}
					
					ResultSet results = statement.executeQuery();
					smallerNames = createListAnimal(results, smallerNames);
			
					for (Animal newAnimal: smallerNames) {minMatches.add(newAnimal);}
					
					if (smallerNames.size() ==0) {noMoreSmallerNames = true;}
					statement.close();
				}
				
				if (!noMoreBiggerNames) {
//					System.out.println("------query for names of LARGER size");
					ArrayList<Animal> largerNames = new ArrayList<Animal>();
					
					if(includesAnimalType) {
						query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=? AND A.Species=?";
					}
					else {
						query = "SELECT * FROM "+this.tableName +" AS A WHERE A.Length_Name=?";
					}
					
					PreparedStatement statement = this.dao2.prepareStatement(query);
					statement.setInt(1, animalName.length()+lengthDeviationFromOriginalName);
					if(includesAnimalType) {statement.setString(2, searchSpecies);}
					
					ResultSet results = statement.executeQuery();
					largerNames = createListAnimal(results, largerNames);
					
					for (Animal newAnimal: largerNames) {minMatches.add(newAnimal);}
					
					if (largerNames.size() ==0) {noMoreBiggerNames = true;}
					statement.close();
				}
			
				lengthDeviationFromOriginalName++;
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error getting names for smaller and bigger names");
				break; //break out of while loop if error encountered
			}			
		}//end of while loop
		
//		System.out.println("\n---- now have minimum number of search queries or no more names could be found");		
		return minMatches;
	}
	
	
	/**
	 * Given ResultSet from executed query, add new Animal object into the arraylist 
	 * @param results = resultset from database
	 * @param currArr = ArrayList<Animal> which is to have animal objects (instantiated with information from the resultset) added in
	 * @return the updated arraylist
	 * @throws SQLException
	 */
	private ArrayList<Animal> createListAnimal(ResultSet results, ArrayList<Animal> currArr) throws Exception{
		while (results.next()) {
			currArr.add(new Animal(results.getInt("Animal_ID"), results.getString("Animal_Name"),
					results.getString("Species"), results.getString("Breed"), results.getInt("Tattoo_Num"),
					results.getString("City_Tattoo"), LocalDate.parse(results.getString("Birth_Date")),
					results.getString("Sex").charAt(0), results.getString("RFID"), results.getString("Microchip"),
					results.getString("Health_Status"), results.getBoolean("Availability_Status"),
					results.getString("Colour"), results.getString("Additional_Info"), 
					results.getInt("Length_Name"), results.getString("SearchKey_Name")));
		}
		
		return currArr;
	}
	
	/**
	 * Calculates the difference in the search keys between the user's search name and the name of the animal stored on the database
	 * @param newSearchKey = search key belonging to the user's search name
	 * @param dbSearchKey = search key belonging to the animal on the database
	 * @return integer representing the difference between the search keys of the user's search name and the name of the animal stored on the database based on presence and frequency of letters
	 */
	private int calcDifference(String newSearchKey, String dbSearchKey) {
//		System.out.println("\n --within calcDifference()");
//		System.out.println("newSearchKey: "+newSearchKey);
//		System.out.println("dbSearchKey: "+dbSearchKey);
		ArrayList<DictionaryLetter> dict_new = createLetterDictionaryBySearchKey(newSearchKey);
		ArrayList<DictionaryLetter> dict_db = createLetterDictionaryBySearchKey(dbSearchKey);
		
		int difference = 0;
		for (int idx=0; idx<dict_new.size(); idx++) {
			int letter_difference = Math.abs(dict_new.get(idx).value - dict_db.get(idx).value);
			difference = difference + letter_difference;
		}
//		System.out.println("difference = "+difference);
		return difference;
	}
	
	
	/**
	 * Creates a dictionary (data structure) based on the search key passed in 
	 * where the keys are letters of the alphabet and the values is the quantity of that letter in the name 
	 * (either of the user's search name or the name of the animal on the database)
	 * @param searchKey = String where each part is delimited with "-" and each part is comprised of a letter of the alphabet and 
	 * 						an integer representing how often that letter appears in the name
	 * @return ArrayList<DictionaryLetter> which acts as a dictionary data structure
	 */
	private ArrayList<DictionaryLetter> createLetterDictionaryBySearchKey(String searchKey){
//		System.out.println("\n --within createLetterDictionaryBySearchKey()");
//		System.out.println("searchKey: "+searchKey);
		ArrayList<DictionaryLetter> dict = createLetterDictionary();
		for (String subString: searchKey.split("-")) {
			subString = subString.replaceAll("\\s", "");
			char letter = subString.charAt(0);
//			System.out.println("letter = "+letter);
			dict.get((int)letter-(int)'a').value = dict.get((int)letter-(int)'a').value +Integer.valueOf(subString.substring(1));
		}
//		System.out.println(dict);
		return dict;
	}
	
	
	/**
	 * Create a search key based on the name passed in
	 * @param animalName = name of an animal
	 * @return searchKey which is a String where each part is delimited with "-" and each part is comprised of a letter of the alphabet and 
	 * 						an integer representing how often that letter appears in the name
	 */
	private String generateSearchKey(String animalName) {
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
//		System.out.print("newSearchKey: "+ newSearchKey);
		return newSearchKey;
	}
	
	
	/**
	 * Create a dictionary data structure where all the values are zero for each key which is a letter of the alphabet
	 * @return dictionary data structure for a blank name so all letter keys have a value of zero 
	 */
	public ArrayList<DictionaryLetter> createLetterDictionary(){
		ArrayList<DictionaryLetter> dict = new ArrayList<DictionaryLetter>();
		for (int idx=0; idx<26; idx++) {
//			System.out.println((char)('a'+idx));
			dict.add(new DictionaryLetter((char)('a'+idx), 0));
		}
		return dict;
	}


	
	
}
