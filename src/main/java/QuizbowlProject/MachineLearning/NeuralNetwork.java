package QuizbowlProject.MachineLearning;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
	
	ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
	
	public void setInputLayer (double[] parameters) {
		for (int i = 0; i < parameters.length; i ++) {
			this.network.get(0).get(i).value = parameters[i];
		}
	}
	
	public void randomlyInitializeNetwork(int inputSize, int hiddenSize, int outputSize) {
		//Input layer (one neuron per feature)
		ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
		
		//Hidden layer - http://stats.stackexchange.com/questions/181/how-to-choose-the-number-of-hidden-layers-and-nodes-in-a-feedforward-neural-netw
		//one layer usually sufficient
		//number of neurons usually in between the number of input and output neurons
		ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
		
		//Output layer (one neuron per class)
		ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
		
		Random random = new Random();
		
		for (int i = 0; i < inputSize; i ++) {
			Neuron neuron = new Neuron();
			neuron.value = 0;
			for (int j = 0; j < hiddenSize; j ++) {
				double randomValue = -10 + 20 * random.nextDouble();
				neuron.theta.add(randomValue);
			}
			inputLayer.add(neuron);
		}
		
		for (int i = 0; i < hiddenSize; i ++) {
			Neuron neuron = new Neuron();
			for (int j = 0; j < outputSize; j ++) {
				double randomValue = -10 + 20 * random.nextDouble();
				neuron.theta.add(randomValue);
			}
			hiddenLayer.add(neuron);
		}
		
		for (int i = 0; i < outputSize; i ++) {
			Neuron neuron = new Neuron();
			outputLayer.add(neuron);
		}

		this.network.add(inputLayer);
		this.network.add(hiddenLayer);
		this.network.add(outputLayer);
	}
	
	public void displayThetas() {
		for (int i = 0; i < this.network.size() - 1; i ++) {
			System.out.println("Layer " + i);
			for (int j = 0; j < this.network.get(i).size(); j ++) {
				System.out.println("Node " + j);
				for (int k = 0; k < this.network.get(i).get(j).theta.size(); k ++) {
					System.out.println(this.network.get(i).get(j).theta.get(k));
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
