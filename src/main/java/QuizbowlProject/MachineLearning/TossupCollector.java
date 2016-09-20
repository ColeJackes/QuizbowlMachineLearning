package QuizbowlProject.MachineLearning;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class TossupCollector {
	
	//1: History
	//2: Literature
	//3: Science
	//4: Other
	////////////////////////////////////// 1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,TB
	public static int[] tossupTypes = new int[] { 1,3,2,4,4,3,1,3,4,4,1,2,3,4,2,1,4,4,2,3,2,
										   4,3,4,2,4,1,4,3,1,4,2,1,4,3,4,4,2,1,3,2,3,
										   2,4,3,1,3,4,4,2,3,1,4,4,2,1,4,2,1,4,4,3,4,
										 };
	
	public static void main(String[] args) throws IOException, TikaException, SAXException {
		getTossups();
	}
	
	public static ArrayList<String> getTossups () throws IOException, TikaException, SAXException {
		
		File dir = new File("/Users/colejackes/Desktop/Development/MachineLearning/QuizbowlPacks/Training");
		File[] directoryListing = dir.listFiles();
		ArrayList<String> tossupArray = new ArrayList<String>();
		
		if (directoryListing != null) {
			for (File child : directoryListing) {
			      
		      //Instantiating Tika facade class
			  Tika tika = new Tika();
			  String pack = tika.parseToString(child);
			  //System.out.println("Extracted Content: " + pack);
			  
			  int startIndex = 0;
			  int endIndex = 0;
			  
//			  boolean lastQuestionOver = false;
//			  boolean firstQuestion = true;
//			  
//			  for (int i = 0; i < pack.length(); i ++) {
//				  if (pack.substring(i, i+5).equals("ANSWER")) {
//					  lastQuestionOver = true;
//				  }
//			  }
//			  
			  
			  //Have to be very careful picking out question locations as often numbers identical to question numbers appear in science questions
			  for (int i = 0; i < pack.length(); i ++) {
				  if (Character.isDigit(pack.charAt(i))) {
					  if (Character.getNumericValue(pack.charAt(i)) != 0) {
			    		  if (pack.charAt(i+1) == '.' && !Character.isDigit(pack.charAt(i+2)) && !Character.isDigit(pack.charAt(i - 1)) && pack.charAt(i - 1) != '-') {
			    			  //start of tossup 1-9
			    			  if (startIndex == 0){
			    				  startIndex = i + 2;
			    			  }
			    			  else {
			    				  endIndex = i + 2;
			    				  //Add to array
			    				  tossupArray.add(pack.substring(startIndex, endIndex));
			    				  startIndex = endIndex;
			    			  }
			    		  } 
			    		  else if (Character.isDigit(pack.charAt(i+1))) {
			    			  if (!Character.isDigit(pack.charAt(i - 1)) && pack.charAt(i - 2) != '-' && (Character.getNumericValue(pack.charAt(i)) == 1 || Character.getNumericValue(pack.charAt(i)) == 2)) {
				    			  if (pack.charAt(i+2) == '.' && !Character.isDigit(pack.charAt(i+2))) {
				    				  //start of tossup 10-24
				    				  endIndex = i + 2;
				    				  //Add to array
				    				  tossupArray.add(pack.substring(startIndex, endIndex));
				    				  startIndex = endIndex;
					    			  
				    			  }	  
			    			  }
			    		  }
					  }
				  }
				  if (pack.substring(i, i+3).equals("TB.")) {
					  //Tiebreaker tossup
    				  endIndex = i + 2;
    				  //Add to array
    				  tossupArray.add(pack.substring(startIndex, endIndex));
    				  startIndex = endIndex; 
				  }
				  if (pack.charAt(i) == '[' && pack.charAt(i + 3) == ']') {
			    		  break;
			    	  }
			      }	

			  
			  }
			
//			  for (int i = 0; i < tossupArray.size(); i ++) {
//				  System.out.println("NNNEEEEWWW TOOSSSSUUUPPP");
//				  if (tossupTypes[i] == 1) {
//					  System.out.println("History:");
//				  }
//				  else if (tossupTypes[i] == 2) {
//					  System.out.println("Literature: ");
//				  }
//				  else if (tossupTypes[i] == 3) {
//					  System.out.println("Science: ");
//				  }
//				  else if (tossupTypes[i] == 4) {
//					  System.out.println("Other: ");
//				  }
//				  System.out.println(tossupArray.get(i));
//			  }
		}
		
		return tossupArray;
	}
}
