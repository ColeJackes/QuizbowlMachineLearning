package QuizbowlProject.MachineLearning;

import java.util.ArrayList;

public class FeatureExtractor {
	
	private String[] historyKeyWords = new String[] {"ruler", "dynasty", "king", "queen"};
	private String[] literatureKeyWords = new String[] {"novel", "book", "written", "author"};
	private String[] scienceKeyWords = new String[] {"alpha", "beta", "molecule", "newton"};
	
	public int size = 17;
	
	public ArrayList<Double> getFeatureArray(String tossup) {
		ArrayList<Double> arr = new ArrayList<Double>();
		
		tossup = removeExtraSpaces(tossup);
		ArrayList<String> tossupArray = getTossupArray(tossup);
		
		arr.add(averageWordLength(tossupArray));
		arr.add(longestWordLength(tossupArray));
		arr.add(pastTenseVerbs(tossupArray));
		arr.add(years(tossupArray));
		arr.add(doubles(tossupArray));
		
		double[] historyKeyWordsArr = historyKeyWords(tossupArray);
		for (int i = 0; i < historyKeyWordsArr.length; i ++){
			arr.add(historyKeyWordsArr[i]);	
		}
		
		double[] literatureKeyWordsArr = literatureKeyWords(tossupArray);
		for (int i = 0; i < literatureKeyWordsArr.length; i ++){
			arr.add(literatureKeyWordsArr[i]);	
		}
		
		double[] scienceKeyWordsArr = scienceKeyWords(tossupArray);
		for (int i = 0; i < scienceKeyWordsArr.length; i ++){
			arr.add(historyKeyWordsArr[i]);	
		}
		
		return arr;
	}
	
	private ArrayList<String> getTossupArray(String tossup) {
		ArrayList<String> tossupArray = new ArrayList<String>();
		
		tossup = removeExtraSpaces(tossup);
		
		int startIndex = 0;
		
		for (int i = 0; i < tossup.length(); i ++) {
			if (tossup.charAt(i) == ' ' || tossup.charAt(i) == tossup.length() - 1) {
				tossupArray.add(tossup.substring(startIndex, i));
				startIndex = i + 1;
			}
		}
		
		return tossupArray;
	}
	
	private String removeExtraSpaces(String tossup) {
		
		for (int i = 1; i < tossup.length(); i ++) {
			if (tossup.charAt(i) == ' ' && tossup.charAt(i - 1) == ' ') {
				tossup = tossup.substring(0, i - 1) + tossup.substring(i);
			}
		}
		
		return tossup;
	}
	
	public double years(ArrayList<String> tossupArray) {
		double years = 0.0;
		for (int i = 0; i < tossupArray.size(); i++) {
			if (tossupArray.get(i).length() == 4) {
				if (Character.isDigit(tossupArray.get(i).charAt(0))){
					years = years + 1;
				}
			}
		}
		return years;
	}
	
	public double doubles(ArrayList<String> tossupArray) {
		double doubles = 0.0;
		for (int i = 0; i < tossupArray.size(); i++) {
			String word = tossupArray.get(i);
			for (int j = 1; j < word.length() - 1; j ++) {
				if (word.charAt(j) == '.' && Character.isDigit(word.charAt(j - 1)) && Character.isDigit(word.charAt(j + 1))) {
					doubles = doubles + 1;
				}
			}
		}
		return doubles;
	}
	
	public double averageWordLength(ArrayList<String> tossupArray) {
		
		int totalLength = 0;
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			totalLength += tossupArray.get(i).length();
		}
		
		double averageWordLength = (double) totalLength / tossupArray.size();
		
		return averageWordLength;
	}
	
	public double longestWordLength(ArrayList<String> tossupArray) {
		double longestWordLength = 0;
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			if (tossupArray.get(i).length() > longestWordLength) {
				longestWordLength = tossupArray.get(i).length();
			}
		}
		
		return longestWordLength;
	}
	
	public double pastTenseVerbs(ArrayList<String> tossupArray) {
		double pastTenseVerbs = 0;
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			String word = tossupArray.get(i);
			if (word.length() >= 5) {
				if (word.charAt(word.length() - 2) == 'e' && word.charAt(word.length() - 1) == 'd') {
					pastTenseVerbs ++;
				}	
			}
		}
		
		return pastTenseVerbs;
	}
	
	public double[] historyKeyWords(ArrayList<String> tossupArray) {
		double[] historyKeyWordOccurrences = new double[historyKeyWords.length];
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			for (int j = 0; j < historyKeyWords.length; j ++) {
				if (tossupArray.get(i).compareToIgnoreCase(historyKeyWords[j]) == 0) {
					historyKeyWordOccurrences[j] ++;
				}
			}
		}
		
		return historyKeyWordOccurrences;
	}
	
	public double[] literatureKeyWords(ArrayList<String> tossupArray) {
		double[] literatureKeyWordOccurrences = new double[literatureKeyWords.length];
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			for (int j = 0; j < literatureKeyWords.length; j ++) {
				if (tossupArray.get(i).compareToIgnoreCase(literatureKeyWords[j]) == 0) {
					literatureKeyWordOccurrences[j] ++;
				}
			}
		}
		
		return literatureKeyWordOccurrences;
	}
	
	public double[] scienceKeyWords(ArrayList<String> tossupArray) {
		double[] scienceKeyWordOccurrences = new double[scienceKeyWords.length];
		
		for (int i = 0; i < tossupArray.size(); i ++) {
			for (int j = 0; j < scienceKeyWords.length; j ++) {
				if (tossupArray.get(i).compareToIgnoreCase(scienceKeyWords[j]) == 0) {
					scienceKeyWordOccurrences[j] ++;
				}
			}
		}
		
		return scienceKeyWordOccurrences;
	}

}
