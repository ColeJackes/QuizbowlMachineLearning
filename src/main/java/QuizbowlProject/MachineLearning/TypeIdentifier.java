package QuizbowlProject.MachineLearning;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class TypeIdentifier {
	public static void main (String[] args) throws IOException, TikaException, SAXException {
		String tossup3 = "This man once ran on a ticket under James Cox, and his advisors included a future governor of Puerto Rico, Rexford Tugwell. During this man's presidency, Frank Murphy and Robert Jackson dissented in a Supreme Court case brought by Fred [*] Korematsu regarding this man's Executive Order 9066. Also known for advocating Lend-Lease aid, his term saw the formation of the Tennessee Valley Authority. For 10 points, name this U.S. President who gave many fireside chats, advocated a New Deal, and died during his record-setting fourth presidential term.";
		String tossup = "A novel by this author features the scientist Beenay from Saro University in Lagash, who learns about the Apostles of Flame. Alexander Adell and Bertram Lupov pose the title inquiry for the first time to Multivac in a short story by this man. Roj Nemmenuh Sarton, a resident of Spacetown, is found murdered in this author’s The Caves of Steel. In another of his novels, Hari Seldon’s development of psychohistory uses the laws of mass action to predict the future. This author of 'The Last Question' and Nightfall put forth 'Three Laws' for machines in the short story 'Runaround' For 10 points, name this American science fiction author of the Foundation series and 'I, Robot.'";
		String tossup2 = "was discovered";
		
		FeatureExtractor featureExtractor = new FeatureExtractor();
		ArrayList<Double> X = featureExtractor.getFeatureArray(tossup);
		
		LogisticRegressionTraining learningAlgorithm = new LogisticRegressionTraining();
		ArrayList<double[]> thetas = learningAlgorithm.thetas;
		
//		for (int i = 0; i < thetas.size(); i ++) {
//			double[] theta = thetas.get(i);
//			for (int j = 0; j < theta.length; j ++) {
//				System.out.println(theta[j]);
//			}
//			System.out.println();
//		}
		
		double max = -10000000;
		int maxi = -1;
		
		for (int i = 0; i < thetas.size(); i ++) {
			double[] theta = thetas.get(i);
			double sum = 0;
			for (int j = 0; j < theta.length; j ++) {
				sum += X.get(j)*theta[j];
			}
			System.out.println(sum);
			if (sum > max) {
				max = sum;
				maxi = i;
			}
		}
		
		if (maxi == 0) {
			System.out.println("History!");
		}
		else if (maxi == 1) {
			System.out.println("Literature!");
		}
		else if (maxi == 2) {
			System.out.println("Science!");
		}
		else if (maxi == 3) {
			System.out.println("Other!");
		}
		else if (maxi == -1) {
			System.out.println("Error!");
		}
		
		
	}

}
