package QuizbowlProject.MachineLearning;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class NeuralNetworkTraining {
	
	
	public static void main (String[] args) throws IOException, TikaException, SAXException {
		
		//Getting X and y
		GetTrainingData getTrainingData = new GetTrainingData();
		double[][] X = getTrainingData.getTossupFeatureMatrix();
		int[] y = getTrainingData.getTrainingDataClassifications();
		
		//Training neural network
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		
		neuralNetwork.randomlyInitializeNetwork(17, 7, 4);
		
		neuralNetwork.displayThetas();
		
		for (int i = 0; i < X.length; i ++) {
			
			neuralNetwork.setInputLayer(X[i]);
			
			forwardPropagation(neuralNetwork);
			
			computeCost(neuralNetwork, X, y, 0.1);
			
			backPropagation(neuralNetwork, y, i);
			
			updateBigDelta(neuralNetwork);
			
			updateD(neuralNetwork, y.length, 0.1);
			
			gradientDescent(neuralNetwork, 0.1);
			
			neuralNetwork.displayThetas();
		
		}
	}
	
	public static void gradientDescent(NeuralNetwork neuralNetwork, double alpha) {
		for (int i = 0; i < neuralNetwork.network.size() - 1; i ++) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i).get(j).theta.size(); k ++) {
					neuralNetwork.network.get(i).get(j).theta.set(k, neuralNetwork.network.get(i).get(j).theta.get(k) - alpha * neuralNetwork.network.get(i).get(j).d.get(k));
				}
			}
		}
	}
	
	//NOTE: this assumes use of bias unit (don't forget to add elsewhere too)
	public static void updateD(NeuralNetwork neuralNetwork, int m, double lambda) {
		for (int i = 0; i < neuralNetwork.network.size() - 1; i ++) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i+1).size(); k ++) {
					if (j != 0) {
						if (neuralNetwork.network.get(i).get(j).d.size() < neuralNetwork.network.get(i+1).size()) {
							neuralNetwork.network.get(i).get(j).d.add((1/m)*neuralNetwork.network.get(i).get(j).bigDelta.get(k) + lambda * neuralNetwork.network.get(i).get(j).theta.get(k));	
						}
						else {
							neuralNetwork.network.get(i).get(j).d.set(k, neuralNetwork.network.get(i).get(j).d.get(k) + (1/m)*neuralNetwork.network.get(i).get(j).bigDelta.get(k) + lambda * neuralNetwork.network.get(i).get(j).theta.get(k));
						}
					}
					else {
						if (neuralNetwork.network.get(i).get(j).d.size() < neuralNetwork.network.get(i+1).size()) {
							neuralNetwork.network.get(i).get(j).d.add((1/m)*neuralNetwork.network.get(i).get(j).bigDelta.get(k));	
						}
						else {
							neuralNetwork.network.get(i).get(j).d.set(k, neuralNetwork.network.get(i).get(j).d.get(k) + (1/m)*neuralNetwork.network.get(i).get(j).bigDelta.get(k));
						}
					}
				}
			}
		}
	}
	
	public static void updateBigDelta(NeuralNetwork neuralNetwork) {
		for (int i = 0; i < neuralNetwork.network.size() - 1; i ++) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i+1).size(); k ++) {
					if (neuralNetwork.network.get(i).get(j).bigDelta.size() < neuralNetwork.network.get(i+1).size()) {
						neuralNetwork.network.get(i).get(j).bigDelta.add(neuralNetwork.network.get(i).get(j).value * neuralNetwork.network.get(i+1).get(k).delta);	
					}
					else {
						neuralNetwork.network.get(i).get(j).bigDelta.set(k, neuralNetwork.network.get(i).get(j).bigDelta.get(k) + neuralNetwork.network.get(i).get(j).value * neuralNetwork.network.get(i+1).get(k).delta);
					}
				}
			}
		}
	}
	
	
	public static void forwardPropagation(NeuralNetwork neuralNetwork) {
		//Consider adding bias neurons!
		for (int i = 1; i < neuralNetwork.network.size(); i ++) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i-1).size(); k ++) {
					neuralNetwork.network.get(i).get(j).value += neuralNetwork.network.get(i-1).get(k).value * neuralNetwork.network.get(i-1).get(k).theta.get(j); 
				}
			}
		}
	}
	
	public static double computeCost(NeuralNetwork neuralNetwork, double[][] X, int[] y, double lambda) {
		double cost = 0.0;
		for (int i = 0; i < X.length; i ++) {
			for (int j = 0; j < neuralNetwork.network.get(neuralNetwork.network.size() - 1).size(); j ++) {
				y = setY(y, j + 1);
				double classifiedValue = classify(neuralNetwork, X, i, j);
				cost += y[i] * Math.log(sigmoid(classifiedValue)) + (1 - y[i]) * Math.log(1 - classifiedValue);
			}
		}
		
		for (int i = 0; i < neuralNetwork.network.size() - 1; i ++ ) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i).get(j).theta.size(); k ++) {
					cost += lambda/(2*y.length) * Math.pow(neuralNetwork.network.get(i).get(j).theta.get(k), 2);
				}
			}
		}
		
		return cost;
	}
	
	public static void backPropagation(NeuralNetwork neuralNetwork, int[] y, int exampleNum) {
		int numLayers = neuralNetwork.network.size();
		
		for (int i = 0; i < neuralNetwork.network.get(numLayers - 1).size(); i ++) {
			y = setY(y, i + 1);
			neuralNetwork.network.get(numLayers - 1).get(i).delta = (neuralNetwork.network.get(numLayers - 1).get(i).value - y[exampleNum]);
		}
		
		for (int i = numLayers - 2; i > 1; i --) {
			for (int j = 0; j < neuralNetwork.network.get(i).size(); j ++) {
				for (int k = 0; k < neuralNetwork.network.get(i).get(j).theta.size(); k ++) {
					neuralNetwork.network.get(i).get(j).delta += neuralNetwork.network.get(i).get(j).theta.get(k) * neuralNetwork.network.get(i).get(k).delta;	
				}
				neuralNetwork.network.get(i).get(j).delta *= sigmoid(neuralNetwork.network.get(j).get(j).value) * (1 - sigmoid(neuralNetwork.network.get(j).get(j).value));
			}
		}
	}
	
	private static double classify(NeuralNetwork neuralNetwork, double[][] X, int exampleNum, int classNum) {
		double sum = 0.0;
		for (int i = 0; i < neuralNetwork.network.get(neuralNetwork.network.size() - 1).get(classNum).theta.size(); i ++) {
			sum += neuralNetwork.network.get(neuralNetwork.network.size() - 1).get(classNum).theta.get(i) * X[exampleNum][i];
		}
		
		return sum;
	}
	
	private static double sigmoid(double d) {
		return 1.0 / (1.0 + Math.exp(-d));
	}
	
	private static int[] setY (int[] y, int classType) {
		int[] newY = y;
		for (int i = 0; i < y.length; i ++) {
			if (y[i] == classType) {
				newY[i] = 1;
			}
			else {
				newY[i] = 0;
			}
		}
		
		return newY;
	}
	
}

