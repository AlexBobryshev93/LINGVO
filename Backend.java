import java.io.*;
import java.util.*;

public class Backend {		
	private int quantity = 5;
	private int difficulty = 5;
	private String preset = "--User defined--";
	private int score, answer;
	private String result = "Successfully Started";
	private List<String> eng = new ArrayList<>();
	private List<String> rus = new ArrayList<>();
	private static final Backend instance = new Backend(); // Singleton pattern
	
	private Backend() {
		String str;
		String path = "";

		//Reading the app data from the files
		try (DataInputStream din = new DataInputStream(new FileInputStream("AppData/data1.dat")); BufferedReader fin = new BufferedReader(new FileReader("AppData/data2.dat"))) {
			quantity = din.readInt();
			difficulty = din.readInt();
			preset = fin.readLine();
		}
		catch (IOException exc) {
			quantity = 5;
			difficulty = 5;
			preset = "--User defined--";
			result = "Reading data error occured. Default settings are used";
		}
 
		//Reading the words using collections
		if (!preset.equals("--User defined--")) path = "Presets/" + preset + "/";
		try (BufferedReader fin1 = new BufferedReader(new FileReader(path + "eng.txt")); 
			BufferedReader fin2 = new BufferedReader(new FileReader(path + "rus.txt"))) {
			for (;;) {
				str = fin1.readLine();
				if (str == null) break;
				eng.add(str);
				str = fin2.readLine();
				if (str == null) break;
				rus.add(str);
			}
		}
		
		// fool protection?
		/*
		catch (FileNotFoundException exc) {
			System.out.println(exc);
			preset = "--User defined--";
			result = "Reading data error occured. Default settings are used";
		}
		*/
		
		catch (IOException exc) {
			result = "Reading data error.";
			System.exit(1);
		}
		
		if ((eng.size() < difficulty) || (rus.size() < difficulty) || (eng.size() < quantity) || (rus.size() < quantity)) {
			result = "Error: too few words to compose a test!";
			System.exit(1);
		}
	}
	
	public static Backend getInstance() {
		return instance;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int sc) {
		score = sc;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public void setAnswer(int an) {
		answer = an;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String res) {
		result = res;
	}
	
	public String getWord(int i, int j) {
		if (i == 0) return eng.get(j);
		else if (i == 1) return rus.get(j);
		else throw new IllegalArgumentException();
	}

	public int getRange() {
		return eng.size();
	}
}