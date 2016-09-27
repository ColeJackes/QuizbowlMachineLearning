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
		ArrayList<String> tossups = getTossups();
//		for (int i = 0; i < tossups.size(); i ++) {
//			System.out.println(tossups.get(i));
//			System.out.println(i);
//		}
	}
	
	public static ArrayList<String> getTossups () throws IOException, TikaException, SAXException {
		
		File dir = new File("/Users/colejackes/Desktop/Development/MachineLearning/QuizbowlPacks/Training");
		File[] directoryListing = dir.listFiles();
		ArrayList<String> tossupArray = new ArrayList<String>();
		
		if (directoryListing != null) {
			for (File file : directoryListing) {
				
//			  System.out.println("New file");
			      
		      //Instantiating Tika facade class
			  Tika tika = new Tika();
			  String pack = tika.parseToString(file);
//			  System.out.println("Extracted Content: " + pack);
			  
			  int numTossups = 0;
			  int startIndex = 0;
			  int endIndex = 0;
			  boolean tossupFinished = false;
			  
			  
			  //Have to be very careful picking out question locations as often numbers identical to question numbers appear in science questions
			  for (int i = 0; i < pack.length(); i ++) {
				  if (pack.substring(i, i + 3).equals("1. ")) {
					  startIndex = i + 3;
				  }
				  
				  if (pack.substring(i, i + 6).equals("ANSWER")) {
					  endIndex = i;
					  tossupArray.add(pack.substring(startIndex, endIndex));
//					  System.out.println(pack.substring(startIndex, endIndex));
					  numTossups ++;
					  if (numTossups == 21) {
						  break;
					  }
					  tossupFinished = true;
				  }
				  
				  if (tossupFinished) {
					  if (Character.isDigit(pack.charAt(i)) && pack.charAt(i + 1) == '.' && pack.charAt(i + 2) == ' ' && !Character.isDigit(pack.charAt(i - 2)) && pack.charAt(i-1) != '-' || pack.substring(i, i + 4).equals("TB. ")) {
						  startIndex = i + 3;
					  }
				  }
			  }
		   }
		}
		
		return tossupArray;
	}
}
