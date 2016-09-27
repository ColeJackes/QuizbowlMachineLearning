package QuizbowlProject.MachineLearning;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

//Class to perform multiclass classification using logistic regression
public class LogisticRegressionTraining {
	
	public static ArrayList<double[]> thetas = new ArrayList<double[]>();
	
	public LogisticRegressionTraining() throws IOException, TikaException, SAXException {
		
		FeatureExtractor featureExtractor = new FeatureExtractor();
		
		//1: History
		//2: Literature
		//3: Science
		//4: Other
		
		//X without the features found
		GetTrainingData getTrainingData = new GetTrainingData();
		double[][] featureMatrix = getTrainingData.getTossupFeatureMatrix();
		int[] tossupTypes = getTrainingData.getTrainingDataClassifications();
		int numFeatures = featureExtractor.size;
		
		//Initial theta
		for (int i = 1; i <= 4; i ++) {
			double[] theta = new double[numFeatures];
			theta = gradientDescent(theta, featureMatrix, tossupTypes, numFeatures, i, 100, 1);
			for (int j = 0; j < theta.length; j ++) {
				System.out.println(theta[j]);
				if (j == 3) {
					System.out.println("History");
				}
				if (j == 11) {
					System.out.println("Literature");
				}
				if (j == 17) {
					System.out.println("Science");
				}
			}
			System.out.println();
			thetas.add(theta);
		}
		
	}
	
	//Using no regularization (at least to start)
	//Turns out that this function is not needed by this implementation of fmincg
	private static double[] getCostAndGradient(double[][] X, int[] y, double[] theta, double lambda) {
		
		double[] costAndGradient = new double[2];
		double cost = 0.0;
		double gradient = 0.0;
		
		double[] temp = theta;
		temp[0] = 0;
		
		//Matlab code I wrote for Assignment 3 of Coursera Machine Learning course
		//J = 1/m*(-y' * log(sigmoid(X * theta)) - (1 - y')*log(1 - sigmoid(X * theta))) + sum(lambda/(2*m).*temp.^2);
		//grad = 1/m*(sigmoid(X * theta) - y)'*X + (lambda/m).*temp';
		for (int i = 0; i < X.length; i ++) {
			for (int j = 0; j < theta.length; j ++) {
				cost += (1/y.length)*((-y[i])*Math.log(sigmoid(X[i][j]*theta[j])) - (1 - y[i])*Math.log((1.0 - sigmoid(X[i][j])*theta[j])));
				gradient += (1/y.length)*(sigmoid(X[i][j]*theta[j]) - y[i])*X[i][j] + (lambda/y.length)*temp[j];
			}
		}
		
//		System.out.println("Cost " +cost);
		
		costAndGradient[0] = cost;
		costAndGradient[1] = gradient;
		
		return costAndGradient;
	}
	
	private static double sigmoid(double d) {
		return 1.0 / (1.0 + Math.exp(-d));
	}
	
	private static double[] gradientDescent(double[] theta, double[][] X, int[] y, int numFeatures, int currentClass, int iterations, double alpha) {
		
		int[] correctType = new int[y.length];
		
		for (int i = 0; i < y.length; i ++) {
			if (y[i] == currentClass) {
				correctType[i] = 1;
			}
			else {
				correctType[i] = 0;
			}
		}
		
		for (int i = 0; i < iterations; i ++) {
			double[] x = new double[y.length];
			double[] originalTheta = theta;
			
//			getCostAndGradient(X, y, theta, 1);
			
			//Classifying
			for (int j = 0; j < y.length; j ++) {
				for (int k = 0; k < numFeatures - 1; k ++) {
					x[j] += originalTheta[k]*X[j][k];
				}
				x[j] = sigmoid(x[j]);
				for (int k = 0; k < theta.length; k ++) {
					theta[k] -= alpha*(x[j] - correctType[j])*X[j][k];	
				}
			}
		}
		
		return theta;
	}
	
	private static double classify(double[] x, double[] theta) {
		double logit = .0;
		for (int i=0; i<theta.length;i++)  {
			logit += theta[i] * x[i];
		}
		return sigmoid(logit);
	}
}
