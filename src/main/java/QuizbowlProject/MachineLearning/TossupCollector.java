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
	//////////////////////////////////////          1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,TB
	public static int[] tossupTypes = new int[] { 
					/*Round01*/					    1,3,2,4,4,3,1,3,4,4,1,2,3,4,2,1,4,4,2,3,2,
					/*Round02*/					    4,3,4,2,4,1,4,3,1,4,2,1,4,3,4,4,2,1,3,2,3,
					/*Round03*/					    2,4,3,1,3,4,4,2,3,1,4,4,2,1,4,2,1,4,4,3,4,
					/*Round04*/						2,4,4,3,2,1,3,4,1,3,4,1,3,2,4,1,4,4,2,3,1,
					/*Round05*/						4,1,3,1,2,1,3,4,2,4,4,1,3,2,4,1,4,3,2,4,3,
					/*Round06*/						4,4,4,4,4,3,1,4,3,2,1,4,4,2,1,3,4,1,2,3,2,
					/*Round07*/						4,4,1,3,4,2,1,4,2,3,1,4,3,4,4,2,1,4,2,3,4,
					/*Round08*/						4,1,3,2,4,2,4,3,4,1,4,3,4,1,3,2,1,4,2,4,1,
					/*Round09*/						4,2,1,3,4,1,4,4,4,2,3,1,4,3,1,2,4,1,2,3,3,
					/*Round10*/						3,2,2,4,1,2,3,4,2,1,4,4,4,1,3,1,4,2,3,1,1,
					/*Round11*/						1,2,3,4,4,1,3,2,4,1,3,4,2,4,3,2,2,1,4,1,2,
					/*Round12*/						3,2,1,1,4,4,3,1,2,4,4,3,2,4,1,3,4,2,1,4,3,
					/*Round13*/						4,4,4,2,3,4,1,4,3,2,1,1,4,2,1,3,4,1,3,2,1,
					/*Round14*/						1,4,2,4,1,3,4,2,3,4,3,2,4,4,1,4,2,3,1,4,2,
					/*Round15*/						1,4,2,1,4,3,4,4,3,2,1,4,4,2,1,4,3,2,4,3
										 };
	
	public static void main(String[] args) throws IOException, TikaException, SAXException {
		ArrayList<String> tossups = getTossups();
	}
	
	public static ArrayList<String> getTossups () throws IOException, TikaException, SAXException {
		//Put in a try-catch
		String filePath = new File("").getAbsolutePath();
		filePath = filePath.concat("/QuizbowlPacks/Training");
		File dir = new File(filePath);
		//File dir = new File("/Users/colejackes/QuizbowlMachineLearning/QuizbowlMachineLearning/QuizbowlPacks/Training");
		File[] directoryListing = dir.listFiles();
		ArrayList<String> tossupArray = new ArrayList<String>();
		if (directoryListing != null) {
			for (File file : directoryListing) {
			      
		      //Instantiating Tika facade class
			  Tika tika = new Tika();
			  String pack = tika.parseToString(file);
			  
			  int numTossups = 0;
			  int startIndex = 0;
			  int endIndex = 0;
			  boolean tossupFinished = false;
			  
			  
			  //Have to be very careful picking out question locations as often numbers identical to question numbers appear in science questions
			  for (int i = 0; i < pack.length(); i ++) {
				  if (pack.substring(i, i + 3).equals("1. ") && numTossups == 0) {
					  startIndex = i + 3;
				  }
				  
				  if (pack.substring(i, i + 6).equals("ANSWER")) {
					  endIndex = i;
					  tossupArray.add(pack.substring(startIndex, endIndex));
					  numTossups ++;
					  if (numTossups == 21) {
						  break;
					  }
					  tossupFinished = true;
				  }
				  
				  if (tossupFinished) {
					  if (Character.isDigit(pack.charAt(i)) && pack.charAt(i + 1) == '.' && pack.charAt(i + 2) == ' ' && !Character.isDigit(pack.charAt(i - 2)) && pack.charAt(i-1) != '-' || pack.substring(i, i + 4).equals("TB. ")) {
						  startIndex = i + 3;
						  tossupFinished = false;
					  }
				  }
			  }
		   }
		}
		
		return tossupArray;
	}
}
