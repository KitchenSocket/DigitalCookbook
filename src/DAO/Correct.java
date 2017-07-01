package DAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import model.Ingredient;
import model.Recipe;


/**
 * spell corrector correct user input according to the dictionary txt file 
 * hon sao rou -> hong shao rou, yu xiang -> yuxiang
 * 
 * @author CHANDIM
 *
 */

public class Correct {

	private final HashMap<String, Integer> dictionaryWords = new HashMap<String, Integer>();

	private final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	 
	private File file;

	public Correct(String fileName) throws IOException {
		
		this.file = new File(fileName);

		BufferedReader in = new BufferedReader(new FileReader(fileName));

		// get content from dictionary file
		for (String temp = in.readLine(); temp != null; temp = in.readLine()) {

			// <word, occurrence>
			// single word=1
			// duplicated word>1
			dictionaryWords.put(temp, dictionaryWords.containsKey(temp) ? dictionaryWords.get(temp) + 1 : 1);
		}
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		
		in.close();
		
	}

	/**
	 * edit input:
	 *  - delete a letter (hello -> ello,hllo,helo,hell) 
	 *  - swap two letters (hello -> ehllo,hlelo,hello,helol) 
	 *  - substitute a letter (hello -> aello,bello,hellz ...) 
	 *  - add a letter in between (hello -> haello ...)
	 * return a list of reconstructed words
	 * 
	 * @param word
	 * @return ArrayList<String>
	 */
	private final ArrayList<String> edits(String word) { 

		ArrayList<String> result = new ArrayList<String>();

		// if the word has less than 3 letters
		// then leave the word
		if (word.length() < 3)
			return result;

		// delete
		for (int i = 0; i < word.length(); ++i)
			result.add(word.substring(0, i) + word.substring(i + 1));

		// swap
		for (int i = 0; i < word.length() - 1; ++i)
			result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1)
					+ word.substring(i + 2));

		// substitute
		for (int i = 0; i < word.length(); ++i)
			for (char c : alphabet)
				result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));

		// add
		for (int i = 0; i <= word.length(); ++i)
			for (char c : alphabet)
				result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));

		return result;
	}

	/**
	 * choose a optimal option from recommendation
	 * 
	 * @param word
	 * @return
	 */
	public final String correct(String word) {

		// if correct
		if (dictionaryWords.containsKey(word)) 
			return word;
			

		// if incorrect
		ArrayList<String> list = edits(word);

		HashMap<Integer, String> candidates = new HashMap<Integer, String>();

		for (String s : list) {
			
			if (dictionaryWords.containsKey(s)) {
				
				candidates.put(dictionaryWords.get(s), s);
				
			}
			
		}

		// if recommend several options
		// return option with most occurrence
		if (candidates.size() > 0) {
			
			return candidates.get(Collections.max(candidates.keySet()));
			
		}

		// if no recommend option
		// edit all recommend option
		for (String s : list) {
			
			for (String w : edits(s)) {
				
				if (dictionaryWords.containsKey(w)) {
					
					candidates.put(dictionaryWords.get(w), w);
					
				}
			}
			
		}
		
		// return option with most occurrence
		// if not, return the origin user input
		return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;

	}

	/**
	 * update the words txt content
	 * 
	 * @param word
	 * @throws IOException
	 */
	public final void updateDict(String word) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		
		OutputStream os = new FileOutputStream(file,true);
		
		ArrayList<String> content = new ArrayList<String>();
		
		ArrayList<String> oneLine = new ArrayList<String>();
		
		for (String line = br.readLine(); line != null; line = br.readLine()) {

			oneLine = new ArrayList<String>(Arrays.asList(line.split(" ")));
			
			content.add(line.trim());
			
			content.addAll(oneLine);
			
			System.out.println(line);
			
		}
		
		br.close();
		

		
		if(!content.contains(word)) {
			
			word += "\r\n";
			
			os.write(word.getBytes());

			oneLine = new ArrayList<String>(Arrays.asList(word.split(" ")));
			
			for(String singleWord : oneLine) {
				
				singleWord += "\r\n"; 
				
				os.write(singleWord.getBytes());
			}
			
		}
		
		os.close();
		
	}
	
	/**
	 * initialize word.txt
	 * 
	 * @throws IOException
	 */
	public final void initDict() throws IOException {
		
		RecipeDAO DAO = new RecipeDAO();
		
		ArrayList<Recipe> list = DAO.getRecipeListByName("%");
		
		IngredientDAO DAOI = new IngredientDAO();
		
		ArrayList<Ingredient> listI = DAOI.getIngredientListByName("%");
		
		for( Recipe recipe : list) {
			
			updateDict(recipe.getName());
			
		}
		
		for( Ingredient ingredient : listI) {
			
			updateDict(ingredient.getName());
			
		}
		
	}
	
	
	public static void main(String args[]) throws IOException {
		//Scanner scan = new Scanner(System.in);
		// while(scan.hasNextLine()){
		//String in = scan.nextLine().trim();
		//System.out.println((new Correct("words.txt")).correct(in));
		Correct test = new Correct("C:\\Users\\CHANDIM\\Desktop\\fakk.txt");
		test.updateDict("hong shao rou");
		
	}

}
