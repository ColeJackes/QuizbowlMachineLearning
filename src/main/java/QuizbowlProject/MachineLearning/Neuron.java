package QuizbowlProject.MachineLearning;

import java.util.ArrayList;

public class Neuron {
	public double value;
	//For hiddenLayer and outputLayer neurons only (may want to use inheritance to create subclass)
	public ArrayList<Double> theta = new ArrayList<Double>();
	public ArrayList<Double> bigDelta = new ArrayList<Double>();
	public ArrayList<Double> d = new ArrayList<Double>();
	public double delta;
	//For ouputLayer neuron only (may want to use inheritance to create subclass)
	public String name;
}
