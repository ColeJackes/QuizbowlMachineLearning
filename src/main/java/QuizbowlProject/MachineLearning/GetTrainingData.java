package QuizbowlProject.MachineLearning;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class GetTrainingData {
	
	public double[][] getTossupFeatureMatrix() throws SAXException, IOException, TikaException {
		
		TossupCollector tossupCollector = new TossupCollector();
		ArrayList<String> tossupArray = tossupCollector.getTossups();
		
		FeatureExtractor featureExtractor = new FeatureExtractor();
		int numFeatures = featureExtractor.getFeatureArray(tossupArray.get(0)).size();
		double[][] X = new double[tossupArray.size()][numFeatures];
		for (int i = 0; i < tossupArray.size(); i ++) {
			ArrayList<Double> features = featureExtractor.getFeatureArray(tossupArray.get(i));
			for (int j = 0; j < numFeatures; j ++) {
				X[i][j] = features.get(j);
			}
		}
		
		return X;
	}
	
	public int[] getTrainingDataClassifications() {
		TossupCollector tossupCollector = new TossupCollector();
		int[] y = tossupCollector.tossupTypes;
		return y;
	}

}
